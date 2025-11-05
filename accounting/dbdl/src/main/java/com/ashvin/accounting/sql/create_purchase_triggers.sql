delimiter //
create trigger insert_purchase_trigger
after insert on purchase
for each row
begin
	update supplier set total_purchase=total_purchase+(NEW.rate*NEW.quantity) where supplier.code=NEW.supplier_code;
end //
delimiter ;
