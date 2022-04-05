package test;

import java.util.List;

import controller.ProductController;
import entities.Product;

public class TestProduct {

    public static void main(String[] args){

        ProductController controller = new ProductController();
        List<Product> products = controller.list();
        products.forEach(System.out::println);
    }

}
