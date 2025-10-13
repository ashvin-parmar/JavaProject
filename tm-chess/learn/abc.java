class abc
{
public static void Something(Object[] objs)
{
System.out.println(objs);
for(Object obj:objs)
{
System.out.println(obj);
}
}
public static void main(String gg[])
{
String abc="abcd";
String pqr="asfaq;";
Object[] obj={abc,pqr};
Something(obj);

}
}
