package br.com.feliciano.mvc.infra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.feliciano.mvc.domain.entities.User;
import br.com.feliciano.mvc.infra.Query;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }


    public void update(User user) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_USER_UPDATE)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User findById(int id) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_USER_SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
                User user = null;
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                }
                return user;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
