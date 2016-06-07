package com.jiajunyang.emosonicsgame;

/**
 * Created by jiajunyang on 07/06/16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class Player extends Image {

    Vector2 position, size;
    Texture player;
    Rectangle bounds;

    // THis is the onCreate method:
    public Player(String fileName, Vector2 position, Vector2 size){
        super(new Texture(Gdx.files.internal(fileName)));
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
//        Image player = new Image(new Texture(Gdx.files.internal(fileName)));
        // Because different player can have different image
//        player = new Texture(Gdx.files.internal(fileName));
    }

    public void update(){
        bounds.set(position.x, position.y, size.x, size.y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(player, position.x, position.y, size.x, size.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }



}