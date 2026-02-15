import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

class ChatBotServerUI extends JFrame
{
private JLabel onlineUsersLabel;
private JList onlineUsersList;
private JButton shutDownButton;

private ImageIcon logoIcon;
private Container container;

public ChatBotServerUI()
{
initComponents();
}
private void initComponents()
{
container=getContentPane();
onlineUsersLabel=new JLabel("Online Users");
onlineUsersList=new JList();
shutDownButton=new JButton("Shut-Down");
container.setLayout(new BorderLayout());

onlineUsersList.add(new JLabel("Sameer"));
onlineUsersList.add(new JLabel("Priyanka"));
onlineUsersList.add(new JLabel("Ramesh"));

JTable table=new JTable();
table.add(onlineUsersList);

container.add(onlineUsersLabel,BorderLayout.NORTH);
container.add(table);
Panel panel=new Panel(new GridLayout(1,3));
panel.add(new JLabel("              "));
panel.add(new JLabel("              "));
panel.add(shutDownButton);
container.add(panel,BorderLayout.SOUTH);

int w=500;
int h=560;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String args[])
{
ChatBotServerUI bot=new ChatBotServerUI();
bot.setVisible(true);
}
}
