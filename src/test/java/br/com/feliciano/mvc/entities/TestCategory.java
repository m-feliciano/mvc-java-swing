package br.com.feliciano.mvc.entities;

import java.util.List;

import br.com.feliciano.mvc.controller.CategoryController;

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
