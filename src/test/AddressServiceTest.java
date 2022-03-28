package test;

import entities.Address;
import infra.exceptions.DbException;

public class AddressServiceTest {

    public static void main(String[] args) throws DbException {

        Address address = new Address("04707000", "250", 1);
        System.out.println(address);
    }

}
