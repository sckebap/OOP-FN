package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	Graphics2D big2;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big2 = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 600);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("PLAYER 1 :" +"%08d", reporter.getScore()), 250, 20);
		big.drawString(String.format("PLAYER 2 :" + "%08d", reporter.getScore2()), 250, 35);
		
		big2.setColor(Color.GREEN);		

		for(Sprite s : sprites){
			s.draw(big);
			//s.draw(big2); 
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);

	}

}