package org.example.controllers;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.example.App;
import org.example.dto.CreateAccountDTO;
import org.example.services.AccountService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AccountAdminController implements CrudHandler {
    private static final Logger logger=  LoggerFactory.getLogger(AccountAdminController.class);
    private AccountService service;


    public AccountAdminController(AccountService service) {
        this.service = service;
    }

    /**
     * Create an account and post
     */
    @Override
    public void create(@NotNull Context context) {
        logger.info("Account was just created");
        CreateAccountDTO newAccount =new CreateAccountDTO();
        service.addAccountToRepo(newAccount);
        int id = newAccount.getAccountNumber();
        context.header("Location", "http://localhost:8080/admin/account/" + id );
    }

    /**
     * This method has no implementation
     */
    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        logger.error("User not authorized to delete records");
        throw new ForbiddenResponse("User not authorized to delete records");
    }

    /**
     * Get all accounts
     */
    @Override
    public void getAll(@NotNull Context context) {
        logger.info("Getting all accounts");
        try {
            context.res.sendRedirect("http://localhost:8080/account");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get an account's balance by its account number
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        logger.info("Getting one account");
        try {
            int id = context.pathParamAsClass("id",Integer.class).get();
            context.res.sendRedirect("http://localhost:8080/account/" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method had no implementation
     */
    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        logger.error("User not authorized to modify records");
        throw new ForbiddenResponse("User not authorized to modify records");
    }
}
