DROP SEQUENCE apartment_info_sequence;
ALTER TABLE address_apartment DROP COLUMN apartment_id;
ALTER TABLE raiting_info DROP COLUMN apartment_id;

DROP TABLE apartment_info;