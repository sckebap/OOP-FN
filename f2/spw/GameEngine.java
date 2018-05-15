package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter {
	GamePanel gp;
	Sound sound = new Sound();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Coin> coins = new ArrayList<Coin>();
	private SpaceShip v;
	private SpaceShip v2;
	private int x;
	private Timer timer;
	private int a = 0;
	private long score = 0;
	private long score2 = 0;
	private double difficulty = 0.1;

	public GameEngine(GamePanel gp, SpaceShip v, SpaceShip v2) {
		this.gp = gp;
		this.v = v;
		this.v2 = v2;

		gp.sprites.add(v);
		gp.sprites.add(v2);

		timer = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);

	}

	public void start() {
		timer.start();
	}

	private void generateEnemy() {
		Enemy e = new Enemy((int) (Math.random() * 390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateCoin() {
		Coin c = new Coin((int) (Math.random() * 390), 30);
		gp.sprites.add(c);
		coins.add(c);
	}

	private void process() {
		if (Math.random() < difficulty) {
			generateEnemy();
		}

		if (((getScore() % 1000 == 0 && getScore() != 0) || (getScore2() % 1000 == 0 && getScore2() != 0) )&& a == 0) {
			generateCoin();
			a++;
		}

		Iterator<Enemy> e_iter = enemies.iterator();
		Iterator<Coin> c_iter = coins.iterator();
		try {
			while (e_iter.hasNext()) {
				Enemy e = e_iter.next();

				e.proceed();

				if (!e.isAlive()) {
					e_iter.remove();
					gp.sprites.remove(e);
					if (v.isAlive) {
						score += 100;
					}
					if (v2.isAlive) {
						score2 += 100;
					}

				}

				while (c_iter.hasNext()) {

					Coin c = c_iter.next();
					c.proceedSpeed();

					if (!c.isAlive()) {
						c_iter.remove();
						gp.sprites.remove(c);
						score += 0;
						score2 += 0;
						a = 0;
					}
				}
			}
			if (getScore() > 5000 || getScore2() > 5000 ) {
				difficulty += 0.1;
			}
		} catch (Exception e) {

		}

		gp.updateGameUI(this);

		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double cr;

		for (Enemy e : enemies) {
			er = e.getRectangle();
			if (er.intersects(vr)) {
				gp.sprites.remove(v);
				v.die();
				if (v2.isAlive == false && v.isAlive == false){
					die();
					if(getScore() > getScore2())
						JOptionPane.showMessageDialog(null, "PLAYER 2 WIN :" + getScore2());
					else
						JOptionPane.showMessageDialog(null, "PLAYER 2 WIN :" + getScore2());				}
				return;
			}
			if (er.intersects(vr2)) {
				v2.die();
				gp.sprites.remove(v2);
				if (v2.isAlive == false && v.isAlive == false){
					die();
					if(getScore() > getScore2())
						JOptionPane.showMessageDialog(null, "PLAYER 2 WIN :" + getScore2());
					else
						JOptionPane.showMessageDialog(null, "PLAYER 2 WIN :" + getScore2());
				}
				return;
			}
		}

		for (Coin c : coins) {
			cr = c.getRectangle();
			if (cr.intersects(vr)) {
				sound.playSound("f2/spw/1.wav");
				score += 300;
				return;
			}

			if (cr.intersects(vr2)) {
				sound.playSound("f2/spw/1.wav");
				score2 += 300;
				return;
			}
		}
	}

	public void die() {
		timer.stop();
	}

	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-3);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(3);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_X:
			v2.move(3);
			break;
		case KeyEvent.VK_Z:
			v2.move(-3);
			break;
		}
	}

	public long getScore() {
		return score;
	}

	public long getScore2() {
		return score2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	
}
