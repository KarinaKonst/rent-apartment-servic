ALTER TABLE  apartment_info
    ADD COLUMN  current_tenant int8 REFERENCES client_info(id) DEFAULT null;

ALTER TABLE apartment_info
    ADD COLUMN   owner int8 REFERENCES client_info(id) DEFAULT null;
