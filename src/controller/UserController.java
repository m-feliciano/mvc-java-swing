package controller;

import dao.UserDAO;
import entities.User;
import infra.ConnectionFactory;

import java.sql.Connection;

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
