CREATE SEQUENCE IF NOT EXISTS public.customer_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    CACHE 1;

create table IF NOT EXISTS customers
(
    id            bigint NOT NULL DEFAULT nextval('customer_id_seq'),
    first_name    VARCHAR(100) NOT NULL ,
    last_name     VARCHAR(100) NOT NULL ,
    phone_number  VARCHAR(20) NOT NULL ,
    email         VARCHAR(100) NOT NULL ,
    created_date  TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX IF NOT EXISTS customer_email_idx ON customers (email);
CREATE UNIQUE INDEX IF NOT EXISTS customer_phone_number_idx ON customers (phone_number COLLATE "C");

CREATE SEQUENCE IF NOT EXISTS public.address_id_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    CACHE 1;


create table IF NOT EXISTS addresses
(
    id            bigint NOT NULL DEFAULT nextval('address_id_seq'),
    customer_id   bigint,
    address_line  VARCHAR(100),
    city          VARCHAR(100),
    country       VARCHAR(100),
    type          VARCHAR(100),
    created_date  TIMESTAMP WITH TIME ZONE,
    modified_date TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_customer
        FOREIGN KEY(customer_id)
            REFERENCES customers(id)
);


CREATE INDEX IF NOT EXISTS address_city_idx ON addresses (city);
