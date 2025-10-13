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
JButton whiteKing=null;
JButton blackKing=null;
boolean whiteMove=true;
private static java.util.List<java.util.List<JButton>> board;
private Container container;
private static String piecesName[][]={
{"rook","knight","bishop","king","queen","bishop","knight","rook"},
{"pawn","pawn","pawn","pawn","pawn","pawn","pawn","pawn"}
};
public static int buttonSize=90;
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
//backgroundColor=Color.BLUE;
backgroundColor=Color.BLACK;
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
//effect of selection [pending]
}
else
{
int x=button.getX();
int y=button.getY();
int sx=selectedButton.getX();
int sy=selectedButton.getY();
if(x==sx && y==sy) return;
//System.out.println("x: "+x+" y: "+y+" sx: "+sx+" sy: "+sy);

Icon buttonPiece=button.getIcon();

if(PiecesManagement.isBlack(selectedPiece))
{
if(PiecesManagement.isBlack(buttonPiece))
{
selectedButton=button;
selectedPiece=buttonPiece;
//effect of selection [pending]
return;
}
}
else
{
if(PiecesManagement.isWhite(buttonPiece))
{
if(selectedButton.equals(whiteKing) && PiecesManagement.isWhiteRook(buttonPiece))
{
//Castling
System.out.println("Chess applied for castling");
}
else
{
selectedButton=button;
selectedPiece=buttonPiece;
//effect of selection [pending]
}
return ;
}
}

//Black Pawn move validation
if(PiecesManagement.isBlackPawn(selectedPiece))
{
if(button.getIcon()==null)
{
if(!((sx==x && sy==(y-buttonSize)) || (sy==buttonSize && sx==x && sy==(y-2*buttonSize) && board.get(y/buttonSize-1).get(x/buttonSize).getIcon()==null))) return;
}
else
{
if(sx!=(x-buttonSize) && sx!=(x+buttonSize)) return ;
if(sy!=(y-buttonSize)) return ;
}
}
else if(PiecesManagement.isWhitePawn(selectedPiece))
{
if(button.getIcon()==null)
{
if(!((sx==x && sy==(y+buttonSize)) || (sy==6*buttonSize && sx==x && sy==(y+2*buttonSize) && board.get(y/buttonSize+1).get(x/buttonSize).getIcon()==null))) return;
}
else
{
if(sx!=(x-buttonSize) && sx!=(x+buttonSize)) return ;
if(sy!=(y+buttonSize)) return ;
}
}
else if(PiecesManagement.isKing(selectedPiece))
{
if(sx==x)
{
if(!(sy==y || sy==y-buttonSize || sy==y+buttonSize)) return;
}
else if(sx-buttonSize==x || sx+buttonSize==x)
{
if(!(sy==y || sy==y-buttonSize || sy==y+buttonSize)) return ;
}
else return ;
}
else if(PiecesManagement.isQueen(selectedPiece))
{
if(sx==x || sy==y)
{
if(ChessBoard.isValidPlusPath(selectedButton,button)==false) return ; 
}
else
{
if(isValidCrossPath(selectedButton,button)==false) return ;
}
}
else if(PiecesManagement.isRook(selectedPiece))
{
if(sx==x || sy==y)
{
if(isValidPlusPath(selectedButton,button)==false) return ; 
}
else
{
return ;
}
}
else if(PiecesManagement.isKnight(selectedPiece))
{
if(sx==x+buttonSize || sx==x-buttonSize || sx==x+2*buttonSize || sx==x-2*buttonSize)
{
if(sx==x+buttonSize || sx==x-buttonSize)
{
if(!(sy==y-2*buttonSize || sy==y+2*buttonSize)) return ; 
}
if(sx==x+2*buttonSize || sx==x-2*buttonSize) 
{
if(!(sy==y-buttonSize || sy==y+buttonSize)) return ;
}
}
else
{
return ;
}
}
else if(PiecesManagement.isBishop(selectedPiece))	//We can use only else
{
if(sx==x || sy==y)
{
return ;
}
else
{
if(isValidCrossPath(selectedButton,button)==false) return ;
}
}
button.setIcon(selectedPiece);
selectedButton.setIcon(null);
if(PiecesManagement.isWhiteKing(selectedPiece))
{
whiteKing=button;
}
else if(PiecesManagement.isBlackKing(selectedPiece))
{
blackKing=button;
}
if(PiecesManagement.isWhiteKing(buttonPiece))
{
JOptionPane.showMessageDialog(ChessBoard.this,"Black Wins","Game ends",JOptionPane.INFORMATION_MESSAGE);
System.out.println("Black wins");
initializeChessPieces();
return ;
//System.exit(0);
}
else if(PiecesManagement.isBlackKing(buttonPiece))
{
JOptionPane.showMessageDialog(ChessBoard.this,"White Wins","Game ends",JOptionPane.INFORMATION_MESSAGE);
System.out.println("White wins");
initializeChessPieces();
return ;
//System.exit(0);
}
whiteMove=!whiteMove;
selectedButton=(whiteMove?whiteKing:blackKing);
selectedPiece=selectedButton.getIcon();
//unselect effect
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
button.setBounds(lm+(j*buttonSize),tm+(i*buttonSize),buttonSize,buttonSize);
button.repaint();
container.add(button);
}
}

initializeChessPieces();


int w=buttonSize*8;
int h=buttonSize*8+40;
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
for(int j=2;j<=5;j++)
{
for(int i=0;i<8;i++)
{
board.get(j).get(i).setIcon(null);
}
}

for(int j=1;j>=0;j--)
{
for(int i=0;i<8;i++)
{
board.get(7-j).get(i).setIcon(PiecesManagement.getPiece("white"+piecesName[j][i]));
}
}
selectedButton=board.get(7).get(3);
selectedPiece=selectedButton.getIcon();
whiteMove=true;
whiteKing=selectedButton;
blackKing=board.get(0).get(3);
}
public static boolean isValidCrossPath(JButton fromButton,JButton toButton)
{
int si=(fromButton.getX()/buttonSize);
int sj=(fromButton.getY()/buttonSize);
int i=(toButton.getX()/buttonSize);
int j=(toButton.getY()/buttonSize); 
if(si==i || sj==j) return false;
int li=si-1;
int ri=si+1;
int uj=sj-1;
int dj=sj+1;
Boolean diagonals[]={true,true,true,true};
//System.out.printf("si: %d, sj: %d, i: %d, j:%d\n",si,sj,i,j);
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
JButton midButton;
java.util.List<JButton> list1=null;
java.util.List<JButton> list2=null;
while(li>=0 || ri<8 || uj>=0 || dj<8)
{
if(uj>=0) 
{
list1=board.get(uj);
}
if(dj<8)
{
list2=board.get(dj);
}
if(list1==null)
{
diagonals[0]=false;
diagonals[1]=false;
}
if(list2==null)
{
diagonals[2]=false;
diagonals[3]=false;
}
if(li>=0)
{
if(diagonals[0])
{
if(li==i && uj==j) return true;
midButton=list1.get(li);
if(midButton.getIcon()!=null) diagonals[0]=false;
}
if(diagonals[2])
{
if(li==i && dj==j) return true;
midButton=list2.get(li);
if(midButton.getIcon()!=null) diagonals[2]=false;
}
}
if(ri<8)
{
if(diagonals[1])
{
if(ri==i && uj==j) return true;
midButton=list1.get(ri);
if(midButton.getIcon()!=null) diagonals[1]=false;
}
if(diagonals[3])
{
if(ri==i && dj==j) return true;
midButton=list2.get(ri);
if(midButton.getIcon()!=null) diagonals[3]=false;
}
}
li--;
ri++;
uj--;
dj++;
}
return false;
}
public static boolean isValidPlusPath(JButton fromButton,JButton toButton)
{
int si=(fromButton.getX()/buttonSize);
int sj=(fromButton.getY()/buttonSize);
int i=(toButton.getX()/buttonSize);
int j=(toButton.getY()/buttonSize); 
if(!(i==si || j==sj)) return false;
int li=si-1;
int ri=si+1;
int uj=sj-1;
int dj=sj+1;
//System.out.printf("si: %d, sj: %d, i: %d, j:%d\n",si,sj,i,j);
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
JButton midButton;
if(sj==j)
{
while(li>=0 || ri<8)
{
if(li>=0) 
{
if(li==i && sj==j) return true;
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
midButton=board.get(sj).get(li);
if(midButton.getIcon()!=null) li=0;
li--;
}
if(ri<8)
{
if(ri==i && sj==j) return true;
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
midButton=board.get(sj).get(ri);
if(midButton.getIcon()!=null) ri=8;
ri++;
}
}
}
else
{
while(uj>=0 || dj<8)
{
if(uj>=0) 
{
if(uj==j && si==i) return true;
midButton=board.get(uj).get(si);
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
if(midButton.getIcon()!=null) uj=0;
uj--;
}
if(dj<8)
{
if(dj==j && si==i) return true;
midButton=board.get(dj).get(si);
//System.out.printf("li: %d ri: %d uj: %d dj: %d\n",li,ri,uj,dj);
if(midButton.getIcon()!=null) dj=8;
dj++;
}
}
}
return false;
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
Image scaledImage;
ImageIcon pieceIcon;
for(int j=0;j<=1;j++)
{
for(int i=0;i<8;i++)
{
pieceName=ChessBoard.piecesName[j][i];

//pieces.put("black"+pieceName,new ImageIcon("chess-resources/theme2/black_"+pieceName+".png"));
//pieces.put("white"+pieceName,new ImageIcon("chess-resources/theme2/white_"+pieceName+".png"));
pieceIcon=new ImageIcon("chess-resources/theme2/black_"+pieceName+".png");
scaledImage=pieceIcon.getImage().getScaledInstance(buttonSize-40,buttonSize-40,Image.SCALE_SMOOTH);
pieceIcon=new ImageIcon(scaledImage);
pieces.put("black"+pieceName,pieceIcon);

pieceIcon=new ImageIcon("chess-resources/theme2/white_"+pieceName+".png");
scaledImage=pieceIcon.getImage().getScaledInstance(buttonSize-40,buttonSize-40,Image.SCALE_SMOOTH);
pieceIcon=new ImageIcon(scaledImage);
pieces.put("white"+pieceName,pieceIcon);
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
if(piece==null) return false;
ImageIcon p=pieces.get("blackpawn");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhitePawn(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("whitepawn");
p=pieces.get("whitepawn");
if(piece.equals(p)) return true;
return false;
}
public static boolean isKing(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackking");
if(piece.equals(p)) return true;
p=pieces.get("whiteking");
if(piece.equals(p)) return true;
return false;
}
public static boolean isQueen(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackqueen");
if(piece.equals(p)) return true;
p=pieces.get("whitequeen");
if(piece.equals(p)) return true;
return false;
}
public static boolean isRook(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackrook");
if(piece.equals(p)) return true;
p=pieces.get("whiterook");
if(piece.equals(p)) return true;
return false;
}
public static boolean isKnight(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackknight");
if(piece.equals(p)) return true;
p=pieces.get("whiteknight");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBishop(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackbishop");
if(piece==p) return true;
p=pieces.get("whitebishop");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBlackKing(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackking");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBlackQueen(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackqueen");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBlackRook(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackrook");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBlackKnight(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackknight");
if(piece.equals(p)) return true;
return false;
}
public static boolean isBlackBishop(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("blackbishop");
if(piece==p) return true;
return false;
}
public static boolean isWhiteKing(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("whiteking");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhiteQueen(Icon piece)
{
if(piece==null) return false;
ImageIcon p=pieces.get("whitequeen");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhiteRook(Icon piece)
{
ImageIcon p=pieces.get("whiterook");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhiteKnight(Icon piece)
{
ImageIcon p=pieces.get("whiteknight");
if(piece.equals(p)) return true;
return false;
}
public static boolean isWhiteBishop(Icon piece)
{
ImageIcon p=pieces.get("whitebishop");
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
