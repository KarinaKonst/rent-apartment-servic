CREATE SEQUENCE client_info_sequence start 11 increment 1;

CREATE TABLE IF NOT EXISTS client_info(

    id int8 PRIMARY KEY NOT NULL,
    first_name varchar(100),
    last_name varchar(100),
    birthday date,
    number_passport varchar(100),
    number_phone varchar(100),
    client_rating varchar(5),
    email_address varchar(100),
    password varchar(50),
    count_of_grocery int4,
    parent_city varchar(100),
    product_first int8 REFERENCES promotion_product (id),
    product_second int8 REFERENCES promotion_product (id),
    commerce boolean DEFAULT false,
    session_token varchar DEFAULT null


    );
