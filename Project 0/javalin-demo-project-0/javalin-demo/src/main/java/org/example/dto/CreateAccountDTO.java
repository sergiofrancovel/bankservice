package org.example.dto;
import org.example.dao.InMemAccountDao;

import java.util.Random;

public class CreateAccountDTO {
    //InMemAccountDao account;
    private Integer accountNumber;
    private Double accountBalance;

    public CreateAccountDTO() {
        Random random = new Random();
        this.accountNumber = random.nextInt(899999999)+100000000;
        this.accountBalance = 0.0;

        //account.createAccount(accountNumber, accountBalance);
    }

    public CreateAccountDTO(Integer accountNumber, Double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance(){
        return accountBalance;
    }

}
