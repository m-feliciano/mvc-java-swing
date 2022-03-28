package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Inventory;
import entities.Product;
import infra.Query;

public class InventoryDAO {

	private Connection conn;

	public InventoryDAO(Connection conn) {
		this.conn = conn;
	}

	public Inventory findById(int id) {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_SELECT_BY_ID)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				Inventory inventory = null;
				while (rs.next()) {
					inventory = new Inventory();
					inventory.setId(rs.getInt(1));
					inventory.setProductId(rs.getInt(2));
					inventory.setCategoryId(rs.getInt(3));
				}
				return inventory;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Inventory> get() {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_SELECT)) {
			try (ResultSet rs = ps.executeQuery()) {

				List<Inventory> inventories = new ArrayList<>();

				Inventory inventory = null;
				while (rs.next()) {
					inventory = new Inventory();
					inventory.setId(rs.getInt(1));
					inventory.setProductId(rs.getInt(2));
					inventory.setCategoryId(rs.getInt(3));
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
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCT_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, inventory.getProductId());
			ps.setInt(2, inventory.getCategoryId());

			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Successufully added constraint");
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
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_UPDATE)) {
			ps.setInt(1, inventory.getProductId());
			ps.setInt(2, inventory.getCategoryId());
			ps.setInt(3, inventory.getId());

			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Successufully update constraint");
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
				System.out.println("Successufully delete constraint\nAffected rows: " + affectedRows);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Product> getProductsByCategoryId(int id) {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_BY_CATEGORY_NAME)) {

			ps.setInt(1, id);

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
