package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {
    private Transaction testTrans;

    @BeforeEach
    void setUp() {
        testTrans = new Transaction(TransactionType.INCOME, 100);
    }

    @Test
    void constructorTest() {
        assertEquals(testTrans.getTransactionType(), TransactionType.INCOME);
        assertEquals(100, testTrans.getTransactionAmount());
    }

    @Test
    void toStringTest() {
        String expected = "Transaction Type: INCOME, Amount: 100.0";
        assertEquals(expected, testTrans.toString());
    }
}
