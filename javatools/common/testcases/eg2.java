import com.thinking.machines.utils.*;

class TestKeyboard2
{
public static void main(String gg[])
{
Keyboard k=new Keyboard();
//Testing all With string to test all cases

String a=k.getString("Enter a string: ");
long b=k.getLong("Enter a long: ");
int c=k.getInt("Enter a int: ");
short d=k.getShort("Enter a short: ");
byte e=k.getByte("Enter a byte: ");
float f=k.getFloat("Enter a float: ");
double g=k.getDouble("Enter a double: ");
char h=k.getCharacter("Enter a character: ");
boolean i=k.getBoolean("Enter a boolean: ");

System.out.println(a);
System.out.println(b);
System.out.println(c);
System.out.println(d);
System.out.println(e);
System.out.println(f);
System.out.println(g);
System.out.println(h);
System.out.println(i);


}
}
