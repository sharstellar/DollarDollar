package ui;

import model.Transaction;
import model.Account;
import model.TransactionType;

import java.util.Scanner;

// DollarDollar application
public class DollarDollar {
    private Scanner scanner;
    private Account transactionList;

    //EFFECTS: runs the DollarDollar application
    public DollarDollar() {
        scanner = new Scanner(System.in);
        this.transactionList = new Account();
        runDollarDollar();
    }

    //MODIFIES: this
    //EFFECTS: processes user's activity input (what they are hoping to do next)
    private void runDollarDollar() {
        int choice;
        mainMenu();
        choice = scanner.nextInt();
        processChoice(choice);
    }

    //MODIFIES: this
    //EFFECTS: processes user's choice to activity by application
    private void processChoice(int choice) {
        if (choice == 1) {
            displayTransactionList(transactionList);
        } else if (choice == 2) {
            newTransaction(transactionList);
        } else if (choice == 3) {
            removeTransaction(transactionList);
        } else if (choice == 4) {
            viewBalance(transactionList);
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
        System.out.println("1. Display My Transaction List");
        System.out.println("2. Add a Transaction");
        System.out.println("3. Delete a Transaction");
        System.out.println("4. View Balance");
        System.out.println("");
        System.out.println("Enter option number: ");
    }

    //EFFECTS: displays a list of transactions of user
    public void displayTransactionList(Account transactionList) {
        System.out.println("Here is a list of all your transactions:");
        if (this.transactionList.getTransactions().size() == 0) {
            System.out.println("You havn't entered any transaction yet!");
        }
        this.transactionList.getTransactions().forEach(System.out::println);
        System.out.println("");
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
            this.transactionList.addTransaction(newTrans);
        } else if (type.equals("expense")) {
            Transaction newTrans = new Transaction(TransactionType.EXPENSE, amount);
            this.transactionList.addTransaction(newTrans);
        }
        System.out.println("");
    }

    //MODIFIES: this
    //EFFECTS: deletes a transaction to user's list of transactions & update user's balance correspondingly
    public void removeTransaction(Account transactionList) {

        displayTransactionList(transactionList);
        System.out.println("Please enter the row of the transaction you would like to remove: ");
        System.out.println("(count starting from one!)");
        int id = scanner.nextInt();
        Transaction deletedTrans = transactionList.getTransactions().get(id - 1);
        this.transactionList.deleteTransaction(deletedTrans);
        displayTransactionList(transactionList);
        System.out.println("");
    }

    //EFFECTS: returns the user's current balance
    public void viewBalance(Account transactionList) {

        System.out.println("Here is your current balance:" + "$" + this.transactionList.getBalance());
        System.out.println("");
    }
}


