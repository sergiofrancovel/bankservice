package org.example.dao;

import java.util.List;
import java.util.Map;

public interface AccountRepository<ID, T>{
    public void createAccount(ID id, T bal);
    public Map getAllAccounts();
    public Double getOneAccount(ID id);
    public int getAccountNumber();
    public double getAccountBalance(ID id);
    public void deleteAccountByNumber(ID id);
    public void setAccountBalance(ID id, T bal);
}
