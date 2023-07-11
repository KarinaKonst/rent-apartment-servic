DROP SEQUENCE promotion_product_sequence;
ALTER TABLE client_info DROP COLUMN product_first;
ALTER TABLE client_info DROP COLUMN product_second;
DROP TABLE promotion_product;