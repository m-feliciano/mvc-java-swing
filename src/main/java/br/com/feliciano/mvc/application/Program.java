package br.com.feliciano.mvc.application;

import javax.swing.WindowConstants;

import br.com.feliciano.mvc.view.InventoryFrame;

public class Program {

    public static void main(String[] args) {
        InventoryFrame home = new InventoryFrame();
        home.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
