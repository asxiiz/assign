package f2.spw;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	int gameOver;

	Font font_Score = new Font("Serif", Font.PLAIN, 20);
	Font font_heart = new Font("Serif", Font.PLAIN, 10);
	Font font_Level = new Font("Serif", Font.PLAIN, 40);
	Font font_FinishGame = new Font("Serif", Font.PLAIN, 100);

	public GamePanel() {
		gameOver = 0;
		bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.WHITE);
	}
	public void setWinner(int x){
		gameOver = x;
	}

	public void updateGameUI(GameReporter reporter){

		big.clearRect(0, 0, 800, 600);

		big.setColor(Color.BLACK);
		big.setFont(font_Score);
		big.drawString(String.format("Player 1 : %08d", reporter.getScoreP1()), 80, 20);
		big.drawString(String.format("Player 2 : %08d", reporter.getScoreP2()), 80+400, 20);
		big.setFont(font_Level);
		big.drawString(String.format("Level %d",reporter.getLevel() +1),350,40);
		big.setFont(font_heart);
		big.drawString(String.format("Heart 1 : %d", reporter.getHeart1()), 110, 60);
		big.drawString(String.format("Heart 2 : %d", reporter.getHeart1()), 110+400, 60);

		if(gameOver == 1){
			big.setFont(font_FinishGame);
			big.drawString(String.format("Player 1 Win!"), 110, 300);
		}else if(gameOver == 2){
			big.setFont(font_FinishGame);
			big.drawString(String.format("Player 2 Win!"), 110, 300);
		}else if(gameOver == 3){
			big.setFont(font_FinishGame);
			big.drawString(String.format("Draw!"), 110, 300);
		}
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
