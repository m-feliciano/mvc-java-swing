package dao;

import entities.Address;
import infra.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO {

    private Connection conn;

    public AddressDAO(Connection conn) {
        this.conn = conn;
    }

    public void update(Address address) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_ADDRESS_UPDATE)) {
            ps.setString(1, address.getCep());
            ps.setString(2, address.getNumber());
            ps.setString(3, address.getPlace());
            ps.setString(4, address.getLocal());
            ps.setInt(5, address.getUserId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully update address");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Address findById(int id) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_ADDRESS_SELECT)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Address address = null;
                while (rs.next()) {
                    address = new Address();
                    address.setCep(rs.getString("cep"));
                    address.setNumber(rs.getString("number"));
                    address.setPlace(rs.getString("place"));
                    address.setLocal(rs.getString("local"));
                    address.setUserId(rs.getInt("user_id"));
                }
                return address;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
