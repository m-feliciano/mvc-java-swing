package controller;

import java.sql.Connection;
import java.util.List;

import dao.CategoryDAO;
import entities.Category;
import infra.ConnectionFactory;

public class CategoryController {

    private CategoryDAO categoryDAO;

    public CategoryController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.categoryDAO = new CategoryDAO(conn);
    }

    public void save(Category category) {
        this.categoryDAO.save(category);
    }

    public void delete(int id) {
        this.categoryDAO.delete(id);
    }

    public Category findById(int id) {
        return this.categoryDAO.findById(id);
    }

    public void update(Category category) {
        this.categoryDAO.update(category);
    }

    public List<Category> list() {
        return this.categoryDAO.list();
    }

    public List<Category> listProductByCategory() {
        return this.categoryDAO.listProductByCategory();
    }

}
