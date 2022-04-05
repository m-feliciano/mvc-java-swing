package application;

import javax.swing.WindowConstants;

import view.InventoryFrame;

public class Program {

    public static void main(String[] args) {
        InventoryFrame home = new InventoryFrame();
        home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
