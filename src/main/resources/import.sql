INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(true, 15, 'FOOD', CURRENT_DATE, nextval('product_seq'), 'Burger', 'Super Delicious burger');

INSERT INTO product
(is_active, price, product_type, created_date, id, "name", description)
VALUES(true, 10, 'DRINK', CURRENT_DATE, nextval('product_seq'), 'Water', 'Refresh water');
