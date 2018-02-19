/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.application.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class MyComponent extends JComponent{
    BufferedImage bufferedImage = null;
    int width = 100;
    int height = 100;
    ArrayList<String> stringsList = new ArrayList<>();
    ArrayList<Point> coordsList = new ArrayList<>();
    @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    @Override
    public void paintComponent(Graphics graphics){
            System.out.println("Paint component");
            Graphics2D graphics2DTemp = (Graphics2D) graphics;
            graphics2DTemp.setColor(Color.WHITE);
            graphics2DTemp.drawImage(bufferedImage, 0,0,width,height, null);
            for(int i = 0; i < stringsList.size();i++){
                Point xy = coordsList.get(i);
                String text = stringsList.get(i);
                System.out.println(text);
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
    }
    public void addText(String text, int x, int y){
        if(x>100||y>100||x<0||y<0){}else{
            stringsList.add(text);
            coordsList.add(new Point(x,y));
            this.repaint();
        }
    }
    
}
