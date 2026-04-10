package com.ashvin.chess.client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ChessBoardPanel extends JPanel {
    public static final int buttonSize = 90;
    private java.util.List<java.util.List<JButton>> board;
    private Icon selectedPiece = null;
    private JButton selectedButton = null;
    private boolean myTurn = false;
    private byte myPlayerId; // 1 for white, 2 for black
    private ChessUI parentUI;
    
    private static final String[][] piecesName = {
        {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"},
        {"pawn", "pawn", "pawn", "pawn", "pawn", "pawn", "pawn", "pawn"}
    };

    public ChessBoardPanel(byte playerId, ChessUI parentUI) {
        this.myPlayerId = playerId;
        this.parentUI = parentUI;
        this.setBackground(new Color(0x1E, 0x1E, 0x1E)); // DARK_CHARCOAL
        initChessBoard();
        setAppearance();
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    private void initChessBoard() {
        setLayout(null);
        board = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            java.util.List<JButton> row = new ArrayList<>(8);
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setContentAreaFilled(false);
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                
                final int r = i;
                final int c = j;
                button.addActionListener(e -> handleSquareClick(r, c, (JButton) e.getSource()));
                row.add(button);
            }
            board.add(row);
        }
    }

    private void handleSquareClick(int logicalR, int logicalC, JButton button) {
        if (!myTurn) return;
        
        Color warmAmber = new Color(0xE6, 0x7E, 0x22);
        
        if (selectedPiece == null) {
            Icon icon = button.getIcon();
            if (icon != null) {
                boolean isWhitePiece = PiecesManagement.isWhite(icon);
                if ((myPlayerId == 1 && isWhitePiece) || (myPlayerId == 2 && !isWhitePiece)) {
                    selectedPiece = icon;
                    selectedButton = button;
                    button.setBackground(warmAmber);
                }
            }
        } else {
            int fromR = getRowIndex(selectedButton);
            int fromC = getColIndex(selectedButton);
            int toR = logicalR;
            int toC = logicalC;

            if (fromR == toR && fromC == toC) {
                // Deselect
                resetColors();
                selectedPiece = null;
                selectedButton = null;
                return;
            }
            
            Icon targetPiece = button.getIcon();
            if (targetPiece != null) {
                boolean isTargetWhite = PiecesManagement.isWhite(targetPiece);
                if ((myPlayerId == 1 && isTargetWhite) || (myPlayerId == 2 && !isTargetWhite)) {
                    // Switch selection
                    resetColors();
                    selectedPiece = targetPiece;
                    selectedButton = button;
                    button.setBackground(warmAmber);
                    return;
                }
            }
            
            if(!isValidMove(selectedPiece, fromR, fromC, toR, toC, targetPiece != null)) {
                return;
            }

            // Execute local move
            button.setIcon(selectedPiece);
            selectedButton.setIcon(null);
            resetColors();
            
            // Check win condition
            if(PiecesManagement.isBlack(targetPiece) && targetPiece.equals(PiecesManagement.getPiece("blackking"))) {
                JOptionPane.showMessageDialog(this, "White Wins!");
                parentUI.endGame(true, "White Wins!");
            } else if (PiecesManagement.isWhite(targetPiece) && targetPiece.equals(PiecesManagement.getPiece("whiteking"))) {
                JOptionPane.showMessageDialog(this, "Black Wins!");
                parentUI.endGame(true, "Black Wins!");
            }
            
            // Notify server
            parentUI.notifyMove((byte)0, (byte)fromC, (byte)fromR, (byte)toC, (byte)toR); 

            selectedPiece = null;
            selectedButton = null;
            myTurn = false;
        }
    }

    private boolean isValidMove(Icon piece, int fromR, int fromC, int toR, int toC, boolean isCapture) {
        if(piece.equals(PiecesManagement.getPiece("whitepawn"))) {
            if(toR == fromR - 1 && toC == fromC && !isCapture) return true;
            if(fromR == 6 && toR == 4 && toC == fromC && !isCapture && board.get(5).get(fromC).getIcon()==null) return true;
            if(toR == fromR - 1 && Math.abs(toC - fromC) == 1 && isCapture) return true;
            return false;
        }
        if(piece.equals(PiecesManagement.getPiece("blackpawn"))) {
            if(toR == fromR + 1 && toC == fromC && !isCapture) return true;
            if(fromR == 1 && toR == 3 && toC == fromC && !isCapture && board.get(2).get(fromC).getIcon()==null) return true;
            if(toR == fromR + 1 && Math.abs(toC - fromC) == 1 && isCapture) return true;
            return false;
        }
        
        boolean isKnight = piece.equals(PiecesManagement.getPiece("whiteknight")) || piece.equals(PiecesManagement.getPiece("blackknight"));
        if(isKnight) {
            int dr = Math.abs(toR - fromR);
            int dc = Math.abs(toC - fromC);
            return (dr == 2 && dc == 1) || (dr == 1 && dc == 2);
        }
        
        boolean isRook = piece.equals(PiecesManagement.getPiece("whiterook")) || piece.equals(PiecesManagement.getPiece("blackrook"));
        boolean isBishop = piece.equals(PiecesManagement.getPiece("whitebishop")) || piece.equals(PiecesManagement.getPiece("blackbishop"));
        boolean isQueen = piece.equals(PiecesManagement.getPiece("whitequeen")) || piece.equals(PiecesManagement.getPiece("blackqueen"));
        boolean isKing = piece.equals(PiecesManagement.getPiece("whiteking")) || piece.equals(PiecesManagement.getPiece("blackking"));

        if(isKing) {
            return Math.abs(toR - fromR) <= 1 && Math.abs(toC - fromC) <= 1;
        }
        
        if (isRook || isQueen) {
            if (fromR == toR || fromC == toC) {
                if(isPathClear(fromR, fromC, toR, toC)) return true;
            }
        }
        
        if (isBishop || isQueen) {
            if (Math.abs(toR - fromR) == Math.abs(toC - fromC)) {
                if(isPathClear(fromR, fromC, toR, toC)) return true;
            }
        }
        
        return false;
    }
    
    private boolean isPathClear(int fromR, int fromC, int toR, int toC) {
        int rStep = Integer.compare(toR, fromR);
        int cStep = Integer.compare(toC, fromC);
        
        int r = fromR + rStep;
        int c = fromC + cStep;
        while(r != toR || c != toC) {
            if(board.get(r).get(c).getIcon() != null) return false;
            r += rStep;
            c += cStep;
        }
        return true;
    }

    private int getRowIndex(JButton btn) {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (board.get(i).get(j) == btn) return i;
            }
        }
        return -1;
    }
    
    private int getColIndex(JButton btn) {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (board.get(i).get(j) == btn) return j;
            }
        }
        return -1;
    }
    
    private void resetColors() {
        Color softCream = new Color(0xF5, 0xF5, 0xDC);
        Color forestGreen = new Color(0x1B, 0x30, 0x22);
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int visualR = (myPlayerId == 1) ? i : 7 - i;
                int visualC = (myPlayerId == 1) ? j : 7 - j;
                boolean isWhiteSquare = (visualR + visualC) % 2 == 0;
                Color backgroundColor = isWhiteSquare ? softCream : forestGreen;
                board.get(i).get(j).setBackground(backgroundColor);
            }
        }
    }

    private void setAppearance() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = board.get(i).get(j);
                int visualR = (myPlayerId == 1) ? i : 7 - i;
                int visualC = (myPlayerId == 1) ? j : 7 - j;
                
                button.setBounds(visualC * buttonSize, visualR * buttonSize, buttonSize, buttonSize);
                add(button);
            }
        }
        resetColors();
        setPreferredSize(new Dimension(buttonSize * 8, buttonSize * 8));
        initializeChessPieces();
    }

    public void initializeChessPieces() {
        for (int j = 0; j <= 1; j++) {
            for (int i = 0; i < 8; i++) {
                board.get(j).get(i).setIcon(PiecesManagement.getPiece("black" + piecesName[j][i]));
            }
        }
        
        for (int j = 2; j <= 5; j++) {
            for (int i = 0; i < 8; i++) {
                board.get(j).get(i).setIcon(null);
            }
        }

        for (int j = 1; j >= 0; j--) {
            for (int i = 0; i < 8; i++) {
                board.get(7 - j).get(i).setIcon(PiecesManagement.getPiece("white" + piecesName[j][i]));
            }
        }
        
        selectedButton = null;
        selectedPiece = null;
    }
    
    public void applyOpponentMove(int fromR, int fromC, int toR, int toC) {
        JButton fromBtn = board.get(fromR).get(fromC);
        JButton toBtn = board.get(toR).get(toC);
        Icon piece = fromBtn.getIcon();
        Icon targetPiece = toBtn.getIcon(); // The piece being captured
        
        toBtn.setIcon(piece);
        fromBtn.setIcon(null);
        
        // check win
        if(PiecesManagement.isBlack(targetPiece) && targetPiece.equals(PiecesManagement.getPiece("blackking"))) {
            JOptionPane.showMessageDialog(this, "White Wins!");
            parentUI.endGame(true, "White Wins!");
        } else if (PiecesManagement.isWhite(targetPiece) && targetPiece.equals(PiecesManagement.getPiece("whiteking"))) {
            JOptionPane.showMessageDialog(this, "Black Wins!");
            parentUI.endGame(true, "Black Wins!");
        }
    }
}
