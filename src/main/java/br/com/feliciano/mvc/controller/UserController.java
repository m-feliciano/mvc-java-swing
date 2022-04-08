package br.com.feliciano.mvc.controller;

import java.sql.Connection;

import br.com.feliciano.mvc.domain.entities.User;
import br.com.feliciano.mvc.infra.ConnectionFactory;
import br.com.feliciano.mvc.infra.dao.UserDAO;

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
