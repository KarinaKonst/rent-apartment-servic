CREATE SEQUENCE raiting_info_sequence start 22 increment 1;

CREATE TABLE IF NOT EXISTS raiting_info(

    id int8 PRIMARY KEY NOT NULL,
    message varchar(1000),
    raiting int4,
    apartment_id int8 REFERENCES apartment_info(id)

    );