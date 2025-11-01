create table customer
(
code int primary key auto_increment,
name char(50) not null unique,
total_sale decimal(14,2) not null,
total_receipt decimal(14,2) not null
);
create table item
(
code int primary key auto_increment,
name char(50) not null unique
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
name char(50) not null unique,
total_purchase decimal(14,2) not null,
total_payment decimal(14,2) not null
);
create table purchase
(
reference_number int primary key auto_increment,
bill_number char(25) unique not null,
purchase_date date not null,
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
receipt_date date not null,
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
