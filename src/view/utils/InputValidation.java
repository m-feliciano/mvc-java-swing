package view.utils;

import javax.swing.JTextField;

public  class InputValidation {

    private InputValidation(){}

    public static boolean validate(JTextField nameTxt, JTextField descriptionTxt) {
        return !(nameTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty());
    }

    public static boolean validate(JTextField jTextField) {
        return !jTextField.getText().isEmpty();
    }

    public static boolean validate(String str) {
        return !(str == null || str.isBlank() || str.isEmpty());
    }
}
