package org.example.services;

import org.example.controllers.AccountAdminController;
import org.example.dao.AccountRepository;
import org.example.dao.InMemAccountDao;
import org.example.dto.CreateAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountService {
    private static final Logger logger=  LoggerFactory.getLogger(AccountAdminController.class);
    AccountRepository<Integer, Double> account;

    public AccountService(AccountRepository<Integer, Double> account) {
        this.account = account;
    }

    /**
     * Add account to the account repository
     */
    public void addAccountToRepo(CreateAccountDTO accountDTO){
        logger.info("Adding the created account to the repository");
        if(accountDTO!=null) {
            if(accountDTO.getAccountNumber()!=null) {
                account.createAccount(accountDTO.getAccountNumber(), accountDTO.getAccountBalance());
            }
        }else{
            throw new RuntimeException("Accounts cannot be null");
        }
    }

    /**
     * List all accounts
     */
    public String listAllAccounts(){
        logger.info("Listing all the accounts");
        String message="";
        for(Object elements : account.getAllAccounts().keySet()){
            message = "The account associated with the number: " + elements +
            " has a total balance of " + account.getAccountBalance((Integer) elements) +
            "\n" + message;
        }
        return message;
    }

    /**
     * List one account
     */
    public String listOneAccount(int id){
        logger.info("Listing one account");
        return "The account associated with the number : " + id +
                " has a total balance of " +account.getOneAccount(id);
    }

}
