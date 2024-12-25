//Tool for analysing any class

import java.lang.reflect.*;

class Analyzer
{
public static void main(String data[])
{
if(data.length!=1)
{
System.out.println("Usage: [java Analyzer class_name_to_analyse]");
return;
}
String classToAnalyse=data[0];
try
{
Class c=Class.forName(classToAnalyse);
System.out.println("Name (Package name included): "+c.getName());
System.out.println("Name (Package name excluded): "+c.getSimpleName());
Method allMethods[];
Method methods[];
Method m;
Class methodReturnType;
Class parameters[];
allMethods=c.getMethods();
System.out.println("Total number of methods (Including base class method): "+allMethods.length);
methods=c.getDeclaredMethods();
System.out.println("Total number of methods (Excluding base/Object class methods): "+methods.length);
int e,f;
System.out.println("Here, Only "+c.getSimpleName()+" class declared methods are shown: \n****************************************");
for(e=0;e<methods.length;e++)
{
m=methods[e];
methodReturnType=m.getReturnType();
System.out.println("Methods number: "+(e+1));
System.out.println("Method name: "+m.getName());
System.out.println("Method return type: "+m.getReturnType());
parameters=m.getParameterTypes();
System.out.println("Number of parameters: "+parameters.length);
for(f=0;f<parameters.length;f++)
{
System.out.printf("Parameter number: %d, Parameter Type: %s\n",f+1,parameters[f].getName());
}
System.out.println("****************************************");
}
Field fields[];
Field field;
Class fieldType;
String fieldName;
fields=c.getDeclaredFields();
for(e=0;e<fields.length;e++)
{
field=fields[e];
fieldName=field.getName();
fieldType=field.getType();
System.out.printf("Field number: %d, Field Name: %s, Field Type: %s\n",e+1,fieldName,fieldType);
}
System.out.println("\nThis is complete details of Class "+c.getSimpleName());
}catch(ClassNotFoundException cnfe)
{
System.out.println("Class: "+cnfe.getMessage()+" Not Found");
}
}
}

