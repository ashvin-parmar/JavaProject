
create database accountingdb;
create user 'accountinguser1'@'localhost' identified by 'accounting#User1';

grant all privileges on accountingdb.* to 'accountinguser1'@'localhost';
flush privileges;
