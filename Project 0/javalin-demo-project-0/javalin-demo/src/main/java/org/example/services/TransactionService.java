package org.example.services;

import org.example.controllers.AccountAdminController;
import org.example.dao.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionService {
    private static final Logger logger=  LoggerFactory.getLogger(AccountAdminController.class);
    AccountRepository<Integer, Double> account;

    public TransactionService(AccountRepository<Integer, Double> account) {
        this.account = account;
    }

    /**
     * Withdraw money from account
     */
    public String withdrawMoney(Integer id, Double amount){
        logger.info("Withdrawing money from account");
        Double currentAmount = account.getAccountBalance(id);
        Double newAmount = currentAmount - amount;
        if(newAmount<0){
            return "You cannot withdraw more money than what is in the account";
        }else {
            account.setAccountBalance(id, newAmount);
            return amount + " was withdrawn from you account. Your new balance " +
                    "is " + newAmount;
        }
    }
    /**
     * Deposit money to account
     */
    public String depositMoney(Integer id, Double amount){
        if(amount>0) {
            logger.info("Depositing money to account");
            Double currentAmount = account.getAccountBalance(id);
            Double newAmount = currentAmount + amount;
            account.setAccountBalance(id, newAmount);
            return amount + " was deposited into your account. Your new balance " +
                    "is " + newAmount;
        }else{
            throw new IllegalArgumentException("You cannot deposit negative amounts");
        }
    }

    public String transferMoney(Integer accountFrom, Integer accountTo, Double amount){
        logger.info("Transferring money from account to account");
        Double accountBalance = account.getAccountBalance(accountFrom);
        if(accountBalance>0) {
            Double newAmount = accountBalance - amount;
            account.setAccountBalance(accountFrom, accountBalance - amount);
            account.setAccountBalance(accountTo, newAmount);
            return "The account associated with the number " + accountFrom + " transferred " +
                    amount + " to account " + accountTo + ". Your current balance  is " +
                    account.getAccountBalance(accountTo);
        }else{
            throw new IllegalArgumentException("Account is out of balance");
        }
    }
}
