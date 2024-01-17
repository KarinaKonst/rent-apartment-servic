CREATE SEQUENCE total_rating_sequence start 1 increment 1;

CREATE TABLE IF NOT EXISTS total_rating(
    id int8 PRIMARY KEY NOT NULL,
    total_rating int4,
    apartment_id int8 REFERENCES apartment_info(id)

    );