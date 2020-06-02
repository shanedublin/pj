package pj;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;

public class MenuScreen implements  Screen, InputProcessor {
	
	final pj game;
	OrthographicCamera cam;
	Stage stage;
	Skin skin;
	ButtonStyle buttonStyle;
	TextButtonStyle textButtonStyle;
	
	final Table HighScoresTable;
	
	
	public MenuScreen(final pj game){
		this.game = game;
		//Gdx.input.setInputProcessor(this);
		this.cam = new OrthographicCamera();
		cam.setToOrtho(false,game.width,game.height);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		Pixmap pixmap2 = new Pixmap(1,1,Format.RGBA8888);
		pixmap2.setColor(new Color(0, 0, 0, 1));
		pixmap2.fill();
		
		
		skin.add("gray", new Texture(pixmap2));
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		
		buttonStyle = new ButtonStyle();
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white",Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.font = skin.getFont("default");

		TextFieldStyle textFieldStyle = new TextFieldStyle();
		//textFieldStyle.background = skin.newDrawable("white",Color.DARK_GRAY);
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.fontColor = Color.BLUE;

		
		WindowStyle ws = new WindowStyle();
		ws.titleFont = (skin.getFont("default"));
		ws.titleFontColor = textFieldStyle.fontColor = Color.WHITE;
		//TODO
		ws.background = skin.getDrawable("gray");

		LabelStyle ls = new LabelStyle(skin.getFont("default"), Color.WHITE);

		skin.add("default", ws);		
		skin.add("default", ls);
		skin.add("default", textButtonStyle);
		skin.add("default", textFieldStyle);

		
		
		final Table table = new Table();
		table.setSize(game.width, game.height);
		table.setPosition(0, 0);
		Label Title = new Label("Pole Jumper", ls);
		TextButton Play = new TextButton("Play",textButtonStyle);
		TextButton characterSelect = new TextButton("Character Select",textButtonStyle);
		TextButton Exit = new TextButton("Exit",textButtonStyle);
		final TextButton HighScores = new TextButton("HighScores",textButtonStyle);
		
		
		
		HighScoresTable = new Table();
		
		HighScoresTable.setSize(game.width, game.height);
		final TextButton Back = new TextButton("Back", skin);
		
		HighScoresTable.row().expand();
		HighScoresTable.add(Back).width(600).height(100).bottom();
		HighScoresTable.setVisible(false);
		HighScoresTable.debug();
		
		Back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				HighScoresTable.setVisible(false);
				table.setVisible(true);
				Back.setChecked(false);
				super.clicked(event, x, y);
			}
		});
		
		
		Exit.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				super.clicked(event, x, y);
			}
		});
		
		Play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				super.clicked(event, x, y);
			}
		});
		
		HighScores.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				table.setVisible(false);
				HighScoresTable.setVisible(true);
				HighScores.setChecked(false);
				super.clicked(event, x, y);
			}
		});
			
		
		
		// TODO replace with an image later.
		table.row();
		table.add(Title).width(600).height(100).pad(20).center();
		
		table.row();		
		table.add(Play).width(600).height(100).pad(20);
		
		table.row();
		table.add(characterSelect).width(600).height(100).pad(20);
		
		table.row();
		table.add(HighScores).width(600).height(100).pad(20);
		
		table.row();		
		table.add(Exit).width(600).height(100).pad(20);
		table.debug();
		
		
	
		stage.addActor(HighScoresTable);
		//stage.addActor(HighScoreWindow);
		stage.addActor(table);
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
		//game.setScreen(new GameScreen(game));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, .5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		game.batch.begin();
		//game.font.draw(game.batch, "Pole Jumper", game.width/2, game.height);
		
	
		if(HighScoresTable.isVisible()){
			
		for (int i = 0; i < game.highscores.scores.size(); i++) {
			Score s = game.highscores.scores.get(i);
			
			game.font.draw(game.batch, (i+1) + ". "+ s.name + ": " + s.score, game.width/2-100, game.height - i*30 -30 );
		}
			
		}
			
		
		
		game.batch.end();
		stage.act();
		stage.draw();
		Table.drawDebug(stage);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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

}
