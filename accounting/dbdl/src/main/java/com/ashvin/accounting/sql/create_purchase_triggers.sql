delimiter //
create trigger insert_purchase_trigger
after insert on purchase
for each row
begin
	update supplier set total_purchase=total_purchase+(NEW.rate*NEW.quantity) where supplier.code=NEW.supplier_code;
end //

create trigger update_purchase_trigger
before update on purchase
for each row
begin
	update supplier set total_purchase=total_purchase-(OLD.rate*OLD.quantity)+(NEW.rate*NEW.quantity) where supplier.code=NEW.supplier_code;
end //

create trigger delete_purchase_trigger
before delete on purchase
for each row
begin 
	update supplier set total_purchase=total_purchase-(OLD.rate*OLD.quantity) where supplier.code=OLD.supplier_code;
end //

delimiter ;
