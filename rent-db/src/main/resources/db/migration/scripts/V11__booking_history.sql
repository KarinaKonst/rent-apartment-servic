CREATE SEQUENCE booking_history_sequence;

CREATE TABLE IF NOT EXISTS booking_history(
id int8 PRIMARY KEY NOT NULL,
client_id int8 REFERENCES client_info(id),
    apartment_id int8 REFERENCES apartment_info(id),
    product_id int8 REFERENCES promotion_product(id),
    date_start date,
    date_end date,
    date_registration_booking date

    );