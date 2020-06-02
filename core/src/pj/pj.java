package pj;

import sun.nio.cs.HistoricallyNamedCharset;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class pj extends Game {
	SpriteBatch batch;
	ShapeRenderer sr;
	BitmapFont font;
	Texture img;
	Texture playerImg;
	Texture waterImg;
	Texture poleImg;
	Sound jumpSound;
	Sound DeathSound;
	public Json json;
	Preferences pref;
	public FileHandle fileHandle;
	
	
	Array<Pole> poles;
	public HighScores highscores;
	
	Player p;
	public int width = 1280;
	public int height = 720;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		font.setScale(2);
		//playerImg = new Texture("RocketKanga.png");
		playerImg = new Texture("Kangaroo.png");
		waterImg = new Texture("Waves.png");
		poleImg = new Texture("Pole.png");
		highscores = new HighScores();
		
		
		// hey libgdx can open a handler for a file that 
		// doesn't yet exist, nifty
		fileHandle = Gdx.files.local("HighScores.json");
		json = new Json();
			// if there is no file make a new one.
			if(!fileHandle.exists()){
			System.out.println("No such file.  Writing new one.");
				// new Highscore object
				HighScores hs = new HighScores();
				// fill it with some values	
				hs.addScore(new Score("rusd", 48));
				hs.addScore(new Score("Drew", 7));
				hs.addScore(new Score("Darth Pammer", -7));
				// write the file to the disk
				fileHandle.writeString(json.prettyPrint(hs), false);
					
				}
			
			// new string to read as a jason
			String hsString = fileHandle.readString();
			// tell the json class to read the string as a 
			//json telling it what class the json represents	
			highscores = json.fromJson(HighScores.class, hsString);
			// print the file to the output to see what it looks like
			//System.out.println(json.prettyPrint(highscores));
				
		
		
		
		//System.out.println(fileHandle.readString());
		
		
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	
	public void dispose(){
		System.out.println("bye bye");
	
		
		batch.dispose();
		sr.dispose();
		img.dispose();
		//playerImg.dispose();
	//	waterImg.dispose();
	}
}
