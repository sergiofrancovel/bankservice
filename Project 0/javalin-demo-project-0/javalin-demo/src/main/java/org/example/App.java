package org.example;

import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import org.example.controllers.*;
import org.example.dao.*;
import org.example.dto.ErrorResponse;
import org.example.models.Roles;
import org.example.models.User;
import org.example.services.*;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger=  LoggerFactory.getLogger(App.class);


    public static void main( String[] args ) {
        // create all my dependencies at this level to control how they get used
        // downstream DEPENDENCY INJECTION

        logger.info("creating repose");

        UserRepository userRepository = new InMemUserRepository();

        UserService userService = new UserService(userRepository);

        JWTService tokenService = new JWTService();

        AuthService authService = new AuthService(userService, tokenService);

        AuthController authController = new AuthController(authService);

        /*code for project 0*/
        AccountRepository<Integer, Double> accountRepo = new InMemAccountDao();
        AccountService accountservice = new AccountService(accountRepo);
        TransactionService transactionService = new TransactionService(accountRepo);
        AccountTransactionController transactionController = new AccountTransactionController(transactionService);

        // create a javalin application with a server and default config
        // listen on port 8080
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.accessManager((handler, context, requiredRoles) -> {
                String header = context.header("Authorization");
                if(requiredRoles.isEmpty()) {
                    System.out.println(requiredRoles);
                    handler.handle(context);
                    System.out.println(requiredRoles);
                    return;
                }
                if(header == null) {
                    logger.error("This request requires and Authorization header");
                    throw new ForbiddenResponse("This request requires and Authorization header");
                } else {
                    if(!header.startsWith("Bearer ")) {
                        logger.error("This request requires token bearer access");
                        throw new ForbiddenResponse("This request requires token bearer access");
                    } else {
                        String token = header.split(" ")[1];
                        try {
                            Claims claims = tokenService.decode(token);
                            String username = claims.getSubject();

                            User user = userService.getUserByUsername(username);

                            if(user == null) {
                                logger.error("User unauthorized to perform request");
                                throw new ForbiddenResponse("User unauthorized to perform request");
                            } else {
                                if(authService.authorize(user, requiredRoles)) {
                                    // if we get here the user is authorized
                                    System.out.println(user);
                                    handler.handle(context);
                                } else {
                                    logger.error("User unauthorized to perform request");
                                    throw new ForbiddenResponse("User unauthorized to perform request");
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                            logger.error("The user could not be validated");
                            throw new ForbiddenResponse("The user could not be validated");
                        }

                    }
                }
            });
        }).start(8080);


        app.routes(() -> {
            crud("admin/account/{id}", new AccountAdminController(accountservice), Roles.ADMIN);
            crud("account/{id}", new AccountUserController(accountservice), Roles.USER);
            path("account/{id}", ()->{
                put("deposit", transactionController.deposit, Roles.USER);
                put("withdraw", transactionController.withdraw, Roles.USER);
                put("transfer", transactionController.transfer, Roles.USER);
            });

            path("auth", () -> {
                post("login", authController.login);
            });

        });

        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ErrorResponse response = new ErrorResponse(e.getMessage(), 404);
            ctx.status(404);
            ctx.json(response);
        });

        app.exception(NullPointerException.class, (e, ctx) -> {
            ErrorResponse response = new ErrorResponse("Null check", 500);
            ctx.status(500);
            ctx.json(response);
        });

        app.exception(UnauthorizedResponse.class, (e, ctx) -> {
            ErrorResponse response = new ErrorResponse(e.getMessage(), e.getStatus());
            ctx.status(e.getStatus());
            ctx.json(response);
        });

        app.exception(Exception.class, (e, ctx) -> {
            ErrorResponse response = new ErrorResponse("Something went wrong.", 500);
            ctx.status(500);
            ctx.json(response);
        });

        app.exception(Exception.class, (e, ctx) -> {
            ForbiddenResponse response = new ForbiddenResponse("User not allowed to modify records");
            ctx.status(403);
            ctx.json(response);
        });

        app.exception(Exception.class, (e, ctx) -> {
            IllegalArgumentException response = new IllegalArgumentException("You cannot deposit negative amounts");
            ctx.status(403);
            ctx.json(response);
        });
    }
}
