import com.ashvin.nframework.client.*;

public class BankUI
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage: javap BankUI area_name");
return;
}
String area=gg[0];
Object arguments[]={area};
NFrameworkClient client=new NFrameworkClient();
String result=(String)client.execute("/banking/getBranch",arguments);
System.out.println("Branch name: "+result);
}
}
