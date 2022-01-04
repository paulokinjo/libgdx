package com.cheeseplease.actors;

import javax.xml.soap.Text;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class AnimatedActor extends BaseActor {

    protected float elapsedTime;
    protected Animation animation;

    public AnimatedActor() {
        super();

        this.elapsedTime = 0;
    }

    public void setAnimation(Animation animation) {
        Texture texture = ((TextureRegion) animation.getKeyFrame(0)).getTexture();
        super.setTexture(texture);
        this.animation = animation;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.elapsedTime += delta;

        if (super.getVelocityX() != 0 || super.getVelocityY() != 0) {
            super.setRotation(MathUtils.atan2(super.getVelocityY(), super.getVelocityX()) * MathUtils.radiansToDegrees);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.region.setRegion((TextureRegion) this.animation.getKeyFrame(this.elapsedTime));
        super.draw(batch, parentAlpha);
    }
}
