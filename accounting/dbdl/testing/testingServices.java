import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;
import java.time.*;

class testingServices
{
public static void main(String gg[])
{
try
{
Instant startTime=Instant.now();
File customerFile=new File("customer_reports.tmp");
//System.out.println("File path: "+file.getAbsolutePath());
ReportService reportService=new ReportService();
reportService.getCustomerReports(customerFile);
Instant endTime=Instant.now();
System.out.println("Duration: "+Duration.between(startTime,endTime).toMillis()+" milli seconds");
System.out.println("Customer reports are available in file: "+customerFile.getPath());

startTime=Instant.now();
File supplierFile=new File("supplier_reports.tmp");
reportService.getSupplierReports(supplierFile);
endTime=Instant.now();
System.out.println("Duration: "+Duration.between(startTime,endTime).toMillis()+" milli seconds");
System.out.println("Supplier reports are available in file: "+supplierFile.getPath());

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
