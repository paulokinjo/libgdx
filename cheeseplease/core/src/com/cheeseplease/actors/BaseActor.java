package com.cheeseplease.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

    protected TextureRegion region;
    protected Rectangle boundary;
    protected float velocityX;
    protected float velocityY;

    public BaseActor() {
        super();

        this.region = new TextureRegion();
        this.boundary = new Rectangle();
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setTexture(Texture texture) {
        int w = texture.getWidth();
        int h = texture.getHeight();

        super.setWidth(w);
        super.setHeight(h);

        this.region.setRegion(texture);
    }

    public Rectangle getBoundingRectangle() {
        this.boundary.set(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        return this.boundary;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        super.moveBy(this.velocityX * delta, this.velocityY * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = super.getColor();

        batch.setColor(c.r, c.g, c.b, c.a);

        if (super.isVisible()) {
            batch.draw(this.region, super.getX(), super.getY(), super.getOriginX(), super.getOriginY(),
                    super.getWidth(), super.getHeight(), super.getScaleX(), super.getScaleY(), super.getRotation());
        }
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(float x) {
        this.velocityX = x;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(float y) {
        this.velocityY = y;
    }

    public void addVelocityX(int amount) {
        this.velocityX += amount;
    }

    public void addVelocityY(int amount) {
        this.velocityY += amount;
    }
}
