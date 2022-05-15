CREATE TABLE inventory (
                           inventory_id BIGINT auto_increment NOT NULL,
                                prdt_cd varchar(100) NULL,
                                batch varchar(100) NULL,
                                stock INTEGER NULL,
                                deal INTEGER NULL,
                                `free` INTEGER NULL,
                                mrp DOUBLE NULL,
                                rate DOUBLE NULL,
                                expiry_date DATE NULL,
                                company varchar(100) NULL,
                                supplier_id INTEGER NULL,
                                CONSTRAINT inventory_pk PRIMARY KEY (inventory_id)
)
;

CREATE TABLE products_mst_data (
                                        prdt_cd varchar(100) NULL,
                                        prdt_name varchar(100) NULL,
                                        CONSTRAINT products_mst_data_pk PRIMARY KEY (prdt_cd)
)
;

CREATE TABLE suppliers_mst_data (
                                    supplier_id BIGINT auto_increment NOT NULL,
                                         supplier_name varchar(100) NULL,
                                         CONSTRAINT suppliers_mst_data_pk PRIMARY KEY (supplier_id)
)
;


