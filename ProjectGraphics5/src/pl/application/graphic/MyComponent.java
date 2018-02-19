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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;


public class MyComponent extends JComponent implements ActionListener{
    BufferedImage bufferedImage = null;
    Timer componentTimer;
    int width = 100;
    int height = 100;
    float alpha = 0.2f;
    int x = 0;
    int y = 0;
    int angle = 0; //in degrees
    ArrayList<String> stringsList = new ArrayList<>();
    ArrayList<Point> coordsList = new ArrayList<>();
    @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    @Override
    public void paintComponent(Graphics graphics){
            Graphics2D graphics2DTemp = (Graphics2D) graphics;
            Composite composite = graphics2DTemp.getComposite();
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(angle));
            graphics2DTemp.setTransform(affineTransform);
            graphics2DTemp.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            graphics2DTemp.setColor(Color.WHITE);
            graphics2DTemp.drawImage(bufferedImage, x,y,width,height, null);
            for(int i = 0; i < stringsList.size();i++){
                Point xy = coordsList.get(i);
                String text = stringsList.get(i);
                graphics2DTemp.setFont(new Font(Font.SERIF,Font.ITALIC, 14));
                graphics2DTemp.drawString(text, xy.x,xy.y);    
            }
        }
    public MyComponent() {
        super.setMinimumSize(new Dimension(100, 100));
        super.setSize(width, height);         
        coordsList.add(new Point(0,10));
        coordsList.add(new Point(0,80));
        stringsList.add("first");
        stringsList.add("second");
        try{
            bufferedImage = ImageIO.read(MyComponent.class.getResource("/pl/application/graphic/resources/fon.jpg"));
        }catch(Exception ex){
            ex.printStackTrace();
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        componentTimer = new Timer(20,this);
        componentTimer.start();
    }
    public void addText(String text, int x, int y){
        if(x>100||y>100||x<0||y<0){}else{
            stringsList.add(text);
            coordsList.add(new Point(x,y));
            this.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(alpha<0.9f){
            alpha += 0.05f;
        }else{
            alpha = 0.2f;
        }
        if(x<10){
            x+=1;
            y+=1;
        }else{
            x=0;
            y=0;
        }
        if(angle<20){
            angle+=1;
        }else{
            angle=0;
        }
        repaint();
    }
    
}
