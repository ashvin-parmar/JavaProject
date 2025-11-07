delimiter //
create procedure increase_salary(in empId int,out salary int)
begin 
	declare x int;
	declare done int default false;
	declare loop1 cursor for (select basic_salary from employee where employee.emp_id=empId);
	declare continue handler for not found set done=true;

open loop1;
 read_loop:LOOP
	fetch loop1 into x;
	if done then
		leave read_loop;
	end if;
	if(x<=1000) then 
		update employee set basic_salary=basic_salary+2000;
--		salary=@x+2000;
	end if;
	if(x>1000 && x<=5000) then 
		update employee set basic_salary=basic_salary+1000;
--		salary=@x+1000;
	end if;
	if(x>5000) then 
		update employee set basic_salary=basic_salary+500;
--		salary=@x+500;
	end if;
	END LOOP;
close loop1;
end; //
delimiter ;
