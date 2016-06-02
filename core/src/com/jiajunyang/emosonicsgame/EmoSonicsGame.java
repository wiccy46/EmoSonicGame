package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.ApplicationAdapter;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EmoSonicsGame extends ApplicationAdapter {
	int scWidth, scHeight;
	private SpriteBatch batch; // This is in the render.
	private OrthographicCamera camera;
	private Sprite catpawSprite; // Add chacrecteristic to the texture.

	// For the welcome page
	private Texture welcomePage;
	private Texture welcomeWord;
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

	// For button.
	TextureAtlas buttonAtlas;
	TextButton.TextButtonStyle buttonStyle;
	TextButton button;
	Skin skin;
	BitmapFont font;
	int catIdx = 0;

	// InputProcessor is a userful thing if you want full access.

	@Override
	public void create () {
		batch = new SpriteBatch();
		scWidth = Gdx.graphics.getWidth();
		scHeight = Gdx.graphics.getHeight();
		welcomePage = new Texture("welcomePage.png");
		welcomeWord = new Texture("welcomeWord.png");
		mario = new Texture("mario.png");
		catpaw = new Texture("touch/catpaw.png");
		catpawSprite = new Sprite(catpaw);
		position = new Vector2(50, 50);


//		welcomeWord = new Texture("playCanvas.png");
		gameState = 0;

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		skin = new Skin();
		buttonAtlas = new TextureAtlas("buttons/button1.txt");
		skin.addRegions(buttonAtlas); // You need to do that.
		buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button");
		buttonStyle.over = skin.getDrawable("buttonpressed");
		buttonStyle.down = skin.getDrawable("buttonpressed");
		buttonStyle.font = font;
		button = new TextButton("start", buttonStyle);

		// Init catpaw pos
		float temp = 1.f;
		for (int i = 0 ; i < numPaws; i++){
			catpawAlpha[i] = temp ;
			temp -= 0.2f;
			for(int j = 0; j < 2; j ++){
				catpawX[i] = -50f;
				catpawY[i] = -50f;
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
	public void shiftArray(int x, int y ){
		for (int i = numPaws - 1 ; i > 0; i--){
			Gdx.app.log("Debug: ", Integer.toString(i));
			catpawX[i] = catpawX[i - 1];
			catpawY[i] = catpawY[i - 1];
		}
	}


	@Override
	public void render (){
		batch.begin();
		batch.draw(welcomePage, 0, 0, scWidth, scHeight );
		batch.draw(mario, position.x, position.y);

		if (gameState == 0){
			batch.draw(welcomePage, 0, 0, scWidth, scHeight );
			batch.draw(welcomeWord, scWidth/2 - welcomeWord.getWidth()/2, scHeight - 200 );
			// Touch to start the game.
			if (Gdx.input.justTouched()){
				gameState = 1;
			}

		}
		else if (gameState == 1) {
			if (Gdx.input.justTouched()) {
				shiftArray(Gdx.input.getX(), Gdx.input.getY());

				catpawX[0] = Gdx.input.getX() - catpaw.getWidth() / 2;
				catpawY[0] = Gdx.graphics.getHeight() - Gdx.input.getY() - catpaw.getHeight() / 2;
				catIdx++;
				if (catIdx == numPaws) {
					catIdx = 0;
				}
			}
			for (int i = 0; i < numPaws; i++) {
				catpawSprite.setPosition(catpawX[i], catpawY[i]);
				catpawSprite.draw(batch, catpawAlpha[i]);
			}
		}

		batch.end();
	}

}