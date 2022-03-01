package org.example.controllers;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import org.example.services.AccountService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountUserController implements CrudHandler {
    AccountService service;
    private static final Logger logger=  LoggerFactory.getLogger(AccountAdminController.class);

    public AccountUserController(AccountService service) {
        this.service = service;
    }
    /**
     * This method has no implementation
     */
    @Override
    public void create(@NotNull Context context) {
        logger.error("User not authorized to create accounts");
        throw new ForbiddenResponse("User not authorized to create accounts");
    }

    /**
     * This method has no implementation
     */
    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        logger.error("User not authorized to delete accounts");
        throw new ForbiddenResponse("User not authorized to delete records");
    }

    /**
     * List all accounts
     */
    @Override
    public void getAll(@NotNull Context context) {
        logger.info("Getting all the accounts for the user");
        context.result(service.listAllAccounts());
    }

    /**
     * Get one account by its account number
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        logger.info("Getting one account for the user");
        int id = context.pathParamAsClass("id", Integer.class).get();
        context.result(service.listOneAccount(id));
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {

    }
}
