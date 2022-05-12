package br.com.feliciano.mvc.view.utils;

import javax.swing.JOptionPane;

import com.mchange.util.AssertException;

public class Message {

    private Message(){
    	throw new AssertException("This class must not be instantiated.");
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "ok", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, "Error: " + message, "error", JOptionPane.ERROR_MESSAGE);
    }

}
