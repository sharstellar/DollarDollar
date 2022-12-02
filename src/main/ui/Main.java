package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

// runs DollarDollar application without GUI
public class Main extends JFrame {

    public static void main(String[] args) {
        try {
            new DollarDollar();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}
