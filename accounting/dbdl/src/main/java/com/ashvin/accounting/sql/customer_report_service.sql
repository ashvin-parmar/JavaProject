-- seperately 
select customer.code,sum(sale.quantity*sale.rate) as to_receive from sale,customer where
sale.customer_code=customer.code group by customer.code order by customer.code;

select customer.code, sum(receipt.amount) from receipt,customer where receipt.customer_code=customer.code group by customer.code order by customer.code;

-- at the same time
select customer.code,customer.name as customer_name, coalesce(tmp_sale.total_amount,0) as to_receive, coalesce(tmp_receipt.amount_paid,0) as advance, coalesce(tmp_sale.total_amount,0)-coalesce(tmp_receipt.amount_paid,0) as to_pay from customer left join ( select sale.customer_code, sum(sale.quantity*sale.rate) as total_amount from sale group by customer_code) as tmp_sale on customer.code=tmp_sale.customer_code left join (select receipt.customer_code,sum(receipt.amount) as amount_paid from receipt group by receipt.customer_code) as tmp_receipt on customer.code=tmp_receipt.customer_code order by customer.code;
