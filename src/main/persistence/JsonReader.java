package persistence;

import model.Account;
import model.Transaction;
import model.TransactionType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Account account = new Account(name);
        addTrans(account, jsonObject);
        return account;
    }

    // MODIFIES: account
    // EFFECTS: parses transactions from JSON object and adds them to account
    private void addTrans(Account account, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTrans = (JSONObject) json;
            addTran(account, nextTrans);
        }
    }

    // MODIFIES: account
    // EFFECTS: parses transaction from JSON object and adds it to account
    private void addTran(Account account, JSONObject jsonObject) {
        TransactionType transType = TransactionType.valueOf(jsonObject.getString("transaction type"));
        Double amount = jsonObject.getDouble("amount");
        Transaction transaction = new Transaction(transType, amount);
        account.addTransaction(transaction);
    }
}
