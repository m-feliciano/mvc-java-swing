package controller;

import dao.CategoryDAO;
import entities.Category;
import infra.ConnectionFactory;

import java.sql.Connection;
import java.util.List;

public class CategoryController {

    private CategoryDAO categoryDAO;

    public CategoryController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.categoryDAO = new CategoryDAO(conn);
    }

    public List<Category> list() {
        return this.categoryDAO.getCategories();
    }

    public List<Category> listProductByCategory() {
        return this.categoryDAO.listProductByCategory();
    }

}
