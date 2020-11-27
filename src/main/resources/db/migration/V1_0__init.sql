CREATE TABLE world_city
(
    id         BIGSERIAL,
    name       varchar(255) NULL,
    country    varchar(255) NULL,
    subcountry varchar(255) NULL,
    geoname_id varchar(255),
    CONSTRAINT world_city_pkey PRIMARY KEY (id)
);
create sequence world_city_seq increment 1 start 1 minvalue 1;
