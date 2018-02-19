/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.application.graphic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import javafx.scene.input.KeyCode;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.Timer;


public class GameComponent extends JComponent implements ActionListener, KeyListener, MouseListener{
    BufferedImage background = null;
    BufferedImage ghost = null;
    BufferedImage pacman = null;
    Timer timer;
    int width = 200;
    int height = 220;
    private final int STATE_BEFORE = 0;
    private final int STATE_GAME = 1;
    private final int STATE_DIED = 2;
    private final int STATE_PAUSED = 3;
    private int currentState = 0;
    private int ghost_y = height - 20;
    private int ghost_x = 50;
    private int pacman_x = 100;
    private int pacman_y = 100;
    Clip game = null;
    Clip intro = null;
    Clip died = null;
    public GameComponent(){
        addKeyListener(this);
        try{
            this.setFocusable(true);
            this.requestFocusInWindow();
            background = ImageIO.read(GameComponent.class.getResource("/pl/application/graphic/resources/background.png"));
            ghost = ImageIO.read(GameComponent.class.getResource("/pl/application/graphic/resources/ghost.png"));//.getScaledInstance(20,20 , Image.SCALE_SMOOTH);
            pacman =  ImageIO.read(GameComponent.class.getResource("/pl/application/graphic/resources/pacman.png"));//.getScaledInstance(20,20 , Image.SCALE_SMOOTH);
            timer = new Timer(100, this);
            InputStream inputStream = new BufferedInputStream(NewJFrame.class.getResourceAsStream("/pl/application/graphic/resources/pacman_chomp.wav"));    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            game = AudioSystem.getClip();
            game.open(audioInputStream);
            game.loop(100);
            InputStream inputStream2 = new BufferedInputStream(NewJFrame.class.getResourceAsStream("/pl/application/graphic/resources/pacman_beginning.wav"));
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(inputStream2);
            intro = AudioSystem.getClip();
            intro.open(audioInputStream2);
            intro.loop(100);
            
            InputStream inputStream3 = new BufferedInputStream(NewJFrame.class.getResourceAsStream("/pl/application/graphic/resources/pacman_death.wav"));
            AudioInputStream audioInputStream3 = AudioSystem.getAudioInputStream(inputStream3);
            died = AudioSystem.getClip();
            died.open(audioInputStream3);
            
            intro.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ));
        g2d.drawImage(background, 0, 0,width,height, null);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2d.drawString("Press esc to pause", 5, 14);
        g2d.drawLine(0, 20, 200, 20);
        g2d.drawImage(pacman, pacman_x, pacman_y, 20, 20, null);
        g2d.drawImage(ghost, ghost_x, ghost_y, 20, 20, null);
        switch(currentState){
            case STATE_BEFORE:
                paintQuestionToBegin(graphics);
                break;
            case STATE_GAME:
                break;
            case STATE_DIED:
                paintDiedMessage(graphics);
                break;
            case STATE_PAUSED:
                paintPausedMessage(graphics);
                break;
        }
    }
    private void paintQuestionToBegin(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        Font currentFont = g2d.getFont();
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g2d.drawString("Are you ready?", 20, 70);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        g2d.drawString("Press Enter to begin", 25, 100);
        g2d.drawString("(or click)", 40, 120);
        g2d.setFont(currentFont);
    }
    private void paintDiedMessage(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        Font currentFont = g2d.getFont();
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g2d.drawString("Died, press enter", 20, 70);
        g2d.drawString("(or click)", 40, 120);
        g2d.setFont(currentFont);
    }
    private void paintPausedMessage(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        Font currentFont = g2d.getFont();
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g2d.drawString("Paused, press enter", 20, 70);
        g2d.drawString("(or click)", 40, 120);
        g2d.setFont(currentFont);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        action();
        repaint();
    }
    public void action(){
        //right 0 
        //left 1
        //top 2
        //down 3
        double[] directions = new double[4];
        directions[0] = Math.sqrt(Math.pow(ghost_x+2 - pacman_x,2)+Math.pow(ghost_y - pacman_y,2));
        directions[1] = Math.sqrt(Math.pow(ghost_x-2 - pacman_x, 2)+Math.pow(ghost_y - pacman_y, 2)); 
        directions[2] = Math.sqrt(Math.pow(ghost_x - pacman_x, 2)+Math.pow(ghost_y-2 - pacman_y,2));
        directions[3] = Math.sqrt(Math.pow(ghost_x - pacman_x,2)+Math.pow(ghost_y+2 - pacman_y, 2));
        double min_value = directions[0];
        int min_value_index = 0;
        for(int i = 0; i < directions.length; i++){
            if(min_value > directions[i]){
                min_value = directions[i];
                min_value_index = i;
            }
        }
        switch(min_value_index){
            case 0:
                ghost_x+=2;
                break;
            case 1:
                ghost_x-=2;
                break;
            case 2:
                ghost_y-=2;
                break;
            case 3:
                ghost_y+=2;
        }
        if(rectangleCollision(pacman_x,pacman_y,15,15,ghost_x,ghost_y,15,15)){
            currentState = STATE_DIED;
            died.start();
            pauseGame();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) { 
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(currentState==STATE_GAME){
                if(pacman_x>=0){
                    pacman_x-=4;
                }
            }
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(currentState==STATE_GAME){
                if(pacman_x+20<=width){
                    pacman_x+=4;
                }
            }
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
            if(currentState==STATE_GAME){
                if(!(pacman_y<=20)){
                    pacman_y-=4;
                }
            }
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            if(currentState==STATE_GAME){
                if(pacman_y+20 <= height){
                    pacman_y+=4;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            if(currentState==STATE_GAME){
                currentState = STATE_PAUSED;
                pauseGame();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(currentState==STATE_DIED){
                pacman_x=100;
                pacman_y=100;
                ghost_x = 50;
                ghost_y = height - 20;
                currentState=STATE_BEFORE;  
            }
            if(currentState==STATE_BEFORE){
                currentState=STATE_GAME;
                resumeGame();
            }
            if(currentState==STATE_PAUSED){
                currentState=STATE_GAME;
                resumeGame();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void pauseGame() {
        if(timer.isRunning()){
            timer.stop();
            repaint();
            if(game.isActive()){
                game.stop();
            }
            intro.loop(100);
            intro.start();
        }
    }
    private void resumeGame(){
        timer.restart();
        if(intro.isActive()){
            intro.stop();
        }
        game.loop(100);
        game.start();
    }
    boolean rectangleCollision(float x1, float y1, float width_1, float height_1, float x2, float y2, float width_2, float height_2){
      return !(x1 > x2+width_2 || x1+width_1 < x2 || y1 > y2+height_2 || y1+height_1 < y2);
    }
    
    
}
