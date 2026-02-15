delimiter $$
create trigger insert_payment_trigger
after insert on payment
for each row
begin
	update supplier set total_payment=total_payment+(NEW.amount) where NEW.supplier_code=supplier.code;
end $$

create trigger update_payment_trigger
before update on payment
for each row
begin 
	update supplier set total_payment=total_payment-(OLD.amount)+(NEW.amount) where NEW.supplier_code=supplier.code;
end $$

create trigger delete_payment_trigger
before delete on payment
for each row
begin 
	update supplier set total_payment=total_payment-(OLD.amount) where OLD.supplier_code=supplier.code;
end $$

delimiter ;
