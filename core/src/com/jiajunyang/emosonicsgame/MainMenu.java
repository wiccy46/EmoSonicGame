package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by jiajunyang on 02/06/16.
 */

public class MainMenu implements Screen {

    SpriteBatch batch;
    // All UI element.
    Stage stage;
    Label label;
    Label.LabelStyle labelStyle;

    // For button.
    TextureAtlas buttonAtlas;
    TextButton.TextButtonStyle buttonStyle;
    TextButton button, sendIPButton;
    Skin skin;
    BitmapFont font;

    Game game;
    public MainMenu(Game game){
        this.game = game;
    }


    String myIP = "192.168.11.93";
    float scWidth, scHeight;


    @Override
    public void show() {
        batch = new SpriteBatch();

        scWidth = Gdx.graphics.getWidth();
        scHeight = Gdx.graphics.getHeight();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);

        stage = new Stage();

        // Create title
        labelStyle =  new Label.LabelStyle(font, Color.BLACK);
        label = new Label("Welcome to EmoSonics", labelStyle);
        label.setPosition(scWidth/2  - label.getWidth()/2, scHeight - 200);
        stage.addActor(label);

        // For buttons
        skin = new Skin();
        buttonAtlas = new TextureAtlas("buttons/button1.txt");
        skin.addRegions(buttonAtlas); // You need to do that.
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.over = skin.getDrawable("buttonpressed"); // over is not necessary for android.
        buttonStyle.down = skin.getDrawable("buttonpressed");
        buttonStyle.font = font;
        button = new TextButton("begin", buttonStyle);
        sendIPButton = new TextButton("Set IP", buttonStyle);
        button.setPosition(50, 20);
        sendIPButton.setPosition(50,500);
        stage.addActor(button);
        stage.addActor(sendIPButton);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(stage);

        // Input listener for buttons.
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button){
                game.setScreen(new PlayScreen(game));

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
        // It is like create
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Same as other render.
        stage.act();
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
