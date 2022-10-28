package persistence;

import model.Account;
import model.Transaction;
import model.TransactionType;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    void testWriterInvalidFile() {
        try {
            Account account = new Account("Stellar's account");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account account = new Account("Stellar's account");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            account = reader.read();
            assertEquals("Stellar's account", account.getName());
            assertEquals(0, account.getBalance());
            assertEquals(0, account.getTransactions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            Account account = new Account("Stellar's account");
            account.addTransaction(new Transaction(TransactionType.INCOME, 100));
            account.addTransaction(new Transaction(TransactionType.EXPENSE, 75));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(account);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            account = reader.read();
            assertEquals("Stellar's account", account.getName());
            assertEquals(25, account.getBalance());
            List<Transaction> transactions = account.getTransactions();
            assertEquals(2, transactions.size());
            checkTransaction(TransactionType.INCOME, 100.0, transactions.get(0));
            checkTransaction(TransactionType.EXPENSE, 75.0, transactions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
