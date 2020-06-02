package pj;

import java.util.Iterator;

import javax.swing.GrayFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen, InputProcessor {
	final pj game;
	OrthographicCamera cam;
	
	Viewport viewport;
	public float gravity = -10;
	public float jumpSpeed = 200;
	public float thrustSpeed = 15;
	public boolean screenTouched;
	public float camSpeed = 150;
	public float interval;
	public float lastPole;
	public boolean start;
	// switch to true to restart the game.
	public boolean lost;
	public float poleWidth;
	public int score = 0;
	public int width = 1280;
	public int height = 720;
	public int poleCounter = 3;
	
	public boolean poles = true;
	public boolean clouds = false;
	public boolean platforms = false;
	public boolean movingPoles = false;
	public boolean stagtights = false;
	public boolean highScoresVisable = false;
	
	
	public GameScreen(pj game){
		cam = new OrthographicCamera();
		cam.setToOrtho(false,game.width,game.height);
		//cam.setToOrtho(false,Gdx.graphics.getWidth(),height);
		//viewport = new FitViewport(width, height, cam);
		
		interval = 640;
		lastPole = 0;
		screenTouched = false;
		start = false;

		this.game = game;
		poleWidth = 100;
		//make a player
		game.p = new Player(0, height-65, 64	, 64);
		game.poles = new Array<Pole>();
		
		
		Gdx.input.setInputProcessor(this);
		
		addPole(-250,500,500);
		addPole(interval,poleWidth , height/2);
		addPole(interval + interval ,poleWidth , height/2);
		//addPole(500,2,500);
		
		Table highscores;
		highscores = new Table();
		
	}
	
	public void addPole(float x, float width, float height){
		Pole p = new Pole(x,0,width,height);
		game.poles.add(p);
		System.out.println("pole Added");
	}
	
	public void addPole(){
		Pole p = new Pole(10, 0, 50, 200);
		game.poles.add(p);
		
	}
	
	public void poleAdder(){
		if(game.p.x - lastPole > interval){
			addPole(lastPole + interval + interval+ interval,poleWidth ,MathUtils.random(128,height/3 * 2));
			lastPole = game.p.x;
			poleWidth --;
			camSpeed +=10;
			jumpSpeed +=10;
			thrustSpeed +=1;
			gravity -=1;
			poleCounter ++;
			if (poleWidth ==0){
				lost = true;
			}
			if(game.poles.size > 10){
				game.poles.removeIndex(0);
				System.out.println("removed 1st pole");
			}
		}
	}
	
	public void dologic(){
		cam.position.x = game.p.x + width/3;
		
		
		if(screenTouched){
			if(game.p.onGround){
				game.p.speed = jumpSpeed;
				game.p.onGround = false;
			}
			
			if(!game.p.onGround && game.p.gas > 0){
				game.p.speed += thrustSpeed;
				game.p.gas--;
			}
		}
		
		// gravity
		game.p.speed += gravity;
		game.p.previousY = game.p.y;
		game.p.y += game.p.speed * Gdx.graphics.getDeltaTime();
		// dont let the player go out the top of the screen
		if(game.p.y + game.p.height > height){
			game.p.y = height - game.p.height;
			game.p.speed = -jumpSpeed;
			score -=2;
		}
		
		// check collision
		Iterator<Pole> itor = game.poles.iterator();
		while(itor.hasNext()){
			Pole p = itor.next();
			// if the player is over the pole
			if(game.p.x  + game.p.width > p.x &&game.p.x < p.x + p.width){
				// if the player is above but not below.
				
				if(game.p.previousY >= p.height - 10 && game.p.y <= p.height){
					game.p.speed =0;
					//add to the score;
					if(!game.p.onGround ){
						score++;
					}
						
					
					game.p.onGround = true;
					game.p.y = p.height ;
					// refill gas tank on pole jump
					game.p.gas = game.p.gasFull;
					
				}
				if(game.p.y < p.height-10){
					lost = true;
					start = false;
					//game.setScreen(new GameScreen(game));
				}
				
			}
		}
		
		
		
		//don't let the player fall out of the bottom of the screen.
		if (game.p.y < 0){
			game.p.y = 0;
			game.p.speed = 0;
			game.p.onGround = true;
			lost = true;
			start = false;
		}
		
		
		if(start){
			game.p.x += camSpeed* Gdx.graphics.getDeltaTime();		
			
		}
		
		poleAdder();
		
	}
	
	public void getInput(){

		if(Gdx.input.isKeyPressed(Keys.A)){
			game.p.x -= camSpeed * Gdx.graphics.getDeltaTime();
		
		}
		if(Gdx.input.isKeyPressed(Keys.E)){
			game.p.x += camSpeed * Gdx.graphics.getDeltaTime();
			
		}
	}
	

	public void renderLose(){

		game.font.draw(game.batch, "Game Over", cam.position.x- "Game Over".length()/2*12, height/2 );
		game.font.draw(game.batch, "Final Score: " + score, cam.position.x - "Final Score:   ".length()/2 *12, height/2-20 );
		System.out.println("You lost");
		
		
	}
	
	@Override
	public void render(float delta) {
		
	
		getInput();
		dologic();
		
		cam.update();
		
		Gdx.gl.glClearColor(0, .5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		game.batch.draw(game.playerImg, game.p.x, game.p.y,game.p.width,game.p.height);
		//game.batch.draw(game.img, 0, 0);
		//game.font.draw(game.batch, "On Ground: " + game.p.onGround, cam.position.x, height);
		//game.font.draw(game.batch, "Speed " + game.p.speed, cam.position.x, height -20);
		//game.font.draw(game.batch, "Player x " + game.p.x, cam.position.x, height -40);
		//game.font.draw(game.batch, "Gas " + game.p.gas, cam.position.x, height -60);
		Iterator<Pole> itorPole = game.poles.iterator();
		
		while(itorPole.hasNext()){
			Pole p =  itorPole.next();
			game.batch.draw(game.poleImg, p.x, p.y, p.width, p.height);
			
		}

		int temp;
		 temp = (int) (game.p.x / game.waterImg.getWidth());
		// System.out.println(temp);
		for(int i =0; i < 8; i ++){
			game.batch.draw(game.waterImg,(i -1+ temp)*game.waterImg.getWidth(),0);
		}

		
		game.font.draw(game.batch, "Score " + score, cam.position.x, height );
		if(lost){
			renderLose();
		}
		
		game.batch.end();
		
		
		game.sr.setProjectionMatrix(cam.combined);
		game.sr.begin(ShapeType.Filled);
		game.sr.setColor(Color.GREEN);
		// Draws a bar that lowers based on gas remaining.
		game.sr.rect(cam.position.x + width/2-64,
				height/2, 64, height/2* game.p.gas/game.p.gasFull);
		//game.sr.rect(game.p.x	, game.p.y, game.p.width, game.p.height);
		
//		Iterator<Pole> itor = game.poles.iterator();
//		
//		while(itor.hasNext()){
//			Pole p =  itor.next();
//			game.sr.rect(p.x,p.y,p.width,p.height);
//			
//		}
		game.sr.end();
		
		
		
	}
	

	@Override
	public void resize(int width, int height) {
		
		//viewport.update(width,height);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	//	System.out.println("down");
		screenTouched = true;
		if(lost){
			game.highscores.addScore(new Score("rusd", score));
			game.fileHandle.writeString(game.json.prettyPrint(game.highscores), false);
			game.highscores.sortScores(0);
			game.highscores.trimScores();
			game.setScreen(new MenuScreen(game));
		}
		
		if(!start){
			start = true;
		}
	//	game.p.speed = 5;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	//	System.out.println("up");
		screenTouched = false;
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
