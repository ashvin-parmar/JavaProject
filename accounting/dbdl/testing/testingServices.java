import java.io.*;

import com.ashvin.accounting.dbdl.services.*;
import com.ashvin.accounting.dbdl.dao.DAOException;

class testingServices
{
public static void main(String gg[])
{
try
{
File file=new File("customer_reports.dat");
//System.out.println("File path: "+file.getAbsolutePath());

ReportService reportService=new ReportService();
reportService.getCustomerReports(file);
System.out.println("Reports are available in file: "+file.getPath());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
