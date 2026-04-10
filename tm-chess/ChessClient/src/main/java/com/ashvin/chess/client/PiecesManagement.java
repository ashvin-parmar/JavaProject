package com.ashvin.chess.client;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PiecesManagement 
{
    private static Map<String,ImageIcon> pieces;
    
    private PiecesManagement() {}
    
    static 
    {
        pieces = new HashMap<>();
        String[] blackRow1 = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
        String[] whiteRow1 = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
        String pawn = "pawn";
        
        int buttonSize = ChessBoardPanel.buttonSize;
        
        for(int i = 0; i < 8; i++) {
            // Black main pieces
            pieces.put("black" + blackRow1[i], createScaledIcon("chess-resources/theme2/black_" + blackRow1[i] + ".png", buttonSize));
            // White main pieces
            pieces.put("white" + whiteRow1[i], createScaledIcon("chess-resources/theme2/white_" + whiteRow1[i] + ".png", buttonSize));
            // Pawns
            pieces.put("black" + pawn, createScaledIcon("chess-resources/theme2/black_" + pawn + ".png", buttonSize));
            pieces.put("white" + pawn, createScaledIcon("chess-resources/theme2/white_" + pawn + ".png", buttonSize));
        }
    }
    
    private static ImageIcon createScaledIcon(String path, int size) {
        java.net.URL imgURL = PiecesManagement.class.getResource("/" + path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(size - 40, size - 40, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } else {
            System.err.println("Couldn't find file: " + path);
            return new ImageIcon();
        }
    }

    public static ImageIcon getPiece(String piece) {
        return pieces.get(piece);
    }

    public static boolean isBlack(Icon piece) {
        if(piece == null) return false;
        if(piece.equals(pieces.get("blackpawn"))) return true;
        String[] blackRow1 = {"rook", "knight", "bishop", "queen", "king"};
        for(String p : blackRow1) {
            if(pieces.get("black" + p).equals(piece)) return true;
        }
        return false;
    }

    public static boolean isWhite(Icon piece) {
        if(piece == null) return false;
        if(piece.equals(pieces.get("whitepawn"))) return true;
        String[] whiteRow1 = {"rook", "knight", "bishop", "queen", "king"};
        for(String p : whiteRow1) {
            if(pieces.get("white" + p).equals(piece)) return true;
        }
        return false;
    }
}
