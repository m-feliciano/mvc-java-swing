package br.com.feliciano.mvc.domain.entities;

import java.util.Comparator;
import java.util.List;

import br.com.feliciano.mvc.controller.CategoryController;

public class TestCategory {

	public static void main(String[] args) {

		CategoryController controller = new CategoryController();
		List<Category> listCategories = controller.listProductByCategory();

		listCategories.sort(Comparator.comparingInt(Category::getId));

		listCategories.forEach(cat -> {
			System.out.println(cat.getId() + ") " + cat.getName());
			cat.getProducts().forEach(System.out::println);
			System.out.println();
		});
	}

}
