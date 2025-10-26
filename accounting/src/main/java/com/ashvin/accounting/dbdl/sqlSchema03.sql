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
