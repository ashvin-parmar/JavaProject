delimiter //
create trigger insert_sale_trigger
after insert on sale
for each row
begin
	update customer set total_sale=total_sale+(NEW.rate*NEW.quantity) where customer.code=NEW.customer_code;
end //
create trigger update_sale_trigger
before update on sale
for each row
begin 
	update customer set total_sale=total_sale-(OLD.rate*OLD.quantity)+(new.rate*new.quantity) where customer.code=old.customer_code;
end //

create trigger delete_sale_trigger
before delete on sale
for each row
begin 
	update customer set total_sale=total_sale-(old.rate*old.quantity) where customer.code=old.customer_code;
end//
delimiter ;
