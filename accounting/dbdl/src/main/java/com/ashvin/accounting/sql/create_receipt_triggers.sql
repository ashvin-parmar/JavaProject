delimiter $$

create trigger insert_receipt_trigger
after insert on receipt
for each row 
begin
	update customer set total_receipt=total_receipt+(NEW.amount) where customer.code=NEW.customer_code;
end $$ 

create trigger update_receipt_trigger
before update on receipt
for each row
begin 
	update customer set total_receipt=total_receipt-(old.amount)+(new.amount) where customer.code=new.customer_code;
end $$

create trigger delete_receipt_trigger
before delete on receipt
for each row
begin 
	update customer set total_receipt=total_receipt-(old.amount) where customer.code=old.customer_code;
end $$

delimiter ;
