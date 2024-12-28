import com.thinking.machines.utils.*;
class TestGeneric
{
public static void main(String gg[])
{
TMArrayList<Integer> list1=new TMArrayList<Integer>();
list1.add(100);
list1.add(200);
list1.add(300);
list1.add(400);
list1.forEach((p)->{
System.out.println(p);
});

TMLinkedList<String> list2=new TMLinkedList<String>();
list2.add("Kalukheda");
list2.add("Indore");
list2.add("Goa");
list2.add("Mumbai");
list2.forEach((j)->{
System.out.println("City : "+j);
});
}
}
