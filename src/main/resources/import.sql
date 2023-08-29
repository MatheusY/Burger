INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(true, 15, 'FOOD', CURRENT_DATE, nextval('product_seq'), 'Hamburger', 'Delicioso hamburger tradicional!');

INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(true, 10, 'DRINK', CURRENT_DATE, nextval('product_seq'), 'Água', '');

INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(false, 5, 'FOOD', CURRENT_DATE, nextval('product_seq'), 'Beef', 'A burger with a beef');

INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(true, 17, 'FOOD', CURRENT_DATE, nextval('product_seq'), 'X-Salada', 'Hamburguer com bastante vegetais');
