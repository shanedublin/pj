package pj;

import com.badlogic.gdx.Gdx;

public class Player extends Entity{
	
 public float speed;
 public boolean touching;
 public boolean onGround;
 public float gas;
 public float gasFull;
 public float previousY;
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		speed = 0;
		// used for cheap raycasting
		previousY = Gdx.graphics.getHeight();
		touching = true;
		onGround = true;
		gas = 250;
		gasFull = 250;
	
	}
	
	
	

}
