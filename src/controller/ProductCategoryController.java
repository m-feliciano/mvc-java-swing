package controller;

import java.sql.Connection;
import java.util.List;

import dao.ProductCategoryDAO;
import entities.Product;
import entities.ProductCategory;
import infra.ConnectionFactory;

public class ProductCategoryController {

	private ProductCategoryDAO productCategoryDAO;

	public ProductCategoryController() {
		Connection conn = new ConnectionFactory().getConnection();
		this.productCategoryDAO = new ProductCategoryDAO(conn);
	}

	public void save(ProductCategory productCategory) {
		this.productCategoryDAO.save(productCategory);
	}

	public void delete(int id) {
		this.productCategoryDAO.delete(id);
	}

	public List<ProductCategory> list() {
		return this.productCategoryDAO.get();
	}

	public List<Product> getProductsByCategoryId(int id) {
		return this.productCategoryDAO.getProductsByCategoryId(id);
	}

	public ProductCategory findById(int id) {
		return this.productCategoryDAO.findById(id);
	}

	public void update(ProductCategory productCategory) {
		this.productCategoryDAO.update(productCategory);
	}

}
