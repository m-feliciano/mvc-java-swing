package view.utils;

import javax.swing.*;

public class Message {

    private Message(){}

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "ok", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, "Error: " + message, "error", JOptionPane.ERROR_MESSAGE);
    }

}
