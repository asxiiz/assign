package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v1 = new SpaceShip(180, 550, 20, 20, 1);
		SpaceShip v2 = new SpaceShip(180+400, 550, 20, 20, 2);
		//Print Graphic2D
		GamePanel gp = new GamePanel();
		//add action for Object
		GameEngine engine = new GameEngine(gp,v1,v2);
		frame.addKeyListener(engine);
		//Output
		frame.getContentPane().add(gp,BorderLayout.CENTER);
		frame.setVisible(true);
		
		//timer in engine start 
		engine.start();
	}
}
