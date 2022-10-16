package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void addTransactionIncomeTest() {
        testAccount.addTransaction(transA);
        assertEquals(TransactionType.INCOME, transA.getTransactionType());
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(200.00, testAccount.getBalance());
        assertTrue(testAccount.getTransactions().contains(transA));
        assertNotSame(TransactionType.EXPENSE, transA.getTransactionType());
    }

    @Test
    void addTransactionExpenseTest() {
        testAccount.addTransaction(transB);
        assertNotSame(TransactionType.INCOME, transB.getTransactionType());
        assertEquals(TransactionType.EXPENSE, transB.getTransactionType());
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(-50.00, testAccount.getBalance());
        assertTrue(testAccount.getTransactions().contains(transB));
    }

    @Test
    void addMultipleTransactionsTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        assertEquals(TransactionType.INCOME, transA.getTransactionType());
        assertNotSame(TransactionType.EXPENSE, transA.getTransactionType());
        assertEquals(TransactionType.EXPENSE, transB.getTransactionType());
        assertNotSame(TransactionType.INCOME, transB.getTransactionType());
        assertEquals(2, testAccount.getTransactions().size());
        assertEquals(150.00, testAccount.getBalance());
        assertEquals(transA, testAccount.getTransactions().get(0));
        assertEquals(transB, testAccount.getTransactions().get(1));
    }

    @Test
    void deleteTransactionsExpenseTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        testAccount.deleteTransaction(transB);
        assertNotSame(TransactionType.INCOME, transB.getTransactionType());
        assertEquals(TransactionType.EXPENSE, transB.getTransactionType());
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(200, testAccount.getBalance());
        assertEquals(transA, testAccount.getTransactions().get(0));
    }

    @Test
    void deleteTransactionsIncomeTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        testAccount.deleteTransaction(transA);
        assertEquals(TransactionType.INCOME, transA.getTransactionType());
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(-50.00, testAccount.getBalance());
        assertEquals(transB, testAccount.getTransactions().get(0));
        assertNotSame(TransactionType.EXPENSE, transA.getTransactionType());
    }

    @Test
    void deleteMultipleTransactionsTest() {
        testAccount.addTransaction(transA);
        testAccount.addTransaction(transB);
        testAccount.addTransaction(transC);
        testAccount.deleteTransaction(transA);
        testAccount.deleteTransaction(transC);
        assertEquals(TransactionType.INCOME, transA.getTransactionType());
        assertNotSame(TransactionType.EXPENSE, transA.getTransactionType());
        assertEquals(TransactionType.EXPENSE, transC.getTransactionType());
        assertNotSame(TransactionType.INCOME, transC.getTransactionType());
        assertEquals(1, testAccount.getTransactions().size());
        assertEquals(-50.00, testAccount.getBalance());
        assertEquals(transB, testAccount.getTransactions().get(0));
    }

}

