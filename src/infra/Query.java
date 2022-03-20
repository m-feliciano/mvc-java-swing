package infra;

public class Query {

	public static final String SQL_PRODUCT_INSERT = "INSERT INTO tb_product (name, description) VALUES (?,?)";
	public static final String SQL_PRODUCT_SELECT_BY_ID = "SELECT id, name, description, created_at FROM tb_product WHERE id = ?";
	public static final String SQL_PRODUCT_DELETE = "DELETE FROM tb_product WHERE id = ? LIMIT 1";
	public static final String SQL_PRODUCTS_SELECT = "SELECT id, name, description, created_at FROM tb_product ORDER BY id";
	public static final String SQL_PRODUCTS_UPDATE = "UPDATE tb_product SET name = ?, description = ? WHERE id = ? LIMIT 1";

	public static final String SQL_PRODUCTS_BY_CATEGORY_NAME = "SELECT id, name, description, created_at "
			+ "FROM tb_product WHERE category_id = (SELECT id FROM tb_category WHERE name = ?)";
	public static final String SQL_CATEGORIES = "SELECT id, name FROM tb_category";
	public static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.id, c.name,"
			+ " p.id, p.name, p.description, p.created_at"
			+ " FROM tb_category c INNER JOIN tb_product p ON c.id = p.category_id;";

}
