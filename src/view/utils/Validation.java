package view.utils;

import javax.swing.*;

public class Validation {

    public static boolean validate(JTextField nameTxt, JTextField descriptionTxt) {
        return !(nameTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty());
    }

    public static boolean validate(JTextField descriptionTxt) {
        return !(descriptionTxt.getText().isEmpty());
    }

    public static boolean validate(String str) {
        return str != null && !str.isBlank() && !str.isEmpty();
    }
}
