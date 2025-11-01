import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;

class testingServices2
{
public static void main(String gg[])
{
ReportService reportService=new ReportService();

reportService.printCustomerReportsDirectly();
System.out.println("--------------------------------------------------------------------------------");

reportService.printSupplierReportsDirectly();

}
}
