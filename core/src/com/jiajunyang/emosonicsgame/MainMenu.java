package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by jiajunyang on 02/06/16.
 */

// Maybe I dont need the set IP button.

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
    public static String myIP = "192.168.11.93";
    float scWidth, scHeight;


    DigitPad n1, n2, n3, n4, n5, n6, n7, n8,n9, n0, ndot, ndel;
    float n5x, n5y;
    public int numWidth = 260;
    public int numHeight = 260;




    // Pass IP to PlayScreen.java
    public static String retriveIP()
    {
        return myIP;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        scWidth = Gdx.graphics.getWidth();
        scHeight = Gdx.graphics.getHeight();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);

        n5x = 350;
        n5y = 2 * numHeight + 30;


        n1 = new DigitPad("numPad/1.png", new Vector2(n5x - 1 * numWidth, n5y + numHeight),new Vector2(numWidth, numHeight));
        n2 = new DigitPad("numPad/2.png", new Vector2(n5x, n5y + numHeight),new Vector2(numWidth, numHeight));
        n3 = new DigitPad("numPad/3.png", new Vector2(n5x + 1 * numWidth, n5y + numHeight),new Vector2(numWidth, numHeight));
        n4 = new DigitPad("numPad/4.png", new Vector2(n5x - 1 * numWidth, n5y),new Vector2(numWidth, numHeight));
        n5 = new DigitPad("numPad/5.png", new Vector2(n5x, n5y),new Vector2(numWidth, numHeight));
        n6 = new DigitPad("numPad/6.png", new Vector2(n5x + 1 * numWidth, n5y),new Vector2(numWidth, numHeight));
        n7 = new DigitPad("numPad/7.png", new Vector2(n5x - 1 * numWidth, n5y - numHeight),new Vector2(numWidth, numHeight));
        n8 = new DigitPad("numPad/8.png", new Vector2(n5x, n5y - numHeight),new Vector2(numWidth, numHeight));
        n9 = new DigitPad("numPad/9.png", new Vector2(n5x + 1 * numWidth, n5y - numHeight),new Vector2(numWidth, numHeight));
        ndot = new DigitPad("numPad/dot.png", new Vector2(n5x - 1 * numWidth,n5y - 2 * numHeight ),new Vector2(numWidth, numHeight));
        ndel = new DigitPad("numPad/del.png", new Vector2(n5x + 1 * numWidth, n5y - 2 * numHeight),new Vector2(numWidth, numHeight));
        n0 = new DigitPad("numPad/0.png", new Vector2(n5x, n5y - 2 * numHeight),new Vector2(numWidth, numHeight));


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
        button.setPosition(scWidth - 400, 50);
        sendIPButton.setPosition(scWidth - 400,400);
        stage.addActor(button);
        stage.addActor(sendIPButton);

        stage.addActor(n0);
        stage.addActor(n1);
        stage.addActor(n2);
        stage.addActor(n3);
        stage.addActor(n4);
        stage.addActor(n5);
        stage.addActor(n6);
        stage.addActor(n7);
        stage.addActor(n8);
        stage.addActor(n9);
        stage.addActor(ndot);
        stage.addActor(ndel);

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
