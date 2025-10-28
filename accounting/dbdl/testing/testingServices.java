import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;

class testingServices
{
public static void main(String gg[])
{
try
{
File customerFile=new File("customer_reports.tmp");
//System.out.println("File path: "+file.getAbsolutePath());

ReportService reportService=new ReportService();
reportService.getCustomerReports(customerFile);
System.out.println("Customer reports are available in file: "+customerFile.getPath());

File supplierFile=new File("supplier_reports.tmp");
reportService.getSupplierReports(supplierFile);
System.out.println("Supplier reports are available in file: "+supplierFile.getPath());

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
