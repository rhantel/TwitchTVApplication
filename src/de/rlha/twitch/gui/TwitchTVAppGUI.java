package de.rlha.twitch.gui;

import de.rlha.twitch.TwitchTVStream;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimeTypeParseException;

/**
 *
 * @author Roland
 */
public class TwitchTVAppGUI extends javax.swing.JFrame {

    private boolean isLiveThreadRunning = false;
    
    /**
     * Creates new form TwitchTVAppGUI
     */
    public TwitchTVAppGUI() {
        initComponents();
        lblUrlValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblChannelName = new javax.swing.JLabel();
        tfChannelName = new javax.swing.JTextField();
        btnCheckLive = new javax.swing.JButton();
        lblStreamStatus = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblGame = new javax.swing.JLabel();
        lblViewers = new javax.swing.JLabel();
        lblUrl = new javax.swing.JLabel();
        lblTitleValue = new javax.swing.JLabel();
        lblGameValue = new javax.swing.JLabel();
        lblViewersValue = new javax.swing.JLabel();
        lblUrlValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblChannelName.setText("Channel name:");

        btnCheckLive.setText("Check");
        btnCheckLive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckLiveActionPerformed(evt);
            }
        });

        lblStreamStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        lblTitle.setText("Title:");

        lblGame.setText("Game:");

        lblViewers.setText("Viewers:");

        lblUrl.setText("URL:");

        lblUrlValue.setForeground(new java.awt.Color(0, 51, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStreamStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblChannelName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfChannelName, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCheckLive))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblGame)
                                    .addComponent(lblTitle))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitleValue)
                                    .addComponent(lblGameValue)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblViewers)
                                    .addComponent(lblUrl))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUrlValue)
                                    .addComponent(lblViewersValue))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChannelName)
                    .addComponent(tfChannelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckLive))
                .addGap(18, 18, 18)
                .addComponent(lblStreamStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(lblTitleValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGame)
                    .addComponent(lblGameValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblViewers)
                    .addComponent(lblViewersValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUrl)
                    .addComponent(lblUrlValue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckLiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckLiveActionPerformed
        if (this.isLiveThreadRunning == true)
            return;
        
        Thread thread = new Thread(() -> {
            // THREAD BEGIN
            
            this.isLiveThreadRunning = true;   
            
            this.clearLabels();
            
            String username = this.tfChannelName.getText();

            if (username == null || username.isEmpty()) {
                this.printErrorToStreamStatusLbl();
                this.isLiveThreadRunning = false;
                return;
            }

            this.lblStreamStatus.setForeground(Color.BLACK);
            this.lblStreamStatus.setText("Checking ...");

            TwitchTVStream stream = new TwitchTVStream(username.trim());            
            
            try {            
                if (stream.isLive()) {
                    this.lblStreamStatus.setForeground(new Color(7, 171, 78));
                    this.lblStreamStatus.setText(username + " is LIVE!");
                    
                    this.lblTitleValue.setText(stream.getTitle());
                    this.lblGameValue.setText(stream.getGame());
                    this.lblViewersValue.setText(stream.getViewers());
                    String url = stream.getUrl();
                    this.lblUrlValue.setText("<HTML><U>" + url + "<U><HTML>");
                    for (MouseListener listener : this.lblUrlValue.getMouseListeners()) {
                        this.lblUrlValue.removeMouseListener(listener);
                    }
                    
                    this.lblUrlValue.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            if (Desktop.isDesktopSupported() == true) {
                                try {
                                    Desktop.getDesktop().browse(new URI(url));
                                } catch (IOException | URISyntaxException ex) {
                                    Logger.getLogger(TwitchTVAppGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }

                        @Override
                        public void mousePressed(MouseEvent me) {
                            
                        }

                        @Override
                        public void mouseReleased(MouseEvent me) {
                            
                        }

                        @Override
                        public void mouseEntered(MouseEvent me) {
                            
                        }

                        @Override
                        public void mouseExited(MouseEvent me) {
                            
                        }
                    });
                }
                else {
                    this.clearLabels();
                    this.lblStreamStatus.setForeground(Color.RED);
                    this.lblStreamStatus.setText(username + " is OFFLINE!");
                }
            }
            catch (IOException | MimeTypeParseException ex) {
                this.printErrorToStreamStatusLbl();
            }
            finally {
                this.isLiveThreadRunning = false;
            }
            
            // THREAD END
        });
        
        thread.start();
    }//GEN-LAST:event_btnCheckLiveActionPerformed

    private void printErrorToStreamStatusLbl() {
        this.lblStreamStatus.setForeground(Color.BLACK);
        this.lblStreamStatus.setText("Something went wrong...");        
    }
    
    private void clearLabels() {
        this.lblTitleValue.setText("");
        this.lblGameValue.setText("");        
        this.lblViewersValue.setText("");
        this.lblUrlValue.setText("");
        for (MouseListener listener : this.lblUrlValue.getMouseListeners()) {
            this.lblUrlValue.removeMouseListener(listener);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());                    
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TwitchTVAppGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TwitchTVAppGUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckLive;
    private javax.swing.JLabel lblChannelName;
    private javax.swing.JLabel lblGame;
    private javax.swing.JLabel lblGameValue;
    private javax.swing.JLabel lblStreamStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitleValue;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JLabel lblUrlValue;
    private javax.swing.JLabel lblViewers;
    private javax.swing.JLabel lblViewersValue;
    private javax.swing.JTextField tfChannelName;
    // End of variables declaration//GEN-END:variables
}
