import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;

class testingServices3
{
public static void main(String gg[])
{
ReportService reportService=new ReportService();

reportService.printCustomerReports();
System.out.println("--------------------------------------------------------------------------------");

reportService.printSupplierReports();

}
}
