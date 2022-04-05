package br.com.feliciano.mvc.controller;

import java.sql.Connection;
import java.util.List;

import br.com.feliciano.mvc.dao.ProductDAO;
import br.com.feliciano.mvc.entities.Product;
import br.com.feliciano.mvc.infra.ConnectionFactory;

public class ProductController {

    private final ProductDAO productDAO;

    public ProductController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.productDAO = new ProductDAO(conn);
    }

    public void save(Product product) {
        this.productDAO.save(product);
    }

    public void delete(int id) {
        this.productDAO.delete(id);
    }

    public List<Product> list() {
        return this.productDAO.list();
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
