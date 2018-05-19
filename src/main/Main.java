package main;

import main.presentation.VAT;

public class Main {

    /**
     * private c'tor to prevent instantiation
     */
    private Main() {
        // deliberately empty
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new VAT().initComponents();

    }
}
