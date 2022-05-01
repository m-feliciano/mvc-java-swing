# CRUD-Tarefas

Controle de estroque utilizando padrão MVC, Java, Swing, PostgreSQL e Lombok(códigos boilerplate).

---

Neste exemplo, a utilização é do banco de dados PostgreSQL executando em um container Docker.

Caso ja conheça bancos de dados relacionais, utilize o arquivo DDL.sql

## Layout Desktop

![Layout do aplicativo usando java Swing](https://i.ibb.co/2ksGJH9/Capture.png)

## Estrutura de diretorios

```

C:.
├───main
│   └───java
│       └───br
│           └───com
│               └───feliciano
│                   └───mvc
│                       ├───application
│                       ├───controller
│                       ├───domain
│                       │   ├───entities
│                       │   │   └───vo
│                       │   └───services
│                       ├───infra
│                       │   ├───dao
│                       │   └───exceptions
│                       └───view
│                           └───utils
└───test
    └───java
        └───br
            └───com
                └───feliciano
                    └───mvc
                        ├───domain
                        │   ├───entities
                        │   └───services
                        └───infra
```

## Passo a passo

## Criando uma rede Docker

```
docker network create -d bridge postgres
```

## Criando o container

```
docker run --name NOME_DO_CONTAINER \
--network=postgres -p 5432:5432 \
-e "POSTGRES_PASSWORD=ESCOLHA_SENHA" \
-d postgres
```

## Executando o container

```
docker exec -it NOME_DO_CONTAINER psql -U postgres
```

## Criando o banco a ser utilizado neste exemplo

```
CREATE DATABASE teste_dm;
```

## Conectar ao banco em uso

```
\c  teste_dm;
```

## Criando a tabela de categoria e sequence

```
CREATE TABLE IF NOT EXISTS tb_category
(
    id INT NOT NULL,
    name VARCHAR(255) NOT NULL
);

ALTER TABLE tb_category
    ADD CONSTRAINT PK_Category PRIMARY KEY (id);

CREATE SEQUENCE tb_category_id_seq;

ALTER TABLE tb_category
    ALTER column id SET DEFAULT nextval('tb_category_id_seq');
```

## Criando a tabela de produtos e sequence

```
CREATE TABLE IF NOT EXISTS tb_product
(
    id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE tb_product
    ADD CONSTRAINT PK_Product PRIMARY KEY (id);

CREATE SEQUENCE tb_product_id_seq;

ALTER TABLE tb_product
    ALTER column id SET DEFAULT nextval('tb_product_id_seq');
```

## Criando tabela de estoque e sequence

```
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
```

## Criando as relações das tabelas

```
ALTER TABLE tb_inventory
    ADD CONSTRAINT FK_category_inventory
        FOREIGN KEY (category_id) REFERENCES tb_category (id);

ALTER TABLE tb_inventory
    ADD CONSTRAINT FK_product_inventory
        FOREIGN KEY (product_id) REFERENCES tb_product (id);
```

## Criando a tabela usuario e sequence

```
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
```

## Criando a tabela endereço e sequence

```
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
```

## Adicionando relação entre usuario e endereço

```
ALTER TABLE tb_address
    ADD CONSTRAINT FK_user_address
        FOREIGN KEY (user_id) REFERENCES tb_user (id);
```

## Populando as tabelas

```
INSERT INTO tb_user(username, email, password)
VALUES ('teste', 'teste', '123');

INSERT INTO tb_address(cep, number, place, local, user_id)
VALUES ('04707000', '86', 'Av Roque roll', 'São paulo', 1);

INSERT INTO tb_category(name)
VALUES ('Gamer'),
       ('Office'),
       ('Cozinha'),
       ('Informática');

INSERT INTO tb_product(name, description, price)
VALUES ('iMac', 'Apple iMac 1TB 16GB 256GB', 20.12),
       ('Teclado', 'Teclado mecânico super leve', 30.32),
       ('Mouse XXX Gamer', 'Mouse Ultra caro', 450.20),
       ('Google Home', 'Google home office', 300.45),
       ('Quadro Decorativo', 'Brasil fabril', 30.00),
       ('Luminaria', 'COR AMARELADA', 320.78),
       ('Smart TV', 'Alexa, Netflix etc RGB', 3400.99);

INSERT INTO tb_inventory(description, quantity, product_id, category_id)
VALUES ('Lote-04-22SP', 2, 1, 1),
       ('Lote-04-22RJ', 2, 2, 2),
       ('Lote-04-22BA', 5, 3, 3),
       ('Lote-04-22MG', 2, 4, 4),
       ('Lote-04-22RS', 1, 5, 2),
       ('Lote-04-22RN', 7, 6, 3);
```