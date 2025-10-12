package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChessUI extends JFrame
{
private String username;
private JList availableMembers;
private Container container;
private NFrameworkClient client;
public ChessUI(String username)
{
super(username);
this.username=username;
initComponents();
setAppearance();
int w=500;
int h=400;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setSize(w,h);
this.setLocation(d.width/2-w/2,d.height/2-h/2);
}
private void initComponents()
{

}
private void setAppearance()
{
//do nothing
}
public void showUI()
{
this.setVisible(true);
}
}
