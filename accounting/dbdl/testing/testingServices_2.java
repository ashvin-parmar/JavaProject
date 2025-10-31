import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;

class testingServices2
{
public static void main(String gg[])
{
try
{
ReportService reportService=new ReportService();

reportService.printCustomerReportsDirectly();
reportService.printSupplierReportsDirectly();

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
