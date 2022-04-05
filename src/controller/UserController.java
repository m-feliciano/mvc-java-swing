package controller;

import java.sql.Connection;

import dao.UserDAO;
import entities.User;
import infra.ConnectionFactory;

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
