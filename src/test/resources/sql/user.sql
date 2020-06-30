-- auto-generated definition
use test;
create table user
(
  id   int auto_increment
    primary key,
  name varchar(100) not null,
  password varchar(100) not null
);


insert into user (name, password) value ('ryo', '123456');