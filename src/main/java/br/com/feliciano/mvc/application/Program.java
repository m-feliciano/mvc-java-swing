package br.com.feliciano.mvc.application;

import javax.swing.WindowConstants;

import br.com.feliciano.mvc.view.InventoryView;

public class Program {

    public static void main(String[] args) {
        InventoryView home = new InventoryView();
        home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
