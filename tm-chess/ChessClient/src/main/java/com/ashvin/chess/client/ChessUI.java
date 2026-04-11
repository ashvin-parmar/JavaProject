package com.ashvin.chess.client;

import com.ashvin.nframework.client.*;
import com.ashvin.chess.server.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

public class ChessUI extends JFrame {
    private String username;
    private JTable availableMembersList;
    private JScrollPane availableMembersListScrollPane;
    private AvailableMembersListModel availableMembersListModel;
    private javax.swing.Timer timer;
    private javax.swing.Timer gameTimer;
    private Container container;
    private NFrameworkClient client;
    private JPanel rightPanel;
    private JPanel leftPanel;
    
    private ChessBoardPanel chessBoardPanel;
    private String gameId = null;
    private byte myPlayerId = 0; // 1 for white (sender), 2 for black (receiver)
    private String opponentName = "";

    private static final Color DARK_CHARCOAL = new Color(0x1E1E1E);
    private static final Color DEEP_NAVY = new Color(0x121826);
    private static final Color FOREST_GREEN = new Color(0x1B3022);
    private static final Color BRIGHT_GOLD = new Color(0xFFD700);
    private static final Color WARM_AMBER = new Color(0xE67E22);
    private static final Color SOFT_CREAM = new Color(0xF5F5DC);

    public ChessUI(String username)
    {
        super("Chess - " + username);
        this.username=username;
        this.client=new NFrameworkClient("localhost",5050);
        initComponents();
        setAppearance();
        initEventListeners();
        
        // Full screen
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void initComponents()
    {
        rightPanel=new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setBackground(DEEP_NAVY);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        this.availableMembersListModel=new AvailableMembersListModel();
        this.availableMembersList=new JTable(availableMembersListModel);
        this.availableMembersList.setRowHeight(40);
        this.availableMembersList.setBackground(DEEP_NAVY);
        this.availableMembersList.setForeground(SOFT_CREAM);
        this.availableMembersList.setFont(new Font("Arial", Font.PLAIN, 16));
        this.availableMembersList.setGridColor(DARK_CHARCOAL);
        this.availableMembersList.setShowVerticalLines(false);
        
        JTableHeader header=availableMembersList.getTableHeader();
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);
        header.setBackground(FOREST_GREEN);
        header.setForeground(BRIGHT_GOLD);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setPreferredSize(new Dimension(header.getWidth(), 50));
        
        TableColumnModel columnModel=header.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(250);
        columnModel.getColumn(1).setPreferredWidth(60);
        
        this.availableMembersList.getColumn("").setCellRenderer(new AvailableMembersListButtonCellRenderer());
        this.availableMembersList.getColumn("").setCellEditor(new AvailableMembersListButtonCellEditor());
        
        this.availableMembersListScrollPane=new JScrollPane(this.availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.availableMembersListScrollPane.getViewport().setBackground(DEEP_NAVY);
        this.availableMembersListScrollPane.setBorder(BorderFactory.createLineBorder(BRIGHT_GOLD, 2));
        
        rightPanel.add(availableMembersListScrollPane, BorderLayout.CENTER);
        
        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(DARK_CHARCOAL);

        container=getContentPane();
        container.setLayout(new BorderLayout());
        container.add(rightPanel,BorderLayout.EAST);
        container.add(leftPanel, BorderLayout.CENTER);
    }
    
    private void setAppearance()
    {
        container.setBackground(DARK_CHARCOAL);
    }
    
    private void initEventListeners()
    {
        this.timer=new javax.swing.Timer(1000,new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                timer.stop();
                try
                {
                    // 1. Get Members
                    Object[] args={username};
                    java.util.List<String> members=(java.util.List<String>)client.execute("/ChessServer/getMembers",args);
                    availableMembersListModel.setMembers(members);
                    
                    // 2. Get Messages
                    Object[] msgArgs={username};
                    java.util.List<?> messages=(java.util.List<?>)client.execute("/ChessServer/getMessages",msgArgs);
                    if(messages != null) {
                        for(Object msgObj : messages) {
                            Message msg = parseMessage(msgObj);
                            if(msg == null) continue;
                            
                            if (msg.type == MESSAGE_TYPE.CHALLENGE) {
                                int result = JOptionPane.showConfirmDialog(ChessUI.this, msg.fromUsername + " has challenged you. Accept?", "Challenge Received", JOptionPane.YES_NO_OPTION);
                                if (result == JOptionPane.YES_OPTION) {
                                    client.execute("/ChessServer/acceptInvitation", username, msg.fromUsername);
                                    startGame(2, msg.fromUsername); // receiver is player 2 (black)
                                } else {
                                    client.execute("/ChessServer/rejectInvitation", username, msg.fromUsername);
                                }
                            } else if (msg.type == MESSAGE_TYPE.CHALLENGE_ACCEPTED) {
                                JOptionPane.showMessageDialog(ChessUI.this, msg.fromUsername + " accepted your challenge!");
                                startGame(1, msg.fromUsername); // sender is player 1 (white)
                            } else if (msg.type == MESSAGE_TYPE.CHALLENGE_REJECTED) {
                                JOptionPane.showMessageDialog(ChessUI.this, msg.fromUsername + " rejected your challenge.");
                                availableMembersListModel.resetAwaiting();
                            }
                        }
                    }
                    
                }catch(Throwable t)
                {
                    // Ignore transient network errors
                }
                if (availableMembersListModel.isAwaitingInvitationReply() && availableMembersListModel.getInviteTime() > 0) {
                    if (System.currentTimeMillis() - availableMembersListModel.getInviteTime() > 15000) {
                        JOptionPane.showMessageDialog(ChessUI.this, "Invite timed out.");
                        availableMembersListModel.resetAwaiting();
                    }
                }
                if (gameId == null) {
                    timer.start();
                }
            }
        });
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we)
            {
                if(timer != null) timer.stop();
                if(gameTimer != null) gameTimer.stop();
                try
                {
                    Object[] args={username};
                    ChessUI.this.client.execute("/ChessServer/logout",args);
                }catch(Throwable t) {}
                System.exit(0);
            }
        });
        
        timer.start();
    }
    
    // Helper to parse message from framework which might return Map/LinkedTreeMap due to Gson
    private Message parseMessage(Object obj) {
        if (obj instanceof Message) return (Message) obj;
        if (obj instanceof Map) {
            Map m = (Map) obj;
            Message msg = new Message();
            msg.fromUsername = (String) m.get("fromUsername");
            msg.toUsername = (String) m.get("toUsername");
            String typeStr = (String) m.get("type");
            if (typeStr != null) {
                msg.type = MESSAGE_TYPE.valueOf(typeStr);
            }
            return msg;
        }
        return null;
    }

    private Move parseMove(Object obj) {
        if (obj instanceof Move) return (Move) obj;
        if (obj instanceof Map) {
            Map m = (Map) obj;
            Move move = new Move();
            move.player = ((Number) m.get("player")).byteValue();
            move.piece = ((Number) m.get("piece")).byteValue();
            move.fromX = ((Number) m.get("fromX")).byteValue();
            move.fromY = ((Number) m.get("fromY")).byteValue();
            move.toX = ((Number) m.get("toX")).byteValue();
            move.toY = ((Number) m.get("toY")).byteValue();
            return move;
        }
        return null;
    }
    
    private JLabel turnLabel;
    private int localMoveCount = 0;
    
    private void startGame(int playerId, String opponentName) {
        if(timer != null) timer.stop();
        this.myPlayerId = (byte) playerId;
        this.opponentName = opponentName;
        this.localMoveCount = 0;
        
        // Clear any pending invite states since we're entering a game
        if (availableMembersListModel != null) {
            availableMembersListModel.resetAwaiting();
        }
        
        try {
            this.gameId = (String) client.execute("/ChessServer/getGameId", username);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        
        leftPanel.removeAll();
        chessBoardPanel = new ChessBoardPanel(myPlayerId, this);
        leftPanel.add(chessBoardPanel);
        
        rightPanel.removeAll();
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        infoPanel.setBackground(DEEP_NAVY);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BRIGHT_GOLD, 2),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        JLabel roleLabel = new JLabel("You are " + (playerId == 1 ? "White" : "Black"), SwingConstants.CENTER);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        roleLabel.setForeground(SOFT_CREAM);
        infoPanel.add(roleLabel);
        
        turnLabel = new JLabel("Waiting for " + opponentName + "...", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel.setForeground(WARM_AMBER);
        infoPanel.add(turnLabel);
        
        JButton resignBtn = new JButton("Resign");
        resignBtn.setFont(new Font("Arial", Font.BOLD, 18));
        resignBtn.setBackground(DARK_CHARCOAL);
        resignBtn.setForeground(WARM_AMBER);
        resignBtn.setFocusPainted(false);
        resignBtn.addActionListener(e -> endGame(true, "resigned"));
        
        JPanel btnWrapper = new JPanel(new FlowLayout());
        btnWrapper.setBackground(DEEP_NAVY);
        btnWrapper.add(resignBtn);
        infoPanel.add(btnWrapper);

        rightPanel.add(infoPanel, BorderLayout.NORTH);
        
        revalidate();
        repaint();
        
        gameTimer = new javax.swing.Timer(500, e -> {
            gameTimer.stop();
            try {
                boolean active = (boolean) client.execute("/ChessServer/isGameActive", gameId);
                if (!active) {
                    String outcome = (String) client.execute("/ChessServer/getGameOutcome", gameId);
                    if (outcome == null) outcome = "resigned";
                    
                    if (outcome.equals("resigned")) {
                        JOptionPane.showMessageDialog(ChessUI.this, opponentName + " resigned. You win!");
                    } else {
                        JOptionPane.showMessageDialog(ChessUI.this, outcome);
                    }
                    endGame(false, null);
                    return;
                }
                
                Object countObj = client.execute("/ChessServer/getMoveCount", gameId);
                int serverMoveCount = (countObj instanceof Number) ? ((Number) countObj).intValue() : 0;
                
                while(localMoveCount < serverMoveCount) {
                    Object moveObj = client.execute("/ChessServer/getMoveAt", gameId, localMoveCount);
                    if (moveObj != null) {
                        Move move = parseMove(moveObj);
                        if (move != null && move.player != myPlayerId) {
                            chessBoardPanel.applyOpponentMove(move.fromY, move.fromX, move.toY, move.toX);
                        }
                    }
                    localMoveCount++;
                }
                
                boolean canPlay = (boolean) client.execute("/ChessServer/canIPlay", gameId, username);
                chessBoardPanel.setMyTurn(canPlay);
                
                if (canPlay) {
                    turnLabel.setText("Your Turn!");
                    turnLabel.setForeground(BRIGHT_GOLD);
                } else {
                    turnLabel.setText(opponentName + "'s Turn...");
                    turnLabel.setForeground(SOFT_CREAM);
                }
            } catch (Throwable t) {
                t.printStackTrace(); // Log error instead of silent swallow
            }
            if(gameId != null) {
                gameTimer.start();
            }
        });
        gameTimer.start();
    }
    
    public void endGame(boolean notifyServer, String outcome) {
        if(gameTimer != null) gameTimer.stop();
        if(notifyServer && gameId != null) {
            try {
                if(outcome == null) outcome = "resigned";
                client.execute("/ChessServer/endGame", gameId, outcome);
            } catch(Throwable t) {}
        }
        gameId = null;
        leftPanel.removeAll();
        rightPanel.removeAll();
        rightPanel.add(availableMembersListScrollPane);
        timer.start();
        revalidate();
        repaint();
    }
    
    public void notifyMove(byte pieceId, byte fromX, byte fromY, byte toX, byte toY) {
        try {
            client.execute("/ChessServer/submitMove", gameId, username, pieceId, fromX, fromY, toX, toY);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void showUI()
    {
        this.setVisible(true);
    }
    
    private void sendInvitation(String toUsername)
    {
        try
        {
            client.execute("/ChessServer/inviteMember",username,toUsername);
        }catch(Throwable t)
        {
            JOptionPane.showMessageDialog(this,t.toString());
        }
    }
    
    class AvailableMembersListModel extends AbstractTableModel
    {
        private java.util.List<String> members;
        private String titles[]={"Online Players",""};
        private java.util.List<JButton> inviteButtons;
        private boolean awaitingInvitationReply=false;
        private long inviteTime=0;
        
        AvailableMembersListModel()
        {
            inviteButtons=new LinkedList<>();
            members=new LinkedList<>();
        }
        
        public boolean isAwaitingInvitationReply() { return awaitingInvitationReply; }
        public long getInviteTime() { return inviteTime; }
        
        public void resetAwaiting() {
            awaitingInvitationReply = false;
            inviteTime = 0;
            for(JButton inviteButton:inviteButtons) {
                inviteButton.setEnabled(true);
                inviteButton.setText("[+]");
            }
            fireTableDataChanged();
        }

        public int getColumnCount()
        {
            return this.titles.length;
        }
        public int getRowCount()
        {
            return this.members.size();
        }
        public Class getColumnClass(int column)
        {
            if(column==0) return String.class;
            return JButton.class;
        }
        public String getColumnName(int column)
        {
            return this.titles[column];
        }
        public Object getValueAt(int row,int column)
        {
            if(column==0) return "  " + this.members.get(row);
            return inviteButtons.get(row);
        }
        public void setValueAt(Object value,int row,int column)
        {
            if(column==1)
            {
                String text=value.toString();
                try
                {
                    if(text.equalsIgnoreCase("Invited"))
                    {
                        awaitingInvitationReply=true;
                        inviteTime = System.currentTimeMillis();
                        for(JButton inviteButton:inviteButtons)
                        {
                            inviteButton.setEnabled(false);
                            inviteButton.setText("[+]");
                        }
                        JButton button=this.inviteButtons.get(row);
                        button.setText("..."); // Indicate waiting
                        fireTableDataChanged();
                        ChessUI.this.sendInvitation(this.members.get(row));
                    }
                }catch(Exception e)
                {
                }
            }
        }
        public void setMembers(java.util.List<String> members)
        {
            if(awaitingInvitationReply) return ;
            this.members=members;
            this.inviteButtons.clear();
            for(int i=0;i<members.size();i++)
            {
                JButton btn = new JButton("[+]");
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setBackground(BRIGHT_GOLD);
                btn.setForeground(DARK_CHARCOAL);
                btn.setFocusPainted(false);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.inviteButtons.add(btn);
            }
            fireTableDataChanged();
        }
        public boolean isCellEditable(int row,int column)
        {
            if(column==0) return false;
            return true;
        }
    }
    class AvailableMembersListButtonCellRenderer implements TableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
        {
            JButton btn = (JButton)value;
            if(btn != null) {
                JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
                wrapper.setBackground(DEEP_NAVY);
                wrapper.add(btn);
                return wrapper;
            }
            return null;
        }
    }
    class AvailableMembersListButtonCellEditor extends DefaultCellEditor
    {
        private JButton button;
        private ActionListener actionListener;
        private int row,column;
        private boolean isClicked=false;
        public AvailableMembersListButtonCellEditor()
        {
            super(new JCheckBox());
            actionListener=new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    fireEditingStopped();
                }
            };
        }
        public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column)
        {
            this.row=row;
            this.column=column;
            this.button=(JButton)availableMembersListModel.getValueAt(row,column);
            this.button.removeActionListener(actionListener);
            this.button.addActionListener(actionListener);
            isClicked=true;
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
            wrapper.setBackground(DEEP_NAVY);
            wrapper.add(button);
            return wrapper;
        }
        public Object getCellEditorValue()
        {
            return "Invited";
        }
        public boolean stopCellEditing()
        {
            isClicked=false;
            return super.stopCellEditing();
        }
        public void fireEditingStopped()
        {
            super.fireEditingStopped();
        }
    }
}
