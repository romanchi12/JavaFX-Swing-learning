/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.application.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;


public class NewJFrame extends javax.swing.JFrame {
    BufferedImage bufferedImage = null;
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        this.setTitle("Taras Mikhalchuk");
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 250));

        jButton1.setText("Load");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, java.awt.BorderLayout.LINE_END);

        jLabel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel1ComponentResized(evt);
            }
        });
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        NewJDialog newJDialog = new NewJDialog(this, rootPaneCheckingEnabled);
        int fileChooser = newJDialog.getjFileChooser1().showOpenDialog(this);
        if(fileChooser == JFileChooser.APPROVE_OPTION){
            File file = newJDialog.getjFileChooser1().getSelectedFile();
            try{
                bufferedImage = ImageIO.read(file);
                int width = jLabel1.getWidth();
                int height = jLabel1.getHeight();
                Image imageTemp = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.scale(4,4);
                affineTransform.rotate(Math.toRadians(45));
                graphics2D.drawString("Taras Mikhalchuk", 10, 10);
                graphics2D.setTransform(affineTransform);
                graphics2D.drawString("Taras Mikhalchuk", 10, 10);
                ImageIcon imageIcon = new ImageIcon(imageTemp);
                jLabel1.setIcon(imageIcon);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel1ComponentResized
        // TODO add your handling code here:
        if(bufferedImage != null){
            int width = jLabel1.getWidth();
                int height = jLabel1.getHeight();
                Image imageTemp = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.scale(4,4);
                affineTransform.rotate(Math.toRadians(45));
                graphics2D.drawString("Taras Mikhalchuk", 10, 10);
                graphics2D.setTransform(affineTransform);
                graphics2D.drawString("Taras Mikhalchuk", 10, 10);
                ImageIcon imageIcon = new ImageIcon(imageTemp);
                jLabel1.setIcon(imageIcon);
        }
    }//GEN-LAST:event_jLabel1ComponentResized

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
