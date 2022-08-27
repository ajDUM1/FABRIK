
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class App {
	
	//to store the nodes; static cause why not
	public static Point[] nodes;
	public static Point start;
	public static Point target;
	
	//minimum distance between nodes
	public static int minD = 50;
	
	public static Point p1, p2;//tracker points
	
	public static int nPoints = 3;//number of points

	private JFrame frame;
	private static Panel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}
	
	public void printAll(){
		//this function is purely for debug
		System.out.println("Nodes: ");
		System.out.println(App.nodes);
	}
	
	public void generatePoints(){
		App.nodes = new Point[App.nPoints];
		for(int i=0;i<App.nPoints;i++){
			if(i%2==0){
				App.nodes[i] = new Point(300+(int)(App.minD*1.414213562*i),300);
			}else{
				App.nodes[i] = new Point(300+(int)(App.minD*1.414213562*i),300+(int)(App.minD*1.414213562));
			}
		}
	}
	
	public Point getPoint(Point t, Point s){
		Point p = new Point();
		
		double d = Math.sqrt((t.x-s.x)*(t.x-s.x)+(t.y-s.y)*(t.y-s.y));
		
		double dx = (t.x-s.x)/d;
		double dy = (t.y-s.y)/d;
		
		p.x = (int) (t.x - dx*App.minD);
		p.y = (int) (t.y - dy*App.minD);
		
		return p;
	}
	
	public void FABRIK(){
		Point t = App.target;
		Point s = App.start;
		int len = nodes.length-1;//number of nodes-1
		for(int i=0;i<50;i++){
			//forward step
			nodes[len].x = t.x;
			nodes[len].y = t.y;
			
			for(int j=1;j<=len;j++){
				nodes[len-j] = getPoint(nodes[len-j+1],nodes[len-j]);
			}
			
			//backward step
			nodes[0].x = s.x;
			nodes[0].y = s.y;
			App.panel.repaint();
			for(int j=1;j<=len;j++){
				nodes[j] = getPoint(nodes[j-1],nodes[j]);
			}
		}
		App.panel.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		generatePoints();
		start = new Point(300,300);
		target = new Point(200,200);
		
		frame = new JFrame("Geometic_Fractals");
		frame.setBounds(100, 100, 600, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new Panel();
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(App.p1.distanceSq(App.target.x,App.target.y)<25){
					App.target = arg0.getPoint();
					App.p1 = arg0.getPoint();
					FABRIK();
				}else if(App.p1.distanceSq(App.start.x,App.start.y)<25){
					App.start = arg0.getPoint();
					App.p1 = arg0.getPoint();
					FABRIK();
				}
			}
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				App.p1 = arg0.getPoint();
				FABRIK();
			}
		});
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 582, 565);
		frame.getContentPane().add(panel);
		
		JLabel lblSides = new JLabel("LENGTH");
		lblSides.setHorizontalAlignment(SwingConstants.CENTER);
		lblSides.setBounds(0, 565, 75, 38);
		frame.getContentPane().add(lblSides);
		
		final JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				App.minD = slider.getValue();
				generatePoints();
				FABRIK();
			}
		});
		slider.setValue(50);
		slider.setMaximum(300);
		slider.setMinimum(10);
		slider.setBounds(87, 565, 200, 38);
		frame.getContentPane().add(slider);
		
		JLabel lblPoints = new JLabel("POINTS");
		lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
		lblPoints.setBounds(299, 565, 75, 38);
		frame.getContentPane().add(lblPoints);
		
		final JSlider slider_1 = new JSlider();
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				App.nPoints = slider_1.getValue();
				generatePoints();
				FABRIK();
			}
		});
		slider_1.setMaximum(10);
		slider_1.setMinimum(1);
		slider_1.setValue(3);
		slider_1.setBounds(382, 565, 200, 38);
		frame.getContentPane().add(slider_1);
	}
}
