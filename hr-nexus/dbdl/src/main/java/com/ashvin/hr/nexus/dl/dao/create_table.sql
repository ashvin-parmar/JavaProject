create table designation (
code int primary key auto_increment,
title char(35) not null unique
);

create table employee (
employee_id int primary key auto_increment,
name char(35) not null,
designation_code int not null,
date_of_birth date not null,
basic_salary decimal(12,2) not null,
gender char(1) not null,
is_indian boolean not null,
pan_number char(20) not null unique,
aadhar_card_number char(20) not null unique,
foreign key (designation_code) references designation(code)
);
