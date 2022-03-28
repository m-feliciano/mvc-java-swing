package controller;

import dao.AddressDAO;
import entities.Address;
import infra.ConnectionFactory;

import java.sql.Connection;

public class AddressController {

    private AddressDAO AddressDAO;

    public AddressController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.AddressDAO = new AddressDAO(conn);
    }

    public Address findById(int id) {
        return this.AddressDAO.findById(id);
    }

    public void update(Address address) {
        this.AddressDAO.update(address);
    }

}
