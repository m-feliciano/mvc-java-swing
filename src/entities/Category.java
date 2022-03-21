package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category {

	private Integer id;
	private String name;
	List<Product> products = new ArrayList<>();

	public Category() {
		super();
	}

	public Category(String name) {
		super();
		this.name = name;
	}

	public Category(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProduct(Product prod) {
		products.add(prod);
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}

}
