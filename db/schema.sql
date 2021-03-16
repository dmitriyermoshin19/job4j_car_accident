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


CREATE TABLE users (
username VARCHAR(50) NOT NULL,
password VARCHAR(100) NOT NULL,
enabled boolean default true,
PRIMARY KEY (username)
);

CREATE TABLE authorities (
username VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
FOREIGN KEY (username) REFERENCES users(username)
);
