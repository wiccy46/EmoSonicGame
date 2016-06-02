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
	private Sprite sprite; // Add chacrecteristic to the texture.

	// For the welcome page
	private Texture welcomePage;
	Texture mario;
	Texture catpaw;

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

	// InputProcessor is a userful thing if you want full access.

	@Override
	public void create () {
		batch = new SpriteBatch();
		scWidth = Gdx.graphics.getWidth();
		scHeight = Gdx.graphics.getHeight();
		welcomePage = new Texture("welcomePage.png");
		mario = new Texture("mario.png");
		catpaw = new Texture("touch/catpaw.png");
		position = new Vector2(50, 50);

//		welcomeWord = new Texture("welcomeWord.png");
//		welcomeWord = new Texture("playCanvas.png");
//		gameState = 0;

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

	}

	@Override
	public  void dispose(){
		batch.dispose();
//		texture.dispose();
	}

	@Override
	public void render (){
		batch.begin();
		batch.draw(welcomePage, 0, 0, scWidth, scHeight );
		batch.draw(mario, position.x, position.y);

		if(Gdx.input.justTouched()){
//			Gdx.app.log("Debug: ", "Pressed");
			batch.draw(catpaw, Gdx.input.getX() - catpaw.getWidth()/2 ,  Gdx.graphics.getHeight() - Gdx.input.getY()- catpaw.getHeight()/2 );
		}

		batch.end();
	}

}
