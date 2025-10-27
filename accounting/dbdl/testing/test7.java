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
Receipt receipt=new Receipt();
receipt.setReceiptDate(new Date());
receipt.setCustomerCode(1);
receipt.setAmount(110);
ReceiptDAO receiptDAO=new ReceiptDAO();
receiptDAO.addReceipt(receipt);
System.out.println("Receipt number: "+receipt.getReceiptNumber());
}catch(DAOException exception)
{
System.out.println("Exception: "+exception.getMessage());
}
}
}
