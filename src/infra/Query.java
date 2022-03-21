package infra;

public class Query {
	
	private Query() {}

	public static final String SQL_PRODUCT_INSERT = "INSERT INTO tb_product (name, description, price) VALUES (?,?,?::numeric)";
	public static final String SQL_PRODUCT_SELECT_BY_ID = "SELECT id, name, description, price, created_at FROM tb_product WHERE id = ?";
	public static final String SQL_PRODUCT_DELETE = "DELETE FROM tb_product WHERE id = ?";
	public static final String SQL_PRODUCTS_SELECT = "SELECT id, name, description, price, created_at FROM tb_product ORDER BY id";
	public static final String SQL_PRODUCTS_UPDATE = "UPDATE tb_product SET name=?, description=? price=? WHERE id = ?";

	public static final String SQL_PRODUCTS_BY_CATEGORY_NAME = "SELECT id, name, description, price, created_at "
			+ "FROM tb_product WHERE category_id = (SELECT id FROM tb_category WHERE name = ?)";
	public static final String SQL_CATEGORIES = "SELECT id, name FROM tb_category ORDER BY name ASC";
	public static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.id, c.name,"
			+ " p.id, p.name, p.description, p.price, p.created_at"
			+ " FROM tb_category c INNER JOIN tb_product p ON c.id = p.category_id;";

}
