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
Sale sale=new Sale();
sale.setBillDate(new Date());
System.out.println("Date: "+sale.getBillDate().toString());
sale.setCustomerCode(1);
sale.setItemCode(1);
sale.setQuantity(5);
sale.setRate(10);

SaleDAO saleDAO=new SaleDAO();
saleDAO.addSale(sale);
System.out.println("Bill number: "+sale.getBillNumber());
}catch(DAOException exception)
{
System.out.println("Exception: "+exception.getMessage());
}
}
}
