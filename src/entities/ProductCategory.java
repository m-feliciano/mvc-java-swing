package entities;

public class ProductCategory {

	private Integer id, productId, categoryId;

	public ProductCategory() {
		super();
	}

	public ProductCategory(Integer id, Integer productId, Integer categoryId) {
		super();
		this.id = id;
		this.productId = productId;
		this.categoryId = categoryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
