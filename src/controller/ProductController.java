package controller;

import dao.ProductDAO;
import entities.Product;
import infra.ConnectionFactory;

import java.sql.Connection;
import java.util.List;

public class ProductController {

    private ProductDAO productDAO;

    public ProductController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.productDAO = new ProductDAO(conn);
    }

    public void save(Product product) {
        this.productDAO.insert(product);
    }

    public void delete(int id) {
        this.productDAO.delete(id);
    }

    public List<Product> list() {
        return this.productDAO.getProducts();
    }

    public List<Product> getProductsByCategoryName(String name) {
        return this.productDAO.getProductsByCategoryName(name);
    }

    public Product findById(int id) {
        return this.productDAO.findById(id);
    }

    public void update(Product prod) {
        this.productDAO.update(prod);
    }

}
