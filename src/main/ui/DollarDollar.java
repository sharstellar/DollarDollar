package ui;

import model.Transaction;
import model.Account;
import model.TransactionType;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// DollarDollar application
public class DollarDollar {
    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner scanner;
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: runs the DollarDollar application
    public DollarDollar() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        this.account = new Account("Stellar's account");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDollarDollar();
    }

    //MODIFIES: this
    //EFFECTS: processes user's activity input (what they are hoping to do next)
    private void runDollarDollar() {
        boolean proceed = true;
        int choice;

        while (proceed) {
            mainMenu();
            choice = scanner.nextInt();

            if (choice == 7) {
                System.out.println("Would you like to save transactions from the current session?");
                String answer = scanner.next();
                if (answer == "yes") {
                    processChoice(5);
                } else {
                    proceed = false;
                }
            } else {
                processChoice(choice);
            }
        }

        System.out.println("bye!");
    }

    //MODIFIES: this
    //EFFECTS: processes user's choice to activity by application
    private void processChoice(int choice) {
        if (choice == 1) {
            displayTransactionList(account);
        } else if (choice == 2) {
            newTransaction(account);
        } else if (choice == 3) {
            removeTransaction(account);
        } else if (choice == 4) {
            viewBalance(account);
        } else if (choice == 5) {
            saveAccount();
        } else if (choice == 6) {
            loadAccount();
        } else {
            System.out.println("Please select a valid option number");
            runDollarDollar();
        }
        runDollarDollar();
    }



    //EFFECTS: displays the activity options to users
    private void mainMenu() {

        System.out.println("Main Menu");
        System.out.println("What can DollarDollar help you with?");
        System.out.println("1. Display My Transactions for Current Session");
        System.out.println("2. Add a Transaction");
        System.out.println("3. Delete a Transaction");
        System.out.println("4. View Balance");
        System.out.println("5. Save Transactions");
        System.out.println("6. Load Previous Transactions");
        System.out.println("7: Quit");
        System.out.println("");
        System.out.println("Enter option number: ");
    }

    //EFFECTS: displays a list of transactions of user
    public void displayTransactionList(Account transactionList) {
        System.out.println("Here is a list of all your transactions:");
        if (this.account.getTransactions().size() == 0) {
            System.out.println("You havn't entered any transaction yet!");
        }
        int counter = 1;
        for (Transaction t : this.account.getTransactions()) {
            System.out.println(counter + ": " + t);
            counter++;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a new transaction to user's list of transactions & update user's balance correspondingly
    public void newTransaction(Account transactionList) {

        System.out.println("Please select an option (income or expense)");
        String type = scanner.next();
        System.out.println(("Please enter an amount:"));
        Double amount = scanner.nextDouble();
        if (type.equals("income")) {
            Transaction newTrans = new Transaction(TransactionType.INCOME, amount);
            this.account.addTransaction(newTrans);
        } else if (type.equals("expense")) {
            Transaction newTrans = new Transaction(TransactionType.EXPENSE, amount);
            this.account.addTransaction(newTrans);
        }
        System.out.println("");
    }

    //MODIFIES: this
    //EFFECTS: deletes a transaction to user's list of transactions & update user's balance correspondingly
    public void removeTransaction(Account transactionList) {

        displayTransactionList(transactionList);
        System.out.println("Please enter the ID of the transaction you would like to remove: ");
        int id = scanner.nextInt();
        Transaction deletedTrans = transactionList.getTransactions().get(id - 1);
        this.account.deleteTransaction(deletedTrans);
        System.out.println("Updated!");
        displayTransactionList(transactionList);
        System.out.println("");
    }

    //EFFECTS: returns the user's current balance
    public void viewBalance(Account transactionList) {

        System.out.println("Here is your current balance:" + "$" + this.account.getBalance());
        System.out.println("");
    }

    //EFFECT: saves the account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved " + account.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded " + account.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}


