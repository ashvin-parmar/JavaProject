import com.thinking.machines.utils.*;
class TestKeyboard1
{
public static void main(String gg[])
{
Keyboard keyboard=new Keyboard();
System.out.print("Enter a string: ");
String a=keyboard.getString();

String b=keyboard.getString("Enter another string: ");

System.out.println("First string: "+a);
System.out.println("Second string: "+b);
}
}
