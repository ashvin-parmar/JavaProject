--  try 1
select customer.name,sum(sale.quantity*sale.rate) as to_receive, sum(receipt.amount) as paid from customer,sale,receipt where customer.code=sale.customer_code && customer.code=receipt.customer_code group by customer.code;
--  +------+----------------------------------------------------+------------+-------+
--  | code | name                                               | to_receive | paid  |
--  +------+----------------------------------------------------+------------+-------+
--  |   77 | DXCbjkQiFqTbQXAqycXYSyQfgherqjeNgOLdkVyGFatXLVrWEy |      14764 | 21730 |
--  +------+----------------------------------------------------+------------+-------+

--  try 2	[seperately works]
select sum(sale.quantity*sale.rate) from sale where customer_code=77;
--  +------------------------------+
--  | sum(sale.quantity*sale.rate) |
--  +------------------------------+
--  |                         3691 |
--  +------------------------------+

select sum(amount) from receipt where customer_code=77;
--  +-------------+
--  | sum(amount) |
--  +-------------+
--  |         530 |
--  +-------------+

--  try 3 --> for customer.code 77
select customer.code, sum(sale.quantity*sale.rate),sum(amount) from customer,sale,receipt where receipt.customer_code=customer.code && sale.customer_code=customer.code && customer.code=77 group by customer.code; 
--  +------+------------------------------+-------------+
--  | code | sum(sale.quantity*sale.rate) | sum(amount) |
--  +------+------------------------------+-------------+
--  |   77 |                        14764 |       21730 |
--  +------+------------------------------+-------------+

--  ------------------------------------------Everything above this line has failed ---------

--  try 4: Seperately calculated based on thier customer.code
select customer.code,sum(sale.quantity*sale.rate) as to_receive from sale,customer where
sale.customer_code=customer.code group by customer.code order by customer.code;

select customer.code, sum(receipt.amount) from receipt,customer where receipt.customer_code=customer.code group by customer.code order by customer.code;

--  But there are some customer.code where no data available, and sum cannot passes as 0 to those customer thats why we should not use this method.

--  -------------------------------- Something really has to learn new -------------------------

select customer_code,sum(sale.quantity*sale.rate) as to_receive from sale group by customer_code union select customer_code,sum(receipt.amount) as paid from receipt group by customer_code order by customer_code;

select customer.code, sum(sale.quantity*sale.rate) as to_receive from customer right join sale on customer.code=sale.customer_code group by customer.code order by customer.code;

select customer.code, sum(sale.quantity*sale.rate) as to_receive from customer left join sale on customer.code=sale.customer_code group by customer.code order by customer.code;



-- ------------------------------- Something something work but not perfect ---------------
-- NOT WORK 
select customer.code, sum(sale.quantity*sale.rate) as to_receive, sum(receipt.amount) as paid from customer left join sale,receipt on customer.code=sale.customer_code and customer.code=receipt.customer_code group by customer.code order by customer.code;
-- --------------------------------- Something something work but not perfect ---------------



select customer.code sum(sale.quantity*sale.rate) as to_receive, sum(receipt.amount) as paid, (paid-to_receive) as to_pay from customer
left join 
(
select customer_code,sum(sale.quantity*sale.rate) as to_receive from sale 
where sale.customer_code=customer.code group by customer.code
) on customer.code=sale.customer_code
left join 
(
select customer_code,sum(receipt.amount) as paid from receipt
where receipt.customer_code=customer.code group by customer.code
) on customer.code=receipt.customer_code
group by customer.code
order by customer.code;
-- -----------------------------------------------------------------------------------------
-- 		WORKING SQL Statement
select customer.code, temp1.to_receive, temp2.paid, temp1.to_receive-temp1.paid as to_pay from customer left join  ( select customer_code,sum(sale.quantity*sale.rate) as to_receive from sale,customer  where customer.code=sale.customer_code group by customer.code ) as temp1 on customer.code=temp1.customer_code left join  ( select customer_code,sum(receipt.amount) as paid from receipt,customer  where customer.code=receipt.customer_code group by customer.code ) as temp2 on customer.code=temp2.customer_code group by customer.code order by customer.code;

-- -----------------------------------------------------------------------------------------
SELECT 
    c.code,
    COALESCE(s.total_sales, 0) AS to_receive,
    COALESCE(r.total_receipts, 0) AS paid,
    COALESCE(s.total_sales, 0) - COALESCE(r.total_receipts, 0) AS balance
FROM customer AS c
LEFT JOIN (
    SELECT 
        customer_code,
        SUM(quantity * rate) AS total_sales
    FROM sale
    GROUP BY customer_code
) AS s ON c.code = s.customer_code
LEFT JOIN (
    SELECT 
        customer_code,
        SUM(amount) AS total_receipts
    FROM receipt
    GROUP BY customer_code
) AS r ON c.code = r.customer_code
ORDER BY c.code;
-- -----------------------------------------------------------------------------------------

