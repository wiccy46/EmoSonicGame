package com.jiajunyang.emosonicsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jiajunyang on 02/06/16.
 */
public class PlayScreen implements Screen {
    int scWidth, scHeight;
    private SpriteBatch batch; // This is in the render.

    private Sprite catpawSprite; // Add chacrecteristic to the texture.
    private Sprite playcanvasSprite;
    private Sprite marioSprite;

    // For the welcome page
    private Texture welcomePage;
    private Texture playCanvas;
    private Texture mario;
    ArrayList<Tile> tiles;
    Iterator<Tile> tileIterator;
    ArrayList<Badge> badges;
    Iterator<Badge> badgeIterator;

    Rectangle playCanvasRect;


    Circle marioCircle = new Circle();
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

    Badge badge1, badge2, badge3, badge4;


    @Override
    public void show() {

        batch = new SpriteBatch();
        scWidth = Gdx.graphics.getWidth();
        scHeight = Gdx.graphics.getHeight();
        welcomePage = new Texture("welcomePage.png");
        playCanvas = new Texture("playCanvas.png");
        mario = new Texture("mario.png");
        catpaw = new Texture("touch/catpaw.png");
        catpawSprite = new Sprite(catpaw);
        catpawSprite.setScale(0.5f, 0.5f);
        marioSprite = new Sprite(mario);


        // Center the playcanvas with the dimension = scHeight * scHeight.
        playcanvasSprite = new Sprite(playCanvas);
        playcanvasSprite.setSize(scHeight, scHeight);
        playcanvasSprite.setPosition(scWidth/2 - scHeight/2, 0);

        playCanvasRect = new Rectangle();
        playCanvasRect.setSize(scHeight, scHeight);
        playCanvasRect.setPosition(scWidth/2 - scHeight/2, 0);

        marioSprite.setScale(0.5f, 0.5f);
        marioCircle.setRadius(marioSprite.getWidth() * 0.3f); // 5% of the width

        tiles = new ArrayList<Tile>();
        tiles.add(new Tile(welcomePage, 0, 0, scWidth,scHeight));

        // Pos and size
        badge1 = new Badge(new Vector2(100, 100),new Vector2(100, 200) );
        badge2 = new Badge(new Vector2(200, 200),new Vector2(100, 200) );
        badge3 = new Badge(new Vector2(300, 300),new Vector2(100, 200) );
        badge4 = new Badge(new Vector2(400, 400),new Vector2(100, 200) );
        badges = new ArrayList<Badge>();
        badges.add(badge1);
        badges.add(badge2);
        badges.add(badge3);
        badges.add(badge4);



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

        badgeIterator = badges.iterator();
        while(badgeIterator.hasNext()){
            Badge cur = badgeIterator.next();
            cur.draw(batch);

        }




//        playcanvasSprite.draw(batch);
        // Draw a rectangle over the canvas



        if (Gdx.input.justTouched()) {
            updatePaws(Gdx.input.getX(), Gdx.input.getY());
        }
        for (int i = 0; i < numPaws; i++) {
            catpawSprite.setPosition(catpawX[i], catpawY[i]);
            catpawSprite.draw(batch, catpawAlpha[i]);
        }

        marioSprite.setPosition(Gdx.input.getX() - marioSprite.getWidth()/2,
            scHeight - Gdx.input.getY() - marioSprite.getHeight()/2);
        marioSprite.draw(batch, 1.f);



        batch.end();
        marioCircle.setPosition(Gdx.input.getX(), scHeight - Gdx.input.getY());
        if (Intersector.overlaps(marioCircle, playCanvasRect)){
            // Once insigt use it to send OSC. But dont do it all the time
//            Gdx.app.log("OSC", "Hit.");
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
