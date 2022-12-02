package ui;

import model.Account;
import model.EventLog;
import model.Transaction;
import model.TransactionType;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// runs graphical user interface of DollarDollar
public class AppGUI extends JFrame implements ActionListener {

    private Account account;
    private Font font;
    private int counter;
    private int height;
    private int width;
    private ImageIcon imgIcon;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel displayPanel;
    private JPanel newTransPanel;
    private JPanel removeTransPanel;
    private JPanel quitPanel;

    private JLabel displayText;
    private JLabel transText;

    private JMenuItem quit;

    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;

    private AppGUI() {
        account = new Account("Stellar's account");

        font = new Font("Monospaced", Font.PLAIN, 12);
        imgIcon = new ImageIcon("./data/piggy.png");
        height = 200;
        width = 700;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        mainPanel.setLayout(new GridLayout(0,2));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(width, height));

        menuPanelSetUp();
        displayPanelSetUp();
        addQuitBar();

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setTitle("Welcome to DollarDollar");
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //MODIFIES: this
    //EFFECTS: implements menuBar with Quit
    private void addQuitBar() {
        JMenuBar quitBar = new JMenuBar();

        quit = new JMenuItem("Quit");
        quit.setFont(font);
        quit.addActionListener(this);
        quitBar.add(quit);

        frame.setJMenuBar(quitBar);
    }

    //MODIFIES: this
    //EFFECTS: sets up display panel for transactions
    private void displayPanelSetUp() {
        JPanel padding = new JPanel();
        padding.setBackground(Color.WHITE);
        displayPanel = new JPanel();
        displayPanel.setBackground(new Color(252, 219, 212));
        displayTransactions();
        displayPanel.add(displayText);
        mainPanel.add(displayPanel);

    }

    //MODIFIES: this
    //EFFECTS: sets up panel with all the menu buttons
    private void menuPanelSetUp() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4,1));
        menuPanel.setBackground(Color.WHITE);
        addMenuButtons();
        mainPanel.add(menuPanel);
    }


    //EFFECTS: helper method for menu button (sets colour, font and position)
    private void setButton(JButton b) {
        b.setFont(font);
        b.setHorizontalTextPosition(JButton.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: implements menu buttons
    private void addMenuButtons() {
        addButton = new JButton("Add a New Transaction");
        setButton(addButton);
        menuPanel.add(addButton);
        addButton.addActionListener(this);

        removeButton = new JButton("Remove a Transaction");
        setButton(removeButton);
        menuPanel.add(removeButton);
        removeButton.addActionListener(this);

        saveButton = new JButton("Save Transactions");
        setButton(saveButton);
        menuPanel.add(saveButton);
        saveButton.addActionListener(this);

        loadButton = new JButton("Load Previous Transactions");
        setButton(loadButton);
        menuPanel.add(loadButton);
        loadButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quit) {
            quitApp();
        } else if (e.getSource() == addButton) {
            addTransaction();
        } else if (e.getSource() == removeButton) {
            if (account.getTransactions().size() != 0) {
                removeTransaction();
            } else {
                JOptionPane.showMessageDialog(displayPanel,
                        "You haven't entered any transactions yet! ",
                        "System Error", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource() == saveButton) {
            saveTransactions();
        } else if (e.getSource() == loadButton) {
            loadTransactions();
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a transaction to account
    public void addTransaction() {
        newTransPanel = new JPanel();
        JTextField amount = new JTextField(5);
        JLabel amountLabel = new JLabel("Enter an amount: ");
        amountLabel.setFont(font);
        newTransPanel.add(amountLabel);
        newTransPanel.add(amount);

        JTextField type = new JTextField(10);
        JLabel typeLabel = new JLabel("Enter income or expense: ");
        typeLabel.setFont(font);
        newTransPanel.add(typeLabel);
        newTransPanel.add(type);

        int input = JOptionPane.showConfirmDialog(mainPanel, newTransPanel, "New Transaction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);


        if (input == JOptionPane.OK_OPTION) {
            if (type.getText().equals("income")) {
                Transaction t = new Transaction(TransactionType.INCOME, Double.parseDouble(amount.getText()));
                account.addTransaction(t);
            } else if (type.getText().equals("expense")) {
                Transaction t = new Transaction(TransactionType.EXPENSE, Double.parseDouble(amount.getText()));
                account.addTransaction(t);
            }
        }

        displayTransactions();
    }


    //MODIFIES: this
    //EFFECTS: removes chosen transaction from account
    public void removeTransaction() {
        removeTransPanel = new JPanel();
        JTextField id = new JTextField(5);
        id.setFont(font);
        JLabel idMsg = new JLabel("Enter the ID of the transaction that you would like to remove: ");
        idMsg.setFont(font);
        removeTransPanel.add(idMsg);
        removeTransPanel.add(id);

        int input = JOptionPane.showConfirmDialog(mainPanel, removeTransPanel, "Remove Transaction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        int intID = Integer.parseInt(id.getText());

        if (input == JOptionPane.OK_OPTION) {
            Transaction deletedTrans = account.getTransactions().get(intID - 1);
            account.deleteTransaction(deletedTrans);
            JOptionPane.showMessageDialog(displayPanel, "Deleted Transaction",
                    "Update", JOptionPane.PLAIN_MESSAGE);
        }

        displayTransactions();
    }

    //MODIFIES: this
    //EFFECTS: saves transactions from current session to file
    public void saveTransactions() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();

            JOptionPane.showMessageDialog(displayPanel, "Saved transactions to " + JSON_STORE,
                    "Saved Transactions", JOptionPane.PLAIN_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(displayPanel, "Failed to save transactions to " + JSON_STORE,
                    "System Error", JOptionPane.PLAIN_MESSAGE);
        }

        displayTransactions();
    }

    //MODIFIES: this
    //EFFECTS: loads previously saved transactions from file
    public void loadTransactions() {
        try {
            account = jsonReader.read();

            JOptionPane.showMessageDialog(displayPanel, "Load previous transactions from  " + JSON_STORE,
                    "Loaded Transactions", JOptionPane.PLAIN_MESSAGE);

            displayPanel.removeAll();
            displayTransactions();

            revalidate();
            repaint();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(displayPanel, "Failed to load transactions from " + JSON_STORE,
                    "System Error", JOptionPane.PLAIN_MESSAGE);
        }

        displayTransactions();
    }

    //this method was referenced from StackOverFlow
    //https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
    //MODIFIES: this
    //EFFECTS: helper method for resizing icon
    public ImageIcon makeIcon(ImageIcon icon) {
        Image i = icon.getImage();
        Image scaled = i.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaled);
        return icon;
    }

    //MODIFIES: this
    //EFFECTS: pop-up message to ensure user is ready to quit
    public void quitApp() {
        quitPanel = new JPanel();
        quitPanel.setLayout(new GridLayout(2, 1));
        JLabel quitReminder = new JLabel("Reminder to save your transactions from current session!");
        quitReminder.setFont(font);
        JLabel quitMsg = new JLabel("Ready to quit DollarDollar?");
        quitMsg.setFont(font);
        quitPanel.add(quitReminder);
        quitPanel.add(quitMsg);
        int input = JOptionPane.showConfirmDialog(mainPanel, quitPanel, "Quit",
                JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, makeIcon(imgIcon));

        if (input == JOptionPane.YES_OPTION) {
            printLog();
            System.exit(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: displays transactions on display panel
    public void displayTransactions() {
        displayText = new JLabel();
        if (account.getTransactions().size() == 0) {
            displayText.setText("You haven't entered any transactions yet...");

            displayPanel.removeAll();
            displayPanel.add(displayText);
        } else {
            displayPanel.removeAll();
            counter = 1;
            for (Transaction t : account.getTransactions()) {
                transText = new JLabel(counter + ": " + t.toString());
                counter++;
                displayPanel.add(transText);

            }
        }
        displayPanel.repaint();
        displayPanel.revalidate();
    }

    //EFFECTS: prints out event log onto console
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println((next.getDate() + " : " + next.getDescription()));
        }
    }


    public static void main(String[] args) {
        new AppGUI();
    }

}
