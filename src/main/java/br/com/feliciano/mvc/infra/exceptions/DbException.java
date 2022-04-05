package br.com.feliciano.mvc.infra.exceptions;

public class DbException extends Exception {

    private static final long serialVersionUID = 1L;

    public DbException(String msg) {
        super(msg);
    }
}
