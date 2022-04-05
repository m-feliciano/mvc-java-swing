package test;

import java.util.List;

import controller.CategoryController;
import entities.Category;
import entities.Product;

public class TestCategory {

    public static void main(String[] args) {

        CategoryController controller = new CategoryController();
        List<Category> listCategories = controller.listProductByCategory();
        listCategories.forEach(c -> {
            System.out.println(c.getName());
            for (Product prod : c.getProducts()) {
                System.out.println("- " + prod.getName());
            }
            System.out.println();
        });
    }

}
