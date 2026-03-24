package com.payment.client;
import com.payment.api.PaymentService;
import java.util.ServiceLoader;

public class Main
{
public static void main(String args[])
{
System.out.println("Hik");
ServiceLoader<PaymentService> loader=ServiceLoader.load(PaymentService.class);
for(PaymentService service:loader)
{
service.process(100.99);
}
}
}
