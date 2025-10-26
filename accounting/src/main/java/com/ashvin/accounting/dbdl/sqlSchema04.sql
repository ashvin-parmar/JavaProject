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
