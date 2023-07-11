CREATE SEQUENCE renter_info_sequence start 11 increment 1;

CREATE TABLE IF NOT EXISTS renter_info(
    id int8 PRIMARY KEY NOT NULL,
    first_name varchar(100),
    last_name varchar(100),
    number_passport varchar(100),
    number_phone varchar(100),
    renter_rating varchar(5)

    );