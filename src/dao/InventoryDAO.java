package dao;

import entities.Inventory;
import infra.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    private final Connection conn;

    public InventoryDAO(Connection conn) {
        this.conn = conn;
    }

//    public Inventory findById(int id) {
//        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_SELECT_BY_ID)) {
//
//            ps.setInt(1, id);
//
//            try (ResultSet rs = ps.executeQuery()) {
//
//                Inventory inventory = null;
//                while (rs.next()) {
//                    inventory = new Inventory();
//                    inventory.setId(rs.getInt(1));
//                    inventory.setProductId(rs.getInt(2));
//                    inventory.setCategoryId(rs.getInt(3));
//                }
//                return inventory;
//            } catch (Exception e) {
//                throw new RuntimeException(e.getMessage());
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }

    public List<Inventory> get() {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_SELECT_LIST)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Inventory> inventories = new ArrayList<>();
                Inventory inventory;
                while (rs.next()) {
                    inventory = new Inventory();
                    inventory.setId(rs.getInt("id"));
                    inventory.setProductId(rs.getInt("product_id"));
                    inventory.setCategoryId(rs.getInt("category_id"));
                    inventory.setQuantity(rs.getInt("quantity"));
                    inventory.setDescription(rs.getString("description"));
                    inventories.add(inventory);
                }
                return inventories;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void save(Inventory inventory) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inventory.getProductId());
            ps.setInt(2, inventory.getCategoryId());
            ps.setInt(3, inventory.getQuantity());
            ps.setString(4, inventory.getDescription());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully added constraint");
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Inserted ID: " + id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void update(Inventory inventory) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_UPDATE)) {
            ps.setInt(1, inventory.getProductId());
            ps.setInt(2, inventory.getCategoryId());
            ps.setInt(3, inventory.getQuantity());
            ps.setString(4, inventory.getDescription());
            ps.setInt(5, inventory.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully update constraint");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(int id) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            int affectedRows = ps.getUpdateCount();
            if (affectedRows > 0) {
                System.out.println("Successfully delete constraint\nAffected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
