package org.example.dto;

public class TransactionDTO {
    Double amountTransferred;
    Integer accountId;

    public TransactionDTO() {
    }

    public TransactionDTO(Double amountTransferred) {
        this.amountTransferred = amountTransferred;
    }

    public TransactionDTO(Double amountTransferred, Integer accountId) {
        this.amountTransferred = amountTransferred;
        this.accountId = accountId;
    }

    public Double getAmountTransferred() {
        return amountTransferred;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
