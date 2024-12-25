import com.thinking.machines.utils.*;
class TestInvalidIteratorException
{
public static void main(String gg[])
{
TMArrayList list;
list=new TMArrayList();
list.add(10);
list.add(20);
list.add(30);
int data;
TMIterator iterator=list.iterator();
try
{
data=iterator.next();		//Testing: Wrong way to iterate
System.out.println("Data: "+data);
data=iterator.next();
System.out.println("Data: "+data);
data=iterator.next();
System.out.println("Data: "+data);
data=iterator.next();		//here exception throws
System.out.println("Data: "+data);
}catch(InvalidIteratorException iie)
{
System.out.println(iie.getMessage());
}
}
}
