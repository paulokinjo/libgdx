package com.starfishcollector.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorBeta extends Actor {

    private final TextureRegion textureRegion;
    private final Rectangle rectangle;

    public ActorBeta() {
        this(new TextureRegion(), new Rectangle());
    }

    public ActorBeta(TextureRegion textureRegion, Rectangle rectangle) {
        super();
        this.textureRegion = textureRegion;
        this.rectangle = rectangle;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Rectangle getRectangle() {
        this.rectangle.setPosition(getX(), getY());
        return rectangle;
    }

    public void setTexture(Texture texture) {
        this.textureRegion.setRegion(texture);

        int width = texture.getWidth();
        int height = texture.getHeight();

        setSize(width, height);

        this.rectangle.setSize(width, height);
    }

    public boolean overlaps(ActorBeta other) {
        return this.getRectangle().overlaps(other.getRectangle());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor();
        setColor(c.r, c.g, c.b, c.a);

        if (isVisible()) {
            batch.draw(textureRegion,
                    getX(), getY(),
                    getOriginX(), getOriginY(),
                    getWidth(), getHeight(),
                    getScaleX(), getScaleY(), getRotation());
        }
    }
}
