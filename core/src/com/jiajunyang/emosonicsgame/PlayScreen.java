package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayScreen implements Screen {
    String myIP = "192.168.11.93";
    String model = "abstract";
    String action, msg;

    int scWidth, scHeight;
    int playerWidth, playerHeight;

    Stage stage;
    Skin skin;
    BitmapFont font;
    private SpriteBatch batch; // This is in the render.
    private Sprite catpawSprite; // Add chacrecteristic to the texture.

    ArrayList<Tile> tiles;
    Iterator<Tile> tileIterator;

    Player player;
    ArrayList<Badge> badges;
    Iterator<Badge> badgeIterator;
    int badgeWidth = 160;
    int badgeHeight = 300;
    int numBadges = 4;

    Rectangle playCanvasRect;

    private Texture catpaw;

    int numPaws = 5;
    // It takes x, y, alpha
    float[]catpawAlpha = new float[numPaws];
    float[] catpawX = new float[numPaws];
    float[] catpawY = new float[numPaws];
    int catIdx = 0;

    // Create a constructor
    Game game;
    public PlayScreen(Game game){
        this.game = game;
    }

    // Create a button to randomise tree positions
    TextureAtlas buttonAtlas;
    TextButton.TextButtonStyle buttonStyle;
    TextButton playButton;


    int randomBadgePosition(String choice)
    {
        int min, max, range;
        if (choice == "x") {
            min = scWidth / 2 - scHeight / 2;
            max = scWidth / 2 + scHeight / 2 - badgeWidth;
            range = (max - min) + 1;
            return (int) (Math.random() * range) + min;
        } else{
            min = 0;
            max = scHeight - badgeHeight;
            range = (max - min) + 1;
            return (int)(Math.random() * range) + min;
        }
    }




    @Override
    public void show() {

        batch = new SpriteBatch();
        stage = new Stage();
        scWidth = Gdx.graphics.getWidth();
        scHeight = Gdx.graphics.getHeight();


        catpaw = new Texture("touch/catpaw.png");
        catpawSprite = new Sprite(catpaw);
        catpawSprite.setScale(0.5f, 0.5f);


        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);


        // This is for detecting player entering the play canvas.
        playCanvasRect = new Rectangle();
        playCanvasRect.setSize(scHeight, scHeight);
        playCanvasRect.setPosition(scWidth/2 - scHeight/2, 0);


        // Pos and size
        // Initialise the badges but move them outside
        playerWidth = 180;
        playerHeight = 240;

        player = new Player("mario.png", new Vector2(50, 300),new Vector2(playerWidth, playerHeight)) ;
        badges = new ArrayList<Badge>();

        for (int i = 0; i < numBadges; i ++){
            badges.add(new Badge(new Vector2(-400, -400),new Vector2(badgeWidth, badgeHeight) ));
        }

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

        // Initialise tile
        tiles = new ArrayList<Tile>();
        // Add tile
        int startTile = (scWidth - scHeight)/2;
        int division = scHeight/6;
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                int R = (int) ((Math.random() * (2 - 0) + 0)); // random between 1 or 0
                if (R == 0){
                    // It can alternate between two different title contents.
                    tiles.add(new Tile(new Texture("grass.jpg"), startTile+ i * division, j * division, division, division));
                } else{
                    tiles.add(new Tile(new Texture("grass.jpg"), startTile + i * division, j * division, division, division));
                }
            }
        }

        skin = new Skin();

        buttonAtlas = new TextureAtlas("buttons/button1.txt");
        skin.addRegions(buttonAtlas); // You need to do that.
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.over = skin.getDrawable("buttonpressed"); // over is not necessary for android.
        buttonStyle.down = skin.getDrawable("buttonpressed");
        buttonStyle.font = font;

        playButton = new TextButton("play", buttonStyle);
        playButton.setPosition(50, 20);

        stage.addActor(playButton);
        stage.addActor(player);

//
        for (int i = 0; i < numBadges; i ++){
            stage.addActor(badges.get(i));
        }


        Gdx.input.setInputProcessor(stage);

        // Each badge print a specific call
        badges.get(0).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                msg = "0";
                action = "play";
                Thread play = new Thread(new OSCSend(myIP, model, action,msg));
                play.start();
                badges.get(0).setScale(1.2f, 1.2f);
                return true;
            }
        });
        badges.get(1).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                msg = "1";
                action = "play";
                Thread play = new Thread(new OSCSend(myIP, model, action, msg));
                play.start();
                badges.get(1).setScale(1.2f, 1.2f);
                return true;
            }
        });
        badges.get(2).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                action = "play";
                msg = "2";
                Thread play = new Thread(new OSCSend(myIP, model, action, msg));
                play.start();
                badges.get(2).setScale(1.2f, 1.2f);
                return true;
            }
        });
        badges.get(3).addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                action = "play";
                msg = "3";
                Thread play = new Thread(new OSCSend(myIP, model, action, msg));
                play.start();
                badges.get(3).setScale(1.2f, 1.2f);
                return true;
            }
        });


        // Input listener for buttons.
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y,
                                      int pointer, int button){
                int tempX, tempY;
                // Randomise the locations of the badges.
                for (int i = 0; i < badges.size(); i++){
                    tempX = randomBadgePosition("x");
                    tempY = randomBadgePosition("y");
                    badges.get(i).setBounds(tempX, tempY, badgeWidth, badgeHeight);
//                    badges.get(i).setPosition(new Vector2(tempX, tempY));
                }
                return true;
            }
        });


        player.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                player.setBounds(Gdx.input.getX() - playerWidth/2,
                        scHeight - Gdx.input.getY() - playerHeight/2, playerWidth, playerHeight);

            }
        });

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
    public void render(float delta) {

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        tileIterator = tiles.iterator();
        while(tileIterator.hasNext()){
            Tile cur = tileIterator.next();
            cur.render(batch);
        }

//
//        badgeIterator = badges.iterator();
//
//        while(badgeIterator.hasNext()){
//            Badge cur = badgeIterator.next();
//            cur.draw(batch);
//        }


        if (Gdx.input.justTouched()) {
            updatePaws(Gdx.input.getX(), Gdx.input.getY());
        }

        for (int i = 0; i < numPaws; i++) {
            catpawSprite.setPosition(catpawX[i], catpawY[i]);
            catpawSprite.draw(batch, catpawAlpha[i]);
        }


        batch.end();
        stage.act();
        stage.draw();
        player.update(); // Update the bound
        if (Intersector.overlaps(player.bounds, playCanvasRect)){
            // Once insigt use it to send OSC. But dont do it all the time
            Gdx.app.log("OSC", "Hit.");
        }

        for (int i = 0; i < numBadges; i ++){
            badges.get(i).setScale(1.f, 1.f);
        }


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
