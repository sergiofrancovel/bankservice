package org.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemAccountDao implements AccountRepository<Integer, Double>{

    private int accountNumber;
    private double accountBalance;

    Map<Integer, Double> account = new HashMap<>();

    public void createAccount(Integer accountNumber, Double accountBalance) {
        if(accountNumber!=null) {
            this.accountNumber = accountNumber;
            this.accountBalance = accountBalance;
            account.put(accountNumber, accountBalance);
        }else{
            throw new RuntimeException("Account number cannot be null");
        }
    }

    @Override
    public Map<Integer, Double> getAllAccounts(){
        return account;
    }

    @Override
    public Double getOneAccount(Integer id){
        return account.get(id);
    }

    @Override
    public int getAccountNumber() {
        return this.accountNumber;
    }

    @Override
    public double getAccountBalance(Integer integer) {
        return account.get(integer);
    }

    @Override
    public void deleteAccountByNumber(Integer integer) {
        account.remove(integer);
    }

    @Override
    public void setAccountBalance(Integer integer, Double bal) {
        account.put(integer, bal);
    }


}
