package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	boolean isAlive = true;
	Color color;
	public SpaceShip(int x, int y, int width, int height,Color color) {
		super(x, y, width, height);
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.fillRect(x, y, width, height);
		g.setColor(color);
	}

	public void die(){
		isAlive = false;
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}

}
