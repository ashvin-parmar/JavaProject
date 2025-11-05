delimiter //
create trigger insert_sale_trigger
after insert on sale
for each row
begin
	update customer set total_sale=total_sale+(NEW.rate*NEW.quantity) where customer.code=NEW.customer_code;
end //
delimiter ;
