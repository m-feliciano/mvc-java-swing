CREATE DATABASE jdbc;

CREATE TABLE IF NOT EXISTS tb_category
(
    id   INT          NOT NULL,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE tb_category
    ADD CONSTRAINT PK_Category PRIMARY KEY (id);

CREATE SEQUENCE tb_category_id_seq;
ALTER TABLE tb_category
    ALTER column id SET DEFAULT nextval('tb_category_id_seq');

CREATE TABLE IF NOT EXISTS tb_product
(
    id          INT          NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       NUMERIC(10, 2),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE tb_product
    ADD CONSTRAINT PK_Product PRIMARY KEY (id);

CREATE SEQUENCE tb_product_id_seq;

ALTER TABLE tb_product
    ALTER column id SET DEFAULT nextval('tb_product_id_seq');

CREATE TABLE IF NOT EXISTS tb_inventory
(
    id          INT NOT NULL,
    description TEXT,
    quantity    INT,
    category_id INT,
    product_id  INT
);

ALTER TABLE tb_inventory
    ADD CONSTRAINT PK_Inventory PRIMARY KEY (id);

CREATE SEQUENCE tb_inventory_id_seq;

ALTER TABLE tb_inventory
    ALTER column id SET DEFAULT nextval('tb_inventory_id_seq');

ALTER TABLE tb_inventory
    ADD CONSTRAINT FK_category_inventory
        FOREIGN KEY (category_id) REFERENCES tb_category (id);

ALTER TABLE tb_inventory
    ADD CONSTRAINT FK_product_inventory
        FOREIGN KEY (product_id) REFERENCES tb_product (id);

INSERT INTO tb_category(name)
VALUES ('Gamer'),
       ('Office'),
       ('Escritorio'),
       ('Informática');

INSERT INTO tb_product(name, description, price)
VALUES ('iMac', 'Apple iMac 1TB 16GB 256GB', 20.12),
       ('Teclado', 'Teclado mecânico super leve', 30.32),
       ('Mouse Gamer', 'Mouse 3 velocidades RGB', 25.33),
       ('Mouse XXX Gamer', 'Mouse Ultra caro', 45.20),
       ('XBOX', 'XBOX GOLD PASS 1 YEAR', 100.09),
       ('SOM', 'Baruho total 220v', 2000.99),
       ('Google Home', 'Google home office', 300.45),
       ('Quadro Decorativo', 'Brasil fabril', 30.00),
       ('Luminaria', 'COR AMARELADA', 320.78),
       ('Smart TV', 'Alexa, Netflix etc RGB', 3400.99);

INSERT INTO tb_inventory(description, quantity, product_id, category_id)
VALUES ('Deposito am', 2, 1, 1),
       ('Deposito pi', 2, 2, 2),
       ('Deposito ba', 5, 3, 3),
       ('Deposito rn', 2, 4, 4),
       ('Deposito rs', 1, 5, 2),
       ('Deposito mg', 7, 6, 3),
       ('Deposito sp', 1, 7, 3),
       ('Deposito rj', 4, 8, 2);

CREATE TABLE IF NOT EXISTS tb_user
(
    id       INT         NOT NULL,
    username VARCHAR(55) NOT NULL,
    email    VARCHAR(55) NOT NULL,
    password VARCHAR(55) NOT NULL
);

ALTER TABLE tb_user
    ADD CONSTRAINT PK_User PRIMARY KEY (id);

CREATE SEQUENCE tb_user_id_seq;
ALTER TABLE tb_user
    ALTER column id SET DEFAULT nextval('tb_user_id_seq');


CREATE TABLE IF NOT EXISTS tb_address
(
    id      INT         NOT NULL,
    cep     VARCHAR(55) NOT NULL,
    number  VARCHAR(55) NOT NULL,
    place   VARCHAR(55) NOT NULL,
    local   VARCHAR(55) NOT NULL,
    user_id INT
);

ALTER TABLE tb_address
    ADD CONSTRAINT PK_Address PRIMARY KEY (id);

CREATE SEQUENCE tb_address_id_seq;
ALTER TABLE tb_address
    ALTER column id SET DEFAULT nextval('tb_address_id_seq');

ALTER TABLE tb_address
    ADD CONSTRAINT FK_user_address
        FOREIGN KEY (user_id) REFERENCES tb_user (id);


INSERT INTO tb_user(username, email, password)
VALUES ('teste','teste','123');

INSERT INTO tb_address(cep, number, place, local, user_id)
VALUES ('04707000', '86', 'Av Roque roll', 'São paulo', 1);