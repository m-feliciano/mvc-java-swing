package br.com.feliciano.mvc.controller;

import java.sql.Connection;

import br.com.feliciano.mvc.dao.AddressDAO;
import br.com.feliciano.mvc.entities.Address;
import br.com.feliciano.mvc.infra.ConnectionFactory;

public class AddressController {

    private AddressDAO addressDAO;

    public AddressController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.addressDAO = new AddressDAO(conn);
    }

    public Address findById(int id) {
        return this.addressDAO.findById(id);
    }

    public void update(Address address) {
        this.addressDAO.update(address);
    }

}
