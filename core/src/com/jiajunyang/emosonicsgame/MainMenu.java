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

import java.util.ArrayList;

/**
 * Created by jiajunyang on 02/06/16.
 */

public class MainMenu implements Screen {
    // For title screen
    Label titleLabel;
    // Set up the label style
    Label.LabelStyle labelStyle;


    // Game overall element.
    SpriteBatch batch;
    Stage stage;
    TextureAtlas buttonAtlas;
    TextButton.TextButtonStyle buttonStyle;
    TextButton playButton, sendIPButton;
    Skin skin;
    BitmapFont font;
    Game game;
    public MainMenu(Game game){
        this.game = game;
    }
    float scWidth, scHeight;


    // IP input section:
    // Define num pad properties.
    Label ipLabel, ipConfirmationLabel;
    DigitPad n1, n2, n3, n4, n5, n6, n7, n8,n9, n0, ndot, ndel;
    float n5x, n5y;
    public int numWidth = 260;
    public int numHeight = 260;
    // For appending the deleting only. The the actual variable to store IP
    ArrayList<String> inputIP = new ArrayList<String>();
    boolean checkIP = false; // This is used to check if ip is ok. Also it prevents game to start if ip not correct.
    public static String inputIPString = "";
    // -------------------------------

    // Pass IP to PlayScreen.java
    public static String retriveIP()
    {
        return inputIPString;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();

        scWidth = Gdx.graphics.getWidth();
        scHeight = Gdx.graphics.getHeight();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(8);
        labelStyle =  new Label.LabelStyle(font, Color.BLACK);

        // label: welcome page, ipLabel: display the entered IP address, ipConfirmationLabel: tell you whether input is valid.
        titleLabel = new Label("Welcome to EmoSonics", labelStyle);
        titleLabel.setPosition(scWidth/2  - titleLabel.getWidth()/2, scHeight - 200);

        // Numpad section
        // n5x, n5y define the center position of the key pad.
        n5x = 350;
        n5y = 2 * numHeight + 30;
        // Create instance.
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


        //ipLabel: display the entered IP address, ipConfirmationLabel: tell you whether input is valid.
        ipLabel = new Label("", labelStyle);
        ipConfirmationLabel = new Label("", labelStyle);
        final IPAddressValidator ipvalidator = new IPAddressValidator();
        ipLabel.setPosition(n5x - 1 * numWidth, n5y + 2 * numHeight + 50 );
        ipConfirmationLabel.setPosition(scWidth/2 - 200, scHeight - 400); // Best to be right next to Validation button
        //-------------------------------------

        // For buttons
        skin = new Skin();
        buttonAtlas = new TextureAtlas("buttons/button1.txt");
        skin.addRegions(buttonAtlas); // You need to do that.
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.over = skin.getDrawable("buttonpressed"); // over is not necessary for android.
        buttonStyle.down = skin.getDrawable("buttonpressed");
        buttonStyle.font = font;
        playButton = new TextButton("Let's Play", buttonStyle);
        sendIPButton = new TextButton("Confirm IP", buttonStyle);
        playButton.setPosition(scWidth - 400, 50);
        sendIPButton.setPosition(scWidth - 400,400);
        //-------------------------------------

        stage.addActor(playButton);
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
        stage.addActor(titleLabel);
        stage.addActor(ipLabel);
        stage.addActor(ipConfirmationLabel);

        Gdx.input.setInputProcessor(stage);

        // Play button
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button){

                if (checkIP){
                    game.setScreen(new PlayScreen(game));

                }
                else{
                    ipConfirmationLabel.setText("Please enter a valid IP before starting the game.");
                }

                return true;
            }
        });

        // Validate IP button
        sendIPButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button){


                checkIP = ipvalidator.validate(inputIPString);
                if (checkIP){
                    ipConfirmationLabel.setText("Valid IP");
                } else{
                    ipConfirmationLabel.setText("Invalid IP, please try again");
                }

                return true;
            }
        });
        //-------------------------------------

        // Add listener of the numpad.
        // Each badge print a specific call
        n1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("1");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s ;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("2");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s ;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("3");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s ;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("4");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n5.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("5");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n6.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("6");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n7.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("7");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n8.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("8");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n9.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("9");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        n0.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add("0");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        ndot.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.add(".");
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        ndel.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inputIP.remove(inputIP.size() - 1);
                inputIPString = "";
                for (String s : inputIP)
                {
                    inputIPString += s;
                }
                ipLabel.setText(inputIPString);
                System.out.println(inputIPString);
                return true;
            }
        });
        //-------------------------------------

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
