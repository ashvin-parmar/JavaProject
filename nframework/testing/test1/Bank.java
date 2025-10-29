import com.ashvin.nframework.server.annotations.*;
import com.ashvin.nframework.server.*;

@Path("/banking")
public class Bank
{
private static Bank bank=null;
private Bank()
{

}
public static Bank getBank()
{
if(bank==null) bank=new Bank();
return bank;
}
@Path("/getBranch")
public String getBranch(String area) throws BankingException
{
if(area.equals("Ujjain"))
{
return "Freeganj";
}
else if(area.equals("Mumbai"))
{
return "RBI Monetary Museum";
}
else if(area.equals("Bengaluru"))
{
return "Shivoham Shiva Temple";
}
throw new BankingException("No branch available");
}
public static void main(String gg[])
{
NFrameworkServer server=new NFrameworkServer();
server.registerClass(Bank.class);
server.start(8080);
}
}
