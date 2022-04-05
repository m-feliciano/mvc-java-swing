package br.com.feliciano.mvc.controller;

import java.sql.Connection;

import br.com.feliciano.mvc.dao.UserDAO;
import br.com.feliciano.mvc.entities.User;
import br.com.feliciano.mvc.infra.ConnectionFactory;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.userDAO = new UserDAO(conn);
    }

    public User findById(int id) {
        return this.userDAO.findById(id);
    }

    public void update(User user) {
        this.userDAO.update(user);
    }

}
