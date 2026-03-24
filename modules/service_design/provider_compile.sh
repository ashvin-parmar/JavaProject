javac --module-path paymentlib/ -d stripeuse/ provider.modules/com/payment/provider/stripe/StripePayment.java provider.modules/module-info.java
jar -c -f paymentlib/provider-use.jar -C provider.modules .
