package model;

import java.util.ArrayList;
import java.util.List;

// Represents a user with a list of transactions and balance (in dollars)
public class Account {
    private double balance;                     // the current balance of the account
    private List<Transaction> transactions;     // the current list of transactions of the account


    //EFFECTS: constructs an account with empty list of transactions and initial balance of 0
    public Account() {
        this.transactions = new ArrayList<>();
        this.balance = 0.00;
    }

    //MODIFIES: this
    //EFFECTS: adds a given transaction to the list of transactions under account and update balance corresponding to
    //         the transaction amount
    //         if the transaction was an income, add transaction amount to balance;
    //         if the transaction was an expense, delete transaction amount from balance;
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        if (transaction.getTransactionType() == TransactionType.INCOME) {
            this.balance = this.balance + transaction.getTransactionAmount();
        } else {
            this.balance = this.balance - transaction.getTransactionAmount();
        }
    }

    //MODIFIES: this
    //EFFECTS: removes a transaction from the list of transactions under account,
    //         if the transaction was an income, delete transaction amount from balance;
    //         if the transaction was an expense, add transaction amount back to balance;
    public void deleteTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        if (transaction.getTransactionType() == TransactionType.INCOME) {
            this.balance = this.balance - transaction.getTransactionAmount();
        } else {
            this.balance = this.balance + transaction.getTransactionAmount();
        }
    }

    //EFFECTS: returns the current balance of account
    public double getBalance() {
        return this.balance;
    }

    //EFFECTS: returns the current list of transactions recorded under account
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

}
