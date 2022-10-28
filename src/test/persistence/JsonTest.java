package persistence;

import model.Transaction;
import model.TransactionType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransaction(TransactionType transactionType, Double amount, Transaction transaction) {
        assertEquals(amount, transaction.getTransactionAmount());
        assertEquals(transactionType, transaction.getTransactionType());
    }
}
