package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EmoSonicsGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	String action;
	String model = "vocal";
	String myIP = "192.168.11.93";



	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.justTouched()){
			action = "play";
			Thread play = new Thread(new OSCSend(myIP, model, action));
			play.start();
		}


		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}
