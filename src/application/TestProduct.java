package application;

import java.util.List;

import controller.ProductController;
import entities.Product;
import infra.exceptions.DbException;

public class TestProduct {

	public static void main(String[] args) throws DbException {

		ProductController controller = new ProductController();

//		Product p = new Product("Gel em amonia", "Good for something");
//		controller.save(p);
		List<Product> products = controller.list();
		products.forEach(System.out::println);
//		Product product = controller.findById(6);
//		System.out.println(product);
//		controller.delete(12);
//		System.out.println();
//		String name = "Escritorio";
//		List<Product> list = controller.getProductsByCategoryName(name);
//		System.out.println("Items em " + name);
//		list.forEach(System.out::println);
	}

}
