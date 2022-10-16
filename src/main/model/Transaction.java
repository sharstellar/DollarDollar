package model;

//Represent a single transaction
public class Transaction {
    private TransactionType transType;       // whether the transaction was an income or an expense
    private double amount;                   // the transaction amount

    //EFFECTS: creates a transaction with corresponding transaction type (income or expense) and given amount
    public Transaction(TransactionType transType, double amount) {
        this.transType = transType;
        this.amount = amount;
    }

    //EFFECTS: returns the transaction amount
    public double getTransactionAmount() {
        return this.amount;
    }

    //EFFECTS: returns the transaction type (income or expense)
    public TransactionType getTransactionType() {
        return this.transType;
    }

    //EFFECTS: returns transaction in sentence form "Transaction Type:(transaction type), Amount:(transaction amount)"
    @Override
    public String toString() {
        return "Transaction Type:" + transType + ", Amount:" + amount;
    }
}
