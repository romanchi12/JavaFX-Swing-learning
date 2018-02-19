/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.application.graphic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;


public class MyComponent extends JComponent implements ActionListener, MouseListener {
    BufferedImage bufferedImage = null;
    Timer timer;
    int width = 250;
    int height = 150;
    float alpha = 0.95f;
    double arc = 0;
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    public void paintComponent(Graphics graph){
        try {
            Graphics2D g2d = (Graphics2D) graph;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g2d.drawImage(bufferedImage, 0,0,width, height, null);
            g2d.setFont(new Font("serif", Font.PLAIN, 14));
            g2d.drawString("advertising block", 10, 15);
            g2d.setColor(Color.RED);
            g2d.drawRect(0, 0, 120, 20);
            g2d.setFont(new Font(Font.SERIF, Font.BOLD, 24));
            g2d.drawString("REKLAMA", width/2 - 55, height/2 - 5);
            BufferedImage saleImage = ImageIO.read(NewJFrame.class.getResource("/pl/application/graphic/resources/Sale.png"));
            Image image = saleImage.getScaledInstance(60, 60,Image.SCALE_SMOOTH);
            //g2d.drawImage(image, 10, height - 60 - 10, null);
            g2d.drawString("Discount 45%", width/2 - 55, height/2 - 25);
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(arc));
            affineTransform.translate(10, height - 60 - 10);
            g2d.drawImage(image, affineTransform ,null);
            
        } catch (IOException ex) {
            Logger.getLogger(MyComponent.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    private void paintQuestion(Graphics graph){
        Graphics2D g2d = (Graphics2D) graph;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));
        g2d.drawImage(bufferedImage, 0,0,width, height, null);
        g2d.setFont(new Font("serif", Font.PLAIN, 14));
        g2d.drawString("advertising block", 10, 15);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, 120, 20);
        g2d.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        g2d.drawString("kupić?", width/2 - 55, height/2 - 5);
    }
    public MyComponent(){
        addMouseListener(this);
        try {
            bufferedImage = ImageIO.read(NewJFrame.class.getResource("/pl/application/graphic/resources/fon.jpg"));
            timer = new Timer(20,this);
            timer.start();
            InputStream inputStream = new BufferedInputStream(NewJFrame.class.getResourceAsStream("/pl/application/graphic/resources/shopen.wav"));    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
            audioClip.loop(10);
            audioClip.start();       
        } catch (IOException ex) {
            ex.printStackTrace();
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(MyComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(MyComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        action();
        repaint();
    }
    public void action(){
        if(alpha<0.95){
            alpha += 0.03;
        }else{
            alpha = 0.2f;
        }
        if(arc < 5){
            arc += 0.1;
        }else{
            arc = -5;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {     
    }
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
     
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        timer.stop();
        paintQuestion(this.getGraphics());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        timer.restart();    
    }
        
}
