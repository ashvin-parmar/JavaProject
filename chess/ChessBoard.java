import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

public class ChessBoard extends JFrame
{
private java.util.List<java.util.List<JButton>> board;
private Container container;
public ChessBoard()
{
initChessBoard();
setAppearance();
}
private void initChessBoard()
{
java.util.List<JButton> row;
JButton button=null;
Color backgroundColor=null;
boolean color=true;	//true=white, false=black
board=new ArrayList<>(8);
for(int i=0;i<8;i++)
{
row=new ArrayList<>(8);
for(int j=0;j<8;j++)
{
button=new JButton();
button.setContentAreaFilled(false);
if(color)
{
//backgroundColor=new Color(100,149,237,100);
backgroundColor=Color.BLUE;
}
else
{
backgroundColor=new Color(255,255,224,100);
//backgroundColor=Color.WHITE;
}
button.setBackground(backgroundColor);
button.setOpaque(true);
button.setBorderPainted(false);
button.setFocusPainted(false);

/*
Color bc=new Color(backgroundColor.getRGB());		//final
button.setModel(new DefaultButtonModel(){
public boolean isRollover()
{
return false;
}
public boolean isClicked()
{
return false;
}
});
*/
/*
button.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent e)
{
JButton button=(JButton)e.getSource();
if(button.isEnabled())
{
button.setBackground(bc.darker());
}
}
public void mouseReleased(MouseEvent e)
{
JButton button=(JButton)e.getSource();
if(button.isEnabled())
{
button.setBackground(bc);
}
}
public void mouseClicked(MouseEvent e)
{
JButton button=(JButton)e.getSource();
}
public void mouseEntered(MouseEvent e)
{
JButton button=(JButton)e.getSource();
if(button.isEnabled())
{
if(button.getModel().isPressed())
{
button.setBackground(bc.darker());
}
else
{
button.setBackground(bc);
}
}
}
public void mouseExited(MouseEvent e)
{
JButton button=(JButton)e.getSource();
button.setBackground(bc);
}
});
*/
row.add(button);
color=!color;
}
color=!color;
board.add(row);
}




container=getContentPane();
}
private void setAppearance()
{
JButton button;
container.setLayout(null);
int lm=10;
int tm=10;
for(int i=0;i<8;i++)
{
for(int j=0;j<8;j++)
{
button=board.get(i).get(j);
button.setBounds(lm+(j*80),tm+(i*80),80,80);
button.repaint();
container.add(button);
}
}

initializeChessPieces();

int w=80*8+20;
int h=80*8+20+40;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public void initializeChessPieces()
{
String places[][]={{"rook","knight","bishop","king","queen","bishop","knight","rook"},{"pawn","pawn","pawn","pawn","pawn","pawn","pawn","pawn"}}

}
public class PiecesManagement 
{
private static Map<String,ImageIcon> pieces;
public PiecesManagement()
{
}
static 
{
pieces=new HashMap<>();
//(String piece1,String piece2)->{
//return piece1.compareToIgnoreCase(piece2);
//}
ImageIcon image=new ImageIcon("/chess-resources/black_pawn.png");
if(image==null) System.out.println("image is null");
pieces.put("blackpawn",new ImageIcon("chess-resources/black_pawn.png"));
pieces.put("whitepawn",new ImageIcon("/chess-resources/white-pawn.png"));
}
public static ImageIcon getPiece(String piece)
{
return pieces.get(piece);
}
}



public static void main(String gg[])
{
ChessBoard cb=new ChessBoard();
cb.setVisible(true);
}
}
