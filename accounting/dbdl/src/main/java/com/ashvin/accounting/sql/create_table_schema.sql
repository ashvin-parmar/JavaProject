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
create table supplier
(
code int primary key auto_increment,
name char(50) not null
);
create table purchase
(
reference_number int primary key auto_increment,
bill_number char(15) unique,
supplier_code int not null,
item_code int not null,
quantity int not null,
rate int not null,
foreign key (supplier_code) references supplier(code),
foreign key (item_code) references item(code)
);
create table receipt
(
receipt_number int primary key auto_increment,
receipt_data date not null,
customer_code int not null,
amount int not null,
foreign key (customer_code) references customer(code)
);
create table payment
(
payment_number int primary key auto_increment,
payment_date date not null,
supplier_code int not null,
amount int not null,
foreign key (supplier_code) references supplier(code)
);
