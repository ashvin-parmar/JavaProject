import com.ashvin.network.client.Configuration;
import com.ashvin.network.common.exceptions.*;
class psp
{
public static void main(String gg[])
{
try
{
System.out.println("Port: "+Configuration.getPort());
System.out.println("Host: "+Configuration.getHost());
}catch(NetworkException ne)
{
System.out.println(ne);
}
}
}
