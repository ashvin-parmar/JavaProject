javac --module-path paymentlib -d clientuse consumer.modules/module-info.java consumer.modules/com/payment/client/Main.java
java --module-path paymentlib:clientuse -m consumer.modules/com.payment.client.Main
