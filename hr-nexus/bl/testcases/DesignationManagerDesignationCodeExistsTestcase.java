import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
public class DesignationManagerDesignationCodeExistsTestcase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
System.out.printf("Designation code %d : %s\n",code,DesignationManager.getDesignationManager().designationCodeExists(code)==true?"exist":"not exist");
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.printf("[%s]:  %s\n",property,blException.getPropertyException(property));
}
}
}
}
