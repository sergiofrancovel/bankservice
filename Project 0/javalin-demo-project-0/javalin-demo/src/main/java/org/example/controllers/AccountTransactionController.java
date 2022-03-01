package org.example.controllers;

import io.javalin.http.Handler;
import org.example.dao.AccountRepository;
import org.example.dao.InMemAccountDao;
import org.example.dto.TransactionDTO;
import org.example.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTransactionController {
    private static final Logger logger=  LoggerFactory.getLogger(AccountAdminController.class);
    private TransactionService service;

    public AccountTransactionController(TransactionService transactionService){
        this.service = transactionService;
    }

    /**
     * This handler is used to deposit money into the account
     */
    public Handler deposit = (ctx) -> {
        logger.info("Depositing money into the account");
        try {
            TransactionDTO transactionDTO = ctx.bodyAsClass(TransactionDTO.class);
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            ctx.result(service.depositMoney(id, transactionDTO.getAmountTransferred()));
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
    };

    /**
     * This handler is used to withdraw money from a specific account
     */
    public Handler withdraw = (ctx) -> {
        logger.info("Withdrawing money into the account");
        TransactionDTO transactionDTO = ctx.bodyAsClass(TransactionDTO.class);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        ctx.result(service.withdrawMoney(id, transactionDTO.getAmountTransferred()));
    };

    public Handler transfer = (ctx) -> {
        logger.info("Transferring money from account to other account");
        TransactionDTO transactionDTO = ctx.bodyAsClass(TransactionDTO.class);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        ctx.result(service.transferMoney(id, transactionDTO.getAccountId(), transactionDTO.getAmountTransferred()));
    };
}
