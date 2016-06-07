package com.jiajunyang.emosonicsgame;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Badge  {

    Vector2 position, size;
    Texture badge;
    Rectangle bounds;

    public Badge(Vector2 position, Vector2 size){
//        super(Gdx.files.internal("tree.png"));
        this.position = position;
        this.size = size;
        bounds = new Rectangle(position.x, position.y, size.x, size.y);
        badge = new Texture(Gdx.files.internal("tree.png"));
    }

    public void update(){
        bounds.set(position.x, position.y, size.x, size.y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(badge, position.x, position.y, size.x, size.y);
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