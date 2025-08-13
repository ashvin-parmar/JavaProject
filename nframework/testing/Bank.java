import com.ashvin.nframework.server.annotations.*;
import com.ashvin.nframework.server.*;

@Path("/banking")
public class Bank
{
@Path("/getBranch")
public String getBranch(String area)
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
return "No branch available";
}
public static void main(String gg[])
{
NFrameworkServer server=new NFrameworkServer();
server.registerClass(Bank.class);
server.start();
}
}
