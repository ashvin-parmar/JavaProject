delimiter $$
create trigger insert_payment_trigger
after insert on payment
for each row
begin
	update supplier set total_payment=total_payment+(NEW.amount) where NEW.supplier_code=supplier.code;
end $$

delimiter ;
