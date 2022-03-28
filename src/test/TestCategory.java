package test;

import controller.CategoryController;
import entities.Category;
import entities.Product;

import java.util.List;

public class TestCategory {

    public static void main(String[] args) {

        CategoryController controller = new CategoryController();

//		List<Category> categories = dao.getCategories();
//		categories.forEach(System.out::println);

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
