import com.ashvin.nframework.client.*;
import java.util.*;
import com.google.gson.*;		//Currently it is dependenci -> but 

public class BankUI
{
public static void main(String gg[])
{
if(gg.length!=1)
{
System.out.println("Usage: javap BankUI area_name");
return;
}
try
{
String area=gg[0];
//Object arguments[]={area};
NFrameworkClient client=new NFrameworkClient("localhost",8080);
String result=(String)client.execute("/banking/getBranch",area);
System.out.println("Branch name : "+result);

//List<State> states=(List<State>)client.execute("/banking/getENUMSTesting",new State(COUNTRY.INDIA,"UP"));
//List<State> stateObjs=(List<State>)client.execute("/banking/getENUMSTesting",COUNTRY.INDIA);
//List<State> states=(List<State>)client.execute("/banking/getENUMSTesting","Something");
//Object stateObjs=client.execute("/banking/getENUMSTesting",COUNTRY.INDIA);
//Object stateObjs=client.execute("/banking/getENUMSTesting",new State(COUNTRY.INDIA,"UP"));

		//FINAL ONE
Object stateObjs=client.execute("/banking/getENUMSTesting",COUNTRY.INDIA);
System.out.println(stateObjs);
Gson gson=new Gson();
JsonArray jsonArray=JsonParser.parseString(stateObjs.toString()).getAsJsonArray();
for(JsonElement element:jsonArray)
{
State state=gson.fromJson(element,State.class);
System.out.println("country: "+state.getCountry()+", state: "+state.getState());
}
/*
stateObjs.forEach((stateObj)->{
if(stateObj instanceof State)
{
State state=(State)stateObj;
System.out.println("country: "+state.getCountry()+", state: "+state.getState());
}
else
{
System.out.println(stateObj);
}
});
*/

}catch(BankingException be)
{
//System.out.println("Banking exception 1");
System.out.println(be.getMessage());		//Arrived for this case
}catch(Throwable t)
{
if(t instanceof BankingException)
{
//System.out.println("Banking exception");
BankingException be=(BankingException)t;
System.out.println(be.getMessage());
}
else
{
//System.out.println("Throwable exception");
System.out.println(t.getMessage());
}
}
}
}
