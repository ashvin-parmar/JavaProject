-- Creating the database, user and thier password. 
-- mysql -u root -p 

create database hr_nexus_db;
create user 'hrnexususer1'@'localhost' identified by 'HR-Nexus#user1';
grant all privileges on hr_nexus_db.* to 'hrnexususer1'@'localhost';
flush privileges;

-- now exit and relogin with new user
-- mysql -u hrnexususer1 -p
