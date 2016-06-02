package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.Game;

//public class EmoSonicsGame extends ApplicationAdapter {
public class EmoSonicsGame extends Game {
	Game game;

	@Override
	public void create () {
		game = this;
		// First call the main menu.
		setScreen(new MainMenu(game));
	}

	@Override
	public  void dispose(){
	}

	@Override
	public void render (){
		super.render();
	}
}
