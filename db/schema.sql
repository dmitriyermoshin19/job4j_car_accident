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