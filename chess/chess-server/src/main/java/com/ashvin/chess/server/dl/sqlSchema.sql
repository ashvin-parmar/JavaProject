create user chessuser01 identified by "ChessUser#01";
create database chessdb;
grant all privileges on 'chessdb.*' to 'chessuser01';
use chessdb;

create table member
(
uname varchar(25) unique,
pwd varchar(51) not null
);
