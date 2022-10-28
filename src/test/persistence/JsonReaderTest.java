package persistence;

import model.Account;
import model.Transaction;
import model.TransactionType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account account = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account account = reader.read();
            assertEquals("Stellar's account", account.getName());
            assertEquals(0, account.getTransactions().size());
            assertEquals(0, account.getBalance());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccount() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account account = reader.read();
            assertEquals("Stellar's account", account.getName());
            assertEquals(25, account.getBalance());
            List<Transaction> transactions = account.getTransactions();
            assertEquals(2, transactions.size());
            checkTransaction(TransactionType.INCOME, 100.0, transactions.get(0));
            checkTransaction(TransactionType.EXPENSE, 75.0, transactions.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
