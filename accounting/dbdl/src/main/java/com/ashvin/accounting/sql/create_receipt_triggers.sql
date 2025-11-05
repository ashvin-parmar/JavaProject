delimiter $$

create trigger insert_receipt_trigger
after insert on receipt
for each row 
begin
	update customer set total_receipt=total_receipt+(NEW.amount) where customer.code=NEW.customer_code;
end $$ 

delimiter ;

