create table customer
(
code int primary key auto_increment,
name char(50) not null
);
create table item
(
code int primary key auto_increment,
name char(50) not null
);
create table sale
(
bill_number int primary key auto_increment,
bill_date date not null,
customer_code int not null,
item_code int not null,
quantity int not null,
rate int not null,
foreign key (customer_code) references customer(code),
foreign key (item_code) references item(code)
);


