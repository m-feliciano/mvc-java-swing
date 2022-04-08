package br.com.feliciano.mvc.domain.entities;

import java.util.List;

import br.com.feliciano.mvc.controller.ProductController;

public class TestProduct {

    public static void main(String[] args){

        ProductController controller = new ProductController();
        List<Product> products = controller.list();
        products.forEach(System.out::println);
    }

}
