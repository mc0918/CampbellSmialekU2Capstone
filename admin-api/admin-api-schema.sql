create schema if not exists spring_security_test;
use spring_security;

create table if not exists users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);

create table if not exists authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username));
	create unique index ix_auth_username on authorities (username,authority
);

-- bloop
create schema if not exists product;
use product;

create table if not exists product (
	product_id int(11) not null auto_increment primary key,
    product_name varchar(50) not null,
    product_description varchar(255) not null,
    list_price decimal(7,2) not null,
    unit_cost decimal(7,2) not null
);


create schema if not exists invoice;
use invoice;

create table if not exists invoice (
	invoice_id int(11) not null auto_increment primary key,
    customer_id int(11) not null,
    purchase_date date not null
);

create table if not exists invoice_item (
	invoice_item_id int(11) not null auto_increment primary key,
    invoice_id int(11) not null,
    product_id int(11) not null,
    quantity int(11) not null,
    unit_price decimal(7,2) not null    
);

alter table invoice_item add constraint fk_invoice_item_invoice foreign key (invoice_id) references invoice(invoice_id);

create schema if not exists level_up;
use level_up;

create table if not exists level_up (
	level_up_id int(11) not null auto_increment primary key,
    customer_id int(11) not null,
    points int(11) not null,
    member_date date not null
);

create schema if not exists customer;
use customer;

create table if not exists customer (
	customer_id int(11) not null auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    zip varchar(10) not null,
    email varchar(75) not null,
    phone varchar(20) not null
);

insert into users(username, password, enabled) values("EMPLOYEE", "$2a$10$FBLAFp8HSO7klMqZV/K1XOwxHOTkfcBnKWAwL0aq0tMTGvpjRV2zW", true);
insert into users(username, password, enabled) values("TEAM_LEAD", "$2a$10$FBLAFp8HSO7klMqZV/K1XOwxHOTkfcBnKWAwL0aq0tMTGvpjRV2zW", true);
insert into users(username, password, enabled) values("MANAGER", "$2a$10$FBLAFp8HSO7klMqZV/K1XOwxHOTkfcBnKWAwL0aq0tMTGvpjRV2zW", true);
insert into users(username, password, enabled) values("ADMIN", "$2a$10$FBLAFp8HSO7klMqZV/K1XOwxHOTkfcBnKWAwL0aq0tMTGvpjRV2zW", true);

insert into authorities(username, authority) values ("EMPLOYEE", "EMPLOYEE");
insert into authorities(username, authority) values ("TEAM_LEAD", "EMPLOYEE");
insert into authorities(username, authority) values ("TEAM_LEAD", "TEAM_LEAD");
insert into authorities(username, authority) values ("MANAGER", "EMPLOYEE");
insert into authorities(username, authority) values ("MANAGER", "TEAM_LEAD");
insert into authorities(username, authority) values ("MANAGER", "MANAGER");
insert into authorities(username, authority) values ("ADMIN", "EMPLOYEE");
insert into authorities(username, authority) values ("ADMIN", "TEAM_LEAD");
insert into authorities(username, authority) values ("ADMIN", "MANAGER");
insert into authorities(username, authority) values ("ADMIN", "ADMIN");

