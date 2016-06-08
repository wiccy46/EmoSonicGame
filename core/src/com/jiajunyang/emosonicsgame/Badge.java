package com.jiajunyang.emosonicsgame;


// Badge is used as a randomise trees on the playcanvas. By clicking on it. It trigger a sound.

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Badge extends Image {

    Vector2 position, size;
    Rectangle bounds;

    public Badge(Vector2 position, Vector2 size){
        super(new Texture(Gdx.files.internal("tree.png")));
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        super.setPosition(position.x, position.y);
        super.setSize(size.x, size.y);
    }

//    public void update(){
//        bounds.set(position.x, position.y, size.x, size.y);
//    }
//
//    public void draw(SpriteBatch batch){
//        batch.draw(badge, position.x, position.y, size.x, size.y);
//    }
//
//    public Vector2 getPosition() {
//        return position;
//    }
//
//    public void setPosition(Vector2 position) {
//        this.position = position;
//    }
//
//    public Vector2 getSize() {
//        return size;
//    }
//
//    public void setSize(Vector2 size) {
//        this.size = size;
//    }
//
//    public Rectangle getBounds() {
//        return bounds;
//    }
//
//    public void setBounds(Rectangle bounds) {
//        this.bounds = bounds;
//    }

}