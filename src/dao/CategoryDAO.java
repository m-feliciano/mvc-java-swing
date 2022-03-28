package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Category;
import entities.Product;
import infra.Query;

public class CategoryDAO {

	private Connection conn;

	public CategoryDAO(Connection conn) {
		this.conn = conn;
	}

	public List<Category> getCategories() {
		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_CATEGORIES)) {
			try (ResultSet rs = ps.executeQuery()) {
				List<Category> categories = new ArrayList<>();
				Category cat = null;
				while (rs.next()) {
					cat = new Category();
					cat.setId(rs.getInt(1));
					cat.setName(rs.getString(2));
					categories.add(cat);
				}
				return categories;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public List<Category> listProductByCategory() {

		List<Category> categories = new ArrayList<>();
		Category last = null;

		try (PreparedStatement ps = conn.prepareStatement(Query.SQL_PRODUCTS_BY_CATEGORY)) {
			ps.execute();
			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					if (last == null || !(last.getName().equals(rs.getString(2)))) {
						Category cat = new Category();
						cat.setId(rs.getInt(1));
						cat.setName(rs.getString(2));
						last = cat;
						categories.add(cat);
					}
					Product prod = new Product();
					prod.setId(rs.getInt(3));
					prod.setName(rs.getString(4));
					prod.setDescription(rs.getString(5));
					prod.setPrice(rs.getBigDecimal(6));
					prod.setRegisterDate(rs.getTimestamp(7));
					last.addProduct(prod);
				}
				return categories;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
