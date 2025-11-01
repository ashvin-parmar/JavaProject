import com.ashvin.accounting.dbdl.dao.*;
import com.ashvin.accounting.dbdl.dto.*;
import java.util.*;
import java.time.*;

class psp
{
public static void main(String gg[])
{
try
{
Payment payment=new Payment();
payment.setPaymentDate(new Date());
payment.setSupplierCode(1);
payment.setAmount(110);
PaymentDAO paymentDAO=new PaymentDAO();
paymentDAO.addPayment(payment);
System.out.println("Payment number: "+payment.getPaymentNumber());
}catch(DAOException exception)
{
System.out.println("Exception: "+exception.getMessage());
}
}
}
