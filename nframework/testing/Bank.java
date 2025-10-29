import com.ashvin.nframework.server.annotations.*;
import com.ashvin.nframework.server.*;
import java.util.*;

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
@Path("/getENUMSTesting")
public List<State> getENUMSTesting(String country) throws BankingException
{
State state=new State(COUNTRY.INDIA,"MP");
System.out.println(country);
if(state.getCountry()==COUNTRY.INDIA.toString()) System.out.println("It's india");
if(state.getCountry()==COUNTRY.USA.toString()) System.out.println("It's USA");
List<State> states=new LinkedList<State>();
states.add(new State(COUNTRY.INDIA,"M.P."));
states.add(new State(COUNTRY.USA,"SOMETHING"));
return states;
//return "Something";
}
public static void main(String gg[])
{
NFrameworkServer server=new NFrameworkServer();
server.registerClass(Bank.class);
server.start(8080);
}
}
