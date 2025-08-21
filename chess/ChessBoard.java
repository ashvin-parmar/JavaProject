import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;

public class ChessBoard extends JFrame
{
Icon selectedPiece=null;
JButton selectedButton=null;
private boolean whiteMove=true;
private java.util.List<java.util.List<JButton>> board;
private Container container;
private static String piecesName[][]={
{"rook","knight","bishop","king","queen","bishop","knight","rook"},
{"pawn","pawn","pawn","pawn","pawn","pawn","pawn","pawn"}
};
public static int buttonSize=80;
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
//backgroundColor=new Color(255,255,224,100);
backgroundColor=Color.WHITE;
}
button.setBackground(backgroundColor);
button.setOpaque(true);
button.setBorderPainted(false);
button.setFocusPainted(false);
button.setOpaque(true);
Color bc=new Color(backgroundColor.getRGB());		//final
button.setModel(new DefaultButtonModel(){
public boolean isRollover()
{
return false;
}
/*
public boolean isClicked()
{
return false;
}
*/
});
button.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent e)
{
JButton button=(JButton)e.getSource();
if(selectedPiece==null) 
{
selectedPiece=button.getIcon();
selectedButton=button;
}
else
{
int x=button.getX();
int y=button.getY();
int sx=selectedButton.getX();
int sy=selectedButton.getY();
if(x==sx && y==sy) return;

Icon buttonPiece=button.getIcon();
//Something crazy

if(PiecesManagement.isBlack(selectedPiece))
{
if(PiecesManagement.isBlack(buttonPiece))
{
selectedButton=button;
selectedPiece=buttonPiece;
return;
}
//Black Pawn move validation
if(PiecesManagement.isBlackPawn(selectedPiece))
{
if(button.getIcon()==null)
{
System.out.println("black: x: "+x+" y: "+y+" sx: "+sx+" sy: "+sy);
if(!((sx==x && sy==(y-buttonSize)) || (sy==buttonSize && sx==x && sy==(y-2*buttonSize)))) return;
}
else
{
if(sx!=(x-buttonSize) && sx!=(x+buttonSize)) return ;
if(sy!=(y-buttonSize)) return ;
}
}
}
else
{
if(PiecesManagement.isWhite(buttonPiece))
{
selectedButton=button;
selectedPiece=buttonPiece;
return ;
}
//White Pawn coding
if(PiecesManagement.isWhitePawn(selectedPiece))
{
if(button.getIcon()==null)
{
System.out.println("x: "+x+" y: "+y+" sx: "+sx+" sy: "+sy);
if(!((sx==x && sy==(y+buttonSize)) || (sy==6*buttonSize && sx==x && sy==(y+2*buttonSize)))) return;
}
else
{
if(sx!=(x-buttonSize) && sx!=(x+buttonSize)) return ;
if(sy!=(y+buttonSize)) return ;
}
}
}
button.setIcon(selectedPiece);
selectedButton.setIcon(null);
selectedPiece=null;
whiteMove=!whiteMove;
System.out.println("end==? x: "+x+" y: "+y+" sx: "+sx+" sy: "+sy);
//System.out.println("Now Move: "+(whiteMove==false?"Black":"White"));
}
}
public void mouseReleased(MouseEvent e)
{
JButton button=(JButton)e.getSource();
}
public void mouseClicked(MouseEvent e)
{
JButton button=(JButton)e.getSource();
}
public void mouseEntered(MouseEvent e)
{
JButton button=(JButton)e.getSource();
}
public void mouseExited(MouseEvent e)
{
JButton button=(JButton)e.getSource();
}
});
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
int lm=0;
int tm=0;
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

int w=80*8;
int h=80*8+40;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public void initializeChessPieces()
{
for(int j=0;j<=1;j++)
{
for(int i=0;i<8;i++)
{
board.get(j).get(i).setIcon(PiecesManagement.getPiece("black"+piecesName[j][i]));
}
}
/*		//Optimize
ImageIcon icon=PiecesManagement.getPiece("blackpawn");
for(int i=0;i<8;i++)
{
board.get(1).get(i).setIcon(icon);
}
*/
for(int j=1;j>=0;j--)
{
for(int i=0;i<8;i++)
{
board.get(7-j).get(i).setIcon(PiecesManagement.getPiece("white"+piecesName[j][i]));
}
}

}
class PiecesManagement 
{
private static Map<String,ImageIcon> pieces;
private PiecesManagement()
{
}
static 
{
pieces=new HashMap<>();
//(String piece1,String piece2)->{
//return piece1.compareToIgnoreCase(piece2);
//}
String pieceName;
for(int j=0;j<=1;j++)
{
for(int i=0;i<8;i++)
{
pieceName=ChessBoard.piecesName[j][i];
pieces.put("black"+pieceName,new ImageIcon("chess-resources/black_"+pieceName+".png"));
pieces.put("white"+pieceName,new ImageIcon("chess-resources/white_"+pieceName+".png"));
}
}
}
public static ImageIcon getPiece(String piece)
{
return pieces.get(piece);
}
public static boolean isBlack(Icon piece)
{
if(piece==null) return false;
if(isBlackPawn(piece)) return true;
for(int i=0;i<5;i++)
{
if(pieces.get("black"+piecesName[0][i]).equals(piece)) return true;
}
return false;
}
public static boolean isWhite(Icon piece)
{
if(piece==null) return false;
if(isWhitePawn(piece)) return true;
for(int i=0;i<5;i++)
{
if(pieces.get("white"+piecesName[0][i]).equals(piece)) return true;
}
return false;
}
public static boolean isBlackPawn(Icon piece)
{
ImageIcon p=pieces.get("blackpawn");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhitePawn(Icon piece)
{
ImageIcon p=pieces.get("whitepawn");
p=pieces.get("whitepawn");
if(piece.equals(p)) return true;
return false;
}
public static boolean isKing(ImageIcon piece)
{
ImageIcon p=pieces.get("blackking");
if(piece.equals(p)) return true;
p=pieces.get("whiteking");
if(piece.equals(p)) return true;
return false;
}
public static boolean isQueen(ImageIcon piece)
{
ImageIcon p=pieces.get("blackqueen");
if(piece.equals(p)) return true;
p=pieces.get("whitequeen");
if(piece.equals(p)) return true;
return false;
}
public static boolean isRook(ImageIcon piece)
{
ImageIcon p=pieces.get("blackrook");
if(piece.equals(p)) return true;
p=pieces.get("whiterook");
if(piece.equals(p)) return true;
return false;
}
public static boolean isKnight(ImageIcon piece)
{
ImageIcon p=pieces.get("blackknight");
if(piece.equals(p)) return true;
p=pieces.get("whiteknight");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBishop(ImageIcon piece)
{
ImageIcon p=pieces.get("blackknight");
if(piece==p) return true;
p=pieces.get("whiteknight");
if(piece.equals(p)) return true;
return false;
}
}

public static void main(String gg[])
{
ChessBoard cb=new ChessBoard();
cb.setVisible(true);
}
}
