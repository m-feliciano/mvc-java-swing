package dao;

import entities.Product;
import infra.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private final Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public Product findById(int id) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                Product prod = null;
                while (rs.next()) {
                    prod = instantiateProduct(rs);
                }
                return prod;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> list() {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_SELECT)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> products = new ArrayList<>();
                Product prod;
                while (rs.next()) {
                    prod = instantiateProduct(rs);
                    products.add(prod);
                }
                return products;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void save(Product prod) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, prod.getName());
            ps.setString(2, prod.getDescription());
            ps.setBigDecimal(3, prod.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successufully added product");
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

    public void update(Product prod) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_UPDATE)) {
            ps.setString(1, prod.getName());
            ps.setString(2, prod.getDescription());
            ps.setBigDecimal(3, prod.getPrice());
            ps.setInt(4, prod.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Successfully update product");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(int id) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            int affectedRows = ps.getUpdateCount();
            if (affectedRows > 0) {
                System.out.println("Successfully delete product\nAffected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> getProductsByCategoryName(String name) {
        try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_BY_CATEGORY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> products = new ArrayList<>();
                Product prod = null;
                while (rs.next()) {
                    prod = instantiateProduct(rs);
                    products.add(prod);
                }
                return products;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Product instantiateProduct(ResultSet rs) throws SQLException {
        Product prod = new Product();
        prod.setId(rs.getInt(1));
        prod.setName(rs.getString(2));
        prod.setDescription(rs.getString(3));
        prod.setPrice(rs.getBigDecimal(4));
        prod.setRegisterDate(rs.getTimestamp(5));
        return prod;
    }

}
