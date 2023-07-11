CREATE SEQUENCE api_key_sequence start 2 increment 1;

CREATE TABLE IF NOT EXISTS api_key(
    id int8 PRIMARY KEY NOT NULL,
    name varchar(100),
    value varchar(100));

