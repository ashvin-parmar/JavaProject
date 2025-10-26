import com.ashvin.accounting.dbdl.dao.*;
import com.ashvin.accounting.dbdl.dto.*;

class psp
{
public static void main(String gg[])
{
String itemName="TV";
String customerName="Ashvin";
String supplierName="Ayush";
try
{
Customer customer=new Customer();
customer.setName(customerName);
(new CustomerDAO()).addCustomer(customer);
System.out.println("Customer code: "+customer.getCode());

Supplier supplier=new Supplier();
supplier.setName(supplierName);
(new SupplierDAO()).addSupplier(supplier);
System.out.println("Supplier code: "+supplier.getCode());


Item item=new Item();
item.setName(itemName);
(new ItemDAO()).addItem(item);
System.out.println("Item code: "+item.getCode());
}catch(DAOException exception)
{
System.out.println(exception.getMessage());
}

}
}
