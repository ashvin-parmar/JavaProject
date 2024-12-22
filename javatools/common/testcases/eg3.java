import com.thinking.machines.utils.*;
class TestTMArrayList
{
public static void main(String gg[])
{
TMArrayList list;
list=new TMArrayList();
for(int i=1500;i<=1700;i++) list.add(i);
System.out.println("Size: "+list.size());
for(int i=0;i<list.size();i++) System.out.print(list.get(i)+" ");
System.out.println();
list.remove(4);
list.insert(4,10000);
list.update(0,1);
list.insert(1,20000);
System.out.println("After changing data: ");
System.out.println("Size: "+list.size());
for(int i=0;i<list.size();i++) System.out.print(list.get(i)+" ");
System.out.println();
System.out.println("Now clear complete list");
list.clear();
}
}
