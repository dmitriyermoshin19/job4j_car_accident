CREATE TABLE types (
id serial primary key,
name varchar(200)
);

insert into types (name) values ('Две машины');
insert into types (name) values ('Машина и человек');
insert into types (name) values ('Машина и велосипед');

CREATE TABLE rules (
id serial primary key,
name varchar(200)
);

insert into rules (name) values ('Статья. 1');
insert into rules (name) values ('Статья. 2');
insert into rules (name) values ('Статья. 3');

CREATE TABLE accident (
id serial primary key,
name varchar(200),
text varchar(2000),
address varchar(500),
type_id integer references types(id),
rIds varchar(10)[]
);

CREATE TABLE accident_rules (
id serial primary key,
accident_id serial,
rules_id serial
);


CREATE TABLE authorities (
id serial primary key,
authority VARCHAR(50) NOT NULL unique
);

CREATE TABLE users (
id serial primary key,
username VARCHAR(50) NOT NULL unique,
password VARCHAR(100) NOT NULL,
enabled boolean default true,
authority_id int not null references authorities(id)
);

insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (enabled, username, password, authority_id)
values (true, 'root', '$2a$10$kVNH1J7DegNjZJ1MWwsabeu2ucViST6NLIKY8wHQmD85HpafH1vL6',
        (select id from authorities where authority = 'ROLE_ADMIN'));
