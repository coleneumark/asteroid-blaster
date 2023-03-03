//source: https://www.educative.io/answers/what-is-drawpolygon-in-java

import java.awt.*;
import javax.swing.*;

public class createPolygon extends JPanel{
	 public void paintComponent(Graphics g) {
		 	//Player Space Ship
		 	int [] xp = {10, 15, 20, 20, 30, 30, 35, 40, 40, 30, 25, 20, 10};
	        int [] yp = {15, 25, 25, 20, 20, 25, 25, 15, 30, 30, 45, 30, 30};
	        double scale = 3;
	        for (int i =0;i<xp.length;i++) {
	        	xp[i] = (int) (xp[i] * scale);
	        	yp[i] = (int) (yp[i] * scale);
	        }
	        Polygon playerPoly = new Polygon(xp,yp,xp.length);
	        g.drawPolygon(playerPoly);
	        
	      //Enemy Space Ship
	        int [] xe = {20, 40, 50, 60, 70, 80, 100, 80, 70, 60, 50, 40};
	        int [] ye = {60, 40, 10, 40, 10, 40, 60, 80, 110, 80, 110, 80};
	        scale = 0.5;
	        for (int i =0;i<xe.length;i++) {
	        	xe[i] = (int) (xe[i] * scale);
	        	ye[i] = (int) (ye[i] * scale);
	        }
	        Polygon enemyPoly = new Polygon(xe,ye,xe.length);
	        g.drawPolygon(enemyPoly);
	        
	        //Large Asteroid
	        
	        //Medium Asteroid
	        
	        //Small Asteroid

     //Bullet
	        int [] xb = {1, 1, 4, 4};
	        int [] yb = {4, 2, 2, 4};
	        scale = 3;
	        for (int i =0;i<xb.length;i++) {
	        	xb[i] = (int) (xb[i] * scale);
	        	yb[i] = (int) (yb[i] * scale);
	        }
	        Polygon bulletPoly = new Polygon(xe,yb,xb.length);
	        g.drawPolygon(bulletPoly);  
	      }
	    	
	 public static void main( String args[] ) {
	        //JFrame.setDefaultLookAndFeelDecorated(true);
	        JFrame frame = new JFrame("Draw Space Ship");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //frame.setBackground(Color.white);
	        frame.setSize(300, 300);

	        createPolygon panel = new createPolygon();
	        frame.add(panel);
	        frame.setVisible(true);
	    }
}