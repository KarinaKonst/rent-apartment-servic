CREATE SEQUENCE smtp_sequence start 2 increment 1;

CREATE TABLE IF NOT EXISTS smtp(
    id int8 PRIMARY KEY NOT NULL,
    host varchar(100),
    port int4,
    user_name varchar(100),
    password varchar(100));


