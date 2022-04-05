package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Inventory;
import infra.Query;
import vo.InventoryVO;

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

    public List<InventoryVO> list() {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_INVENTORY_SELECT_LIST_JOIN)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<InventoryVO> inventoriesVo = new ArrayList<>();
                InventoryVO inventoryVo;
                while (rs.next()) {
                    inventoryVo = new InventoryVO();
                    inventoryVo.setId(rs.getInt("id"));
                    inventoryVo.setProductId(rs.getInt("p_id"));
                    inventoryVo.setProductName(rs.getString("p_name"));
                    inventoryVo.setProductPrice(rs.getBigDecimal("p_price"));
                    inventoryVo.setCategoryId(rs.getInt("c_id"));
                    inventoryVo.setCategoryName(rs.getString("c_name"));
                    inventoryVo.setQuantity(rs.getInt("quantity"));
                    inventoryVo.setDescription(rs.getString("description"));
                    inventoriesVo.add(inventoryVo);
                }
                return inventoriesVo;
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
