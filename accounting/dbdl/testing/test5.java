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
Purchase purchase=new Purchase();
purchase.setBillNumber("ABCDE0123456789");	//15 characters long
purchase.setSupplierCode(1);
purchase.setItemCode(1);
purchase.setQuantity(5);
purchase.setRate(10);

PurchaseDAO purchaseDAO=new PurchaseDAO();
purchaseDAO.addPurchase(purchase);
System.out.println("Reference number: "+purchase.getReferenceNumber());
}catch(DAOException exception)
{
System.out.println("Exception: "+exception.getMessage());
}
}
}
