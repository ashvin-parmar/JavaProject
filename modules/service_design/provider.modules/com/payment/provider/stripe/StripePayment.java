package com.payment.provider.stripe;

import com.payment.api.*;

public class StripePayment implements PaymentService
{
public void process(double amount)
{
System.out.println(amount);
}
}
