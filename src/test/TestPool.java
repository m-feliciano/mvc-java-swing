package test;

import infra.ConnectionFactory;
import infra.exceptions.DbException;

public class TestPool {

    public static void main(String[] args) throws DbException {

        ConnectionFactory factory = new ConnectionFactory();

        for (int i = 0; i < 15; i++) {
            factory.getConnection();
            System.out.println("Connection number: " + i);
        }
    }

}
