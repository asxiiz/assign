package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	int p;
	
	public SpaceShip(int x, int y, int width, int height,int p) {
		super(x, y, width, height);
		this.p = p;
	}

	//change color player1 and player2
	@Override
	public void draw(Graphics2D g) {
		if(p == 1)
			g.setColor(Color.MAGENTA);
		else if(p == 2)
			g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		
	}

	//chage location that object can move
	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 800 - width)
			x = 800 - width;
	}
	//Add this Fn. to set move if object die
	public void setMove(int x){
		step = x;
	}

}
