package infra;

public abstract class Query {

    public static final String SQL_PRODUCTS_SELECT = "SELECT id, name, description, price, created_at FROM tb_product ORDER BY id";
    public static final String SQL_PRODUCT_SELECT_BY_ID = "SELECT id, name, description, price, created_at FROM tb_product WHERE id = ?";
    public static final String SQL_PRODUCT_INSERT = "INSERT INTO tb_product (name, description, price) VALUES (?,?,?::numeric)";
    public static final String SQL_PRODUCT_DELETE = "DELETE FROM tb_product WHERE id = ?";
    public static final String SQL_PRODUCTS_UPDATE = "UPDATE tb_product SET name=?, description=?, price=(?::numeric) WHERE id = ?";

    public static final String SQL_CATEGORIES = "SELECT id, name FROM tb_category ORDER BY name ASC";
    public static final String SQL_PRODUCTS_BY_CATEGORY_NAME = """
            SELECT id, name, description, price, created_at
            FROM tb_product WHERE category_id = (SELECT id FROM tb_category WHERE name = ?)""";
    public static final String SQL_PRODUCTS_BY_CATEGORY = """
            SELECT c.id, c.name, p.id, p.name, p.description, p.price, p.created_at
            FROM tb_category c INNER JOIN tb_product p ON c.id = p.category_id;
            """;

    public static final String SQL_ADDRESS_SELECT = "SELECT cep, number, place, local, user_id FROM tb_address WHERE user_id = ?";
    public static final String SQL_ADDRESS_UPDATE = "UPDATE tb_address SET cep= ?, number=?, place=?, local=? WHERE user_id = ?";

    public static final String SQL_USER_UPDATE = "UPDATE tb_user SET username=?, email=? WHERE id = ?";
    public static final String SQL_USER_SELECT = "SELECT id, username, email FROM tb_user WHERE id = ?";

    public static final String SQL_INVENTORY_SELECT = "SELECT id, description, quantity, product_id, category_id FROM tb_inventory ORDER BY id";
    public static final String SQL_INVENTORY_SELECT_BY_ID = "SELECT id, description, quantity, product_id, category_id FROM tb_inventory WHERE id = ?";
    public static final String SQL_INVENTORY_INSERT = "INSERT INTO tb_inventory (description, quantity, product_id, category_id) VALUES (?,?,?,?)";
    public static final String SQL_INVENTORY_DELETE = "DELETE FROM tb_inventory WHERE id = ?";
    public static final String SQL_INVENTORY_UPDATE = "UPDATE tb_inventory SET description=?, quantity=?, product_id=?, category_id=? WHERE id = ?";
}
