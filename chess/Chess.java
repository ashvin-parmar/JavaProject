import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

public class Chess extends JFrame
{
private ChessBoard chessBoard;
private Container container;
public Chess()
{
initChess();
setAppearance();
}
public void initChess()
{
chessBoard=new ChessBoard();
container=getContentPane();
}
public void setAppearance()
{
container.setLayout(null);
int lm=10;
int tm=10;
chessBoard.setBounds(lm,tm,320,320);
chessBoard.setVisible(true);
JButton button=new JButton(PiecesManagement.getPiece("blackpawn"));
button.setBounds(lm+320,tm+320,40,40);
container.add(chessBoard);
container.add(button);

int w=80*8+20+20;
int h=80*8+20+40+20;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
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
pieces.put("blackpawn",new ImageIcon("/chess-resources/black_pawn.png"));
pieces.put("whitepawn",new ImageIcon("/chess-resources/white-pawn.png"));
}
public static ImageIcon getPiece(String piece)
{
return pieces.get(piece);
}
}
public static void main(String gg[])
{
Chess chess=new Chess();
}
}
