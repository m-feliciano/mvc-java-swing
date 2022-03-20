
CREATE DATABASE java_jdbc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
	
	CREATE TABLE IF NOT EXISTS tb_category (
		id INT, 
		name VARCHAR(255) NOT NULL
	) CHARACTER set utf8mb4 COLLATE utf8mb4_unicode_ci;
	
	ALTER TABLE tb_category MODIFY COLUMN id INT AUTO_INCREMENT;
	ALTER TABLE tb_category ADD CONSTRAINT PK_Category PRIMARY KEY(id);
	
	INSERT INTO tb_category(name) VALUES
	('Gamer'),
	('Office'),
	('Escritorio'),
	('Informática');
	
	
	CREATE TABLE IF NOT EXISTS tb_product (
		id INT, 
		name VARCHAR(255) NOT NULL, 
		description TEXT,
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		category_id INT
	) CHARACTER set utf8mb4 COLLATE utf8mb4_unicode_ci;
	
	
	ALTER TABLE tb_product MODIFY COLUMN id INT AUTO_INCREMENT;
	ALTER TABLE tb_product ADD CONSTRAINT PK_Product PRIMARY KEY(id);
	ALTER TABLE tb_product ADD CONSTRAINT FK_category_product FOREIGN KEY(category_id) REFERENCES tb_category(id);
	
	INSERT INTO tb_product(name, description, category_id) VALUES
		("iMac","Apple iMac 1TB 16GB 256GB", 2),
		("Teclado","Teclado mecânico super leve", 3),
		("Mouse Gamer","Mouse 3 velocidades RGB", 1),
		("Mouse XXX Gamer","Mouse Ultra caro", 1),
		("XBOX","XBOX GOLD PASS 1 YEAR", 1),
		("SOM","Baruho total 220v", 2),
		("Google Home","Google home office", 3),
		("Quadro Decorativo","Brasil fabril", 3),
		("Luminaria","COR AMARELADA", 3),
		("Smart TV","Alexa, Netflix etc RGB", 4);