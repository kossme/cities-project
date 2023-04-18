create schema if not exists cities;

create table if not exists cities.city
(
    id          bigint not null
    primary key,
    name        varchar(255),
    picture_url text
    );
