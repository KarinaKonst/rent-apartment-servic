CREATE SEQUENCE apartment_info_sequence start 20 increment 1;


CREATE TABLE IF NOT EXISTS apartment_info(

    id int8 PRIMARY KEY NOT NULL,
    number_of_rooms varchar(100),
    price varchar(100),
    availability boolean DEFAULT true,
    renter_id int8 REFERENCES renter_info(id)

    );
