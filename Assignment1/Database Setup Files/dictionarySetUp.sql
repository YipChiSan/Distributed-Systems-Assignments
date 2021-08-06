create database dictionary;
use dictionary;

create user ''@'localhost';

grant select, insert, delete  on *.* to ''@'localhost';
grant all privileges on dictionary.* to serverUser;


create table mainDictionary (
      
      word varchar(30) not null,
      meaning varchar(100) not null,
      primary key (word)
);
