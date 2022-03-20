package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Product;
import infra.Query;

public class ProductDAO {

	private Connection conn;

	public ProductDAO(Connection conn) {
		this.conn = conn;
	}

	public Product findById(int id) {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_SELECT_BY_ID)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				Product prod = null;
				while (rs.next()) {
					prod = new Product();
					prod.setId(rs.getInt(1));
					prod.setName(rs.getString(2));
					prod.setDescription(rs.getString(3));
					prod.setRegisterDate(rs.getTimestamp(4)); //
				}
				return prod;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Product> getProducts() {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_SELECT)) {
			try (ResultSet rs = ps.executeQuery()) {

				List<Product> products = new ArrayList<>();
				Product prod = null;

				while (rs.next()) {
					prod = new Product();
					prod.setId(rs.getInt(1));
					prod.setName(rs.getString(2));
					prod.setDescription(rs.getString(3));
					prod.setRegisterDate(rs.getTimestamp(4));
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

	public void insert(Product prod) {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, prod.getName());
			ps.setString(2, prod.getDescription());

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
			ps.setInt(3, prod.getId());

			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Successufully update product");
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
				System.out.println("Successufully delete product\nAffected rows: " + affectedRows);
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
					prod = new Product();
					prod.setId(rs.getInt(1));
					prod.setName(rs.getString(2));
					prod.setDescription(rs.getString(3));
					prod.setRegisterDate(rs.getTimestamp(4));
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

}
