import com.ashvin.hr.nexus.bl.exceptions.*;
import com.ashvin.hr.nexus.bl.interfaces.pojo.*;
import com.ashvin.hr.nexus.bl.interfaces.manager.*;
import com.ashvin.hr.nexus.bl.pojo.*;
import com.ashvin.hr.nexus.bl.manager.*;

import java.util.*;
public class DesignationManagerGetDesignationsTestcase
{
public static void main(String gg[])
{
try
{
Set<DesignationInterface> designations=DesignationManager.getDesignationManager().getDesignations();
for(DesignationInterface designation:designations)
{
System.out.printf("Code: %d, Title: %s\n",designation.getCode(),designation.getTitle());
}
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
