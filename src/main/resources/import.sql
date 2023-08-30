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

INSERT INTO public.user_info
(is_active, is_verified, id, "name", email)
VALUES(true, true, nextval('user_seq'), 'Matheus', 'matheus.matsubara@gmail.com');

INSERT INTO public.user_info
(is_active, is_verified, id, "name", email)
VALUES(true, true, nextval('user_seq'), 'Murilo', 'murilo@gmail.com');

INSERT INTO public.user_info
(is_active, is_verified, id, "name", email)
VALUES(true, true, nextval('user_seq'), 'Graca', 'graca@gmail.com');

INSERT INTO public.user_info
(is_active, is_verified, id, "name", email)
VALUES(true, true, nextval('user_seq'), 'Milton', 'milton@gmail.com');

INSERT INTO public.user_info
(is_active, is_verified, id, "name", email)
VALUES(false, false, nextval('user_seq'), 'mima', 'mima@gmail.com');

INSERT INTO public.purchase_order
(id, ordereddate, user_fk, order_status)
VALUES(nextval('order_seq'), '2023-08-28 00:00:00.000', 1, 'CONCLUDED');

INSERT INTO public.purchase_order
(id, ordereddate, user_fk, order_status)
VALUES(nextval('order_seq'), CURRENT_DATE, 1, 'IN_PROGRESS');

INSERT INTO public.item
(quantity, subtotal, id, order_fk, product_fk)
VALUES(2, 30, nextval('item_seq'), 1, 1);

INSERT INTO public.item
(quantity, subtotal, id, order_fk, product_fk)
VALUES(1, 20, nextval('item_seq'), 1, 2);

INSERT INTO public.item
(quantity, subtotal, id, order_fk, product_fk)
VALUES(1, 17, nextval('item_seq'), 2, 4);


