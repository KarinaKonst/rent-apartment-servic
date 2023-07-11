CREATE SEQUENCE address_apartment_sequence start 20 increment 1;


CREATE TABLE IF NOT EXISTS address_apartment(

    id int8 PRIMARY KEY NOT NULL,
    city varchar(50),
    street varchar(100),
    number_house varchar(100),
    number_apartment varchar(100),
    apartment_id int8 REFERENCES apartment_info(id)

    );