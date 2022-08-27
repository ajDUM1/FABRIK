import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class Panel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
		
		Point prev = App.start;
		for(Point p:App.nodes){
			g2d.drawLine(prev.x, prev.y, p.x, p.y);
			
			prev = p;
		}
		//to draw lines and nodes
		for(Point p:App.nodes){
			g2d.fillOval(p.x-3, p.y-3, 6, 6);
		}
		
		g2d.setColor(Color.GREEN);//for outline		
		g2d.fillOval(App.start.x-5, App.start.y-5, 10, 10);//draw start outline
		g2d.setColor(Color.RED);
		g2d.fillOval(App.target.x-5, App.target.y-5, 10, 10);//draw target outline
		
		g2d.setColor(Color.BLACK);//for outline		
		g2d.drawOval(App.start.x-5, App.start.y-5, 10, 10);//draw start outline
		g2d.drawOval(App.target.x-5, App.target.y-5, 10, 10);//draw target outline		
		
	}

}
