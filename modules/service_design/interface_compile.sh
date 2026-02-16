javac -d internaluse/ interfaces.modules/com/payment/api/PaymentService.java interfaces.modules/module-info.java
jar -c -f paymentlib/internal-use.jar -C internaluse .
