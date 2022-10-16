package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {
    private Account testAccount;
    private Transaction transA;
    private Transaction transB;
    private Transaction transC;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        transA = new Transaction(TransactionType.INCOME, 200.00);
        transB = new Transaction(TransactionType.EXPENSE, 50.00);
        transC = new Transaction(TransactionType.EXPENSE, 100);
    }

    @Test
    void constructorTest(){
        assertEquals(0, testAccount.getTransactions().size());
        assertEquals(0.0, testAccount.getBalance());
    }

    @Test
    void addTransactionPositiveBalanceTest() {
        testAccount.addTransaction(transA);
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(200.00, testAccount.getBalance());
        assertTrue(testAccount.getTransactions().contains(transA));
    }

    @Test
    void addTransactionNegativeBalanceTest() {
        testAccount.addTransaction(transB);
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(-50.00, testAccount.getBalance());
        assertTrue(testAccount.getTransactions().contains(transB));
    }

    @Test
    void addMultipleTransactionsTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        assertEquals(2, testAccount.getTransactions().size());
        assertEquals(150.00, testAccount.getBalance());
        assertEquals(transA, testAccount.getTransactions().get(0));
        assertEquals(transB, testAccount.getTransactions().get(1));
    }

    @Test
    void deleteTransactionsTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        testAccount.deleteTransaction(transB);
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(200, testAccount.getBalance());
        assertEquals(transA, testAccount.getTransactions().get(0));
    }

    @Test
    void deleteMultipleTransactionsTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        testAccount.addTransaction(transC);
        testAccount.deleteTransaction(transA);
        testAccount.deleteTransaction(transC);
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(-50.00, testAccount.getBalance());
        assertEquals(transB, testAccount.getTransactions().get(0));
    }




}

