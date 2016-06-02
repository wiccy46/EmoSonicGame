package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

//public class EmoSonicsGame extends ApplicationAdapter {
public class EmoSonicsGame extends Game {
	int scWidth, scHeight;
	private SpriteBatch batch; // This is in the render.

	private Sprite catpawSprite; // Add chacrecteristic to the texture.
	private Sprite playcanvasSprite;

	// For the welcome page
	private Texture welcomePage;
	private Texture welcomeWord;
	private Texture playCanvas;
	private Texture mario;
	private Texture catpaw;

	int numPaws = 5;
	// It takes x, y, alpha
	float[]catpawAlpha = new float[numPaws];
	float[] catpawX = new float[numPaws];
	float[] catpawY = new float[numPaws];


	Vector2 position;

	String action;
	String model = "vocal";
	String myIP = "192.168.11.93";

	int gameState = 0; // 0: welcome, 1: play 2: result.

	// Creating the front page.
	Stage stage;
	Label label;
	Label.LabelStyle labelStyle;

	// For button.
	TextureAtlas buttonAtlas;
	TextButton.TextButtonStyle buttonStyle;
	TextButton button, sendIPButton;
	Skin skin;
	BitmapFont font;
	int catIdx = 0;

	Game game; // Menu has to go through game





	@Override
	public void create () {


		batch = new SpriteBatch();
		scWidth = Gdx.graphics.getWidth();
		scHeight = Gdx.graphics.getHeight();
		welcomePage = new Texture("welcomePage.png");
		welcomeWord = new Texture("welcomeWord.png");
		playCanvas = new Texture("playCanvas.png");
		mario = new Texture("mario.png");
		catpaw = new Texture("touch/catpaw.png");
		catpawSprite = new Sprite(catpaw);

		// Center the playcanvas with the dimension = scHeight * scHeight.
		playcanvasSprite = new Sprite(playCanvas);
		playcanvasSprite.setSize(scHeight, scHeight);
		playcanvasSprite.setPosition(scWidth/2 - scHeight/2, 0);



		position = new Vector2(50, 50);

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(5);

		gameState = 0;
		stage = new Stage();
		labelStyle =  new Label.LabelStyle(font, Color.BLACK);
		label = new Label("Welcome to EmoSonics", labelStyle);
		label.setPosition(scWidth/2  - label.getWidth()/2, scHeight - 200);

		stage.addActor(label);



		skin = new Skin();
		buttonAtlas = new TextureAtlas("buttons/button1.txt");
		skin.addRegions(buttonAtlas); // You need to do that.
		buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.over = skin.getDrawable("buttonpressed"); // over is not necessary for android.
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = font;
		button = new TextButton("start", buttonStyle);
		sendIPButton = new TextButton("Set IP", buttonStyle);
		button.setPosition(50, 50);
		sendIPButton.setPosition(500,500);
		stage.addActor(button);
		stage.addActor(sendIPButton);

		Gdx.input.setInputProcessor(stage);
		Gdx.input.setInputProcessor(stage);

		// Input listener
		button.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
			int pointer, int button){
				System.out.println("Clicked");
				gameState = 1;
				return true;
			}
		});

		sendIPButton.addListener(new InputListener(){
			@Override
			public boolean touchDown (InputEvent event, float x, float y,
									  int pointer, int button){
				myIP = "192.168.11.94";
				System.out.println(myIP);
				return true;
			}
		});

		// Init catpaw pos
		float temp = 1.f;
		for (int i = 0 ; i < numPaws; i++){
			catpawAlpha[i] = temp ;
			temp -= 0.2f;
			for(int j = 0; j < 2; j ++){
				catpawX[i] = -250f; // Move it out of the screen first.
				catpawY[i] = -250f;
			}
		}
		// -------------------

	}

	@Override
	public  void dispose(){
		batch.dispose();
//		texture.dispose();
	}

	// Move element one up for the fading effect.
	public void updatePaws(int x, int y ){
		for (int i = numPaws - 1 ; i > 0; i--){
			catpawX[i] = catpawX[i - 1];
			catpawY[i] = catpawY[i - 1];
		}
		catpawX[0] = x - catpaw.getWidth() / 2;
		catpawY[0] = Gdx.graphics.getHeight() - y - catpaw.getHeight() / 2;
		catIdx++;
		if (catIdx == numPaws) {
			catIdx = 0;
		}
	}


	@Override
	public void render (){
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		stage.act();

//		batch.draw(welcomePage, 0, 0, scWidth, scHeight );


//		batch.draw(mario, position.x, position.y);

		if (gameState == 0){
//			batch.draw(welcomePage, 0, 0, scWidth, scHeight );

			// Touch to start the game.

		}
		else if (gameState == 1) {
			// Draw playcanvas first.
//			batch.draw(playCanvas, scWidth/2 - playCanvas.getWidth()/2, scHeight/2 - playCanvas.getHeight()/2);
			playcanvasSprite.draw(batch);
			if (Gdx.input.justTouched()) {
				updatePaws(Gdx.input.getX(), Gdx.input.getY());
			}
			for (int i = 0; i < numPaws; i++) {
				catpawSprite.setPosition(catpawX[i], catpawY[i]);

				catpawSprite.draw(batch, catpawAlpha[i]);
			}
		}

		stage.draw();

//		batch.draw(welcomePage, 0, 0, scWidth, scHeight );
		batch.end();
	}
}
