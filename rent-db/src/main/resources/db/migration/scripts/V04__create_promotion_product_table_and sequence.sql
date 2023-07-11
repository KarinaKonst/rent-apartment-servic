CREATE SEQUENCE promotion_product_sequence start 3 increment 1;

CREATE TABLE IF NOT EXISTS promotion_product(

    id int8 PRIMARY KEY ,
    name_product varchar(100),
    description varchar(500),
    discount int4 );