INSERT INTO customer (name, surname, gender) VALUES ('Stanisław', 'Styczyński', 'male');
INSERT INTO customer (name, surname, gender) VALUES ('Kamil', 'Małowiejski', 'male');
INSERT INTO customer (name, surname, gender) VALUES ('Rafał', 'Muszynek', 'male');
INSERT INTO customer (name, surname, gender) VALUES ('Matylda', 'Beka', 'female');
INSERT INTO customer (name, surname, gender) VALUES ('Michael', 'Bass', 'unknown');


INSERT INTO email_address (customer_id, email_address) VALUES (1, 'Stanislaw@Styczynski.com');
INSERT INTO email_address (customer_id, email_address) VALUES (1, 'Inny@Stasiu.com');
INSERT INTO email_address (customer_id, email_address) VALUES (2, 'Kamil@Małowiejski.com');
INSERT INTO email_address (customer_id, email_address) VALUES (2, 'entliczek@pentliczek.com');
INSERT INTO email_address (customer_id, email_address) VALUES (3, 'Rafal@Muszynek.com');
INSERT INTO email_address (customer_id, email_address) VALUES (3, 'rafalek@buziaczki.com');
INSERT INTO email_address (customer_id, email_address) VALUES (4, 'Matylda@Beka.com');
INSERT INTO email_address (customer_id, email_address) VALUES (4, 'candy@sugar.com');
INSERT INTO email_address (customer_id, email_address) VALUES (5, 'j@hehe.com');

INSERT INTO address (customer_id, address) VALUES (1, 'Szmaragdowa 16');
INSERT INTO address (customer_id, address) VALUES (1, 'Migdałowa 47');
INSERT INTO address (customer_id, address) VALUES (2, 'Ametystowa 2');
INSERT INTO address (customer_id, address) VALUES (3, 'Koralikowa 232');
INSERT INTO address (customer_id, address) VALUES (3, 'Malownicza 22');
INSERT INTO address (customer_id, address) VALUES (4, 'Ludowa 5');
INSERT INTO address (customer_id, address) VALUES (4, 'Balonowa 135');
INSERT INTO address (customer_id, address) VALUES (5, 'Niewadomogdzie 0');

INSERT INTO phone_number (customer_id, phone_number) VALUES (1, '634 345 253');
INSERT INTO phone_number (customer_id, phone_number) VALUES (1, '123 254 364');
INSERT INTO phone_number (customer_id, phone_number) VALUES (2, '949 239 492');
INSERT INTO phone_number (customer_id, phone_number) VALUES (2, '993 239 123');
INSERT INTO phone_number (customer_id, phone_number) VALUES (3, '312 435 234');
INSERT INTO phone_number (customer_id, phone_number) VALUES (3, '534 234 734');
INSERT INTO phone_number (customer_id, phone_number) VALUES (4, '473 283 132');
INSERT INTO phone_number (customer_id, phone_number) VALUES (5, '234 436 475');
