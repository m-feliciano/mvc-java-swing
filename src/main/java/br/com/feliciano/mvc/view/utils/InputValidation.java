package br.com.feliciano.mvc.view.utils;

import javax.swing.JTextField;

import com.mchange.util.AssertException;

public  class InputValidation {

    private InputValidation(){
    	throw new AssertException("This class must not be instantiated.");
    }

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
