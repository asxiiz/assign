package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Health> heart = new ArrayList<Health>();	
	private SpaceShip player1;	
	private SpaceShip player2;

	private Timer timerEnemy,timerScore,timerHeart;
	
	private long score1 = 0,score2 = 0;
	private int level=0;
	private int defultScore1 = 100;
	private int defultScore2 = 100;
	
	private double difficulty = 0.1;
	private int heart1 = 1;
	private int heart2 = 1;

	
	public GameEngine(GamePanel gp, SpaceShip player1, SpaceShip player2) {
		this.gp = gp;

		this.player1 = player1;
		this.player2 = player2;			
		
		gp.sprites.add(player1);
		gp.sprites.add(player2);
		timerScore = new Timer(10000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				difficulty += 0.2;
				level += 1;

			}
		});
		
		timerEnemy = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();

			}
		});
	
		timerHeart = new Timer(20000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();

			}
		});
		timerEnemy.setRepeats(true);
		timerScore.setRepeats(true);
		timerHeart.setRepeats(true);	
	}
	
	public void start(){
		timerEnemy.start();
		timerScore.start();
		timerHeart.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*790), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateHeart(){
		Health h = new Health((int)(Math.random()*790), 30);
		gp.sprites.add(h);
		heart.add(h);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
			
		}
		
		else if(Math.random() > difficulty){
			generateHeart();
		}

		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Health> h_iter = heart.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
							
				score1 += defultScore1;
				score2 += defultScore2;
			}
		}

		while(h_iter.hasNext()){
			Health h = h_iter.next();
			h.proceed();
			if(!h.isAlive()){
				h_iter.remove();
				gp.sprites.remove(h);
			}
		}
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr1 = player1.getRectangle();
		Rectangle2D.Double vr2 = player2.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double hr;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr1)){
				if(heart1 > 0)
					heart1 -= 1;
				else{
					player1.setMove(0);
					defultScore1 = 0;
					heart1 = 0;
				}
				return;
			}
			else if(er.intersects(vr2)){
				if(heart2 > 0)
					heart2 -= 1;
				else{
					player2.setMove(0);
					defultScore2 = 0;
					heart2 = 0;
				}

				return;
			}

			if (heart1 == 0  &&  heart2 == 0) {
				if(score1 > score2)
					gp.setWinner(1);
				else if(score2 > score1)
					gp.setWinner(2);
				else
					gp.setWinner(3);

				gp.updateGameUI(this);
				die();				
			}
		}

		for(Health h : heart){
			hr = h.getRectangle();
			if(hr.intersects(vr1)){
				heart1 += 1;
			}
			else if(hr.intersects(vr2)){
				heart2 += 1;
			}
		}
	}
	
	public void die(){
		timerEnemy.stop();
		timerScore.stop();
		timerHeart.stop();
	}
	
	void controlVehicleForP1(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player1.move(-1);
				break;
			case KeyEvent.VK_D:
				player1.move(1);
				break;
			case KeyEvent.VK_P:
				//difficulty += 0.1;
				break;
		}
	}

	void controlVehicleForP2(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player2.move(-1);
				break;
			case KeyEvent.VK_RIGHT:
				player2.move(1);
				break;
			case KeyEvent.VK_P:
				break;
		}
	}

	public long getScoreP1(){
		return score1;
	}
	
	public long getScoreP2(){
		return score2;
	}

	public int getLevel(){
		return level;
	}

	public int getHeart1(){
		return heart1;
	}

	public int getHeart2(){
		return heart2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicleForP1(e);
		controlVehicleForP2(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		controlVehicleForP1(e);
		controlVehicleForP2(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
