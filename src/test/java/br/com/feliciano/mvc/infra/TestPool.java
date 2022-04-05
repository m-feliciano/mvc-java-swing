package br.com.feliciano.mvc.infra;

import br.com.feliciano.mvc.infra.exceptions.DbException;

public class TestPool {

    public static void main(String[] args) throws DbException {

        ConnectionFactory factory = new ConnectionFactory();

        for (int i = 0; i < 15; i++) {
            factory.getConnection();
            System.out.println("Connection number: " + i);
        }
    }

}
