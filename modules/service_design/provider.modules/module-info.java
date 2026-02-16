module provider.modules
{
requires interfaces.modules;
provides com.payment.api.PaymentService with com.payment.provider.stripe.StripePayment;
}
