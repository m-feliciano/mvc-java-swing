package controller;

import java.sql.Connection;

import dao.AddressDAO;
import entities.Address;
import infra.ConnectionFactory;

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
