package com.starfishcollector.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.starfishcollector.game.fixtures.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@RunWith(GdxTestRunner.class)
public class ActorBetaTests {

    private static ActorBeta actor;

    @Before
    public void beforeEach() {
        actor = Mockito.spy(new ActorBeta());
    }

    @Test
    public void actor_whenValid_createsTextureRegion() {
        assertNotNull(actor.getTextureRegion());
    }

    @Test
    public void actor_whenValid_createsARectangle() {
        assertNotNull(actor.getRectangle());
    }

    @Test
    public void whenGetRectangle_shouldSetPosition_calledWithXAndY() {
        Rectangle rectangle = mock(Rectangle.class);
        actor = new ActorBeta(null, rectangle);
        actor.getRectangle();
        Mockito.verify(rectangle).setPosition(0, 0);
    }

    @Test
    public void whenSetTexture_setsRegion_withParameter() {
        TextureRegion textureRegion = mock(TextureRegion.class);
        actor = new ActorBeta(textureRegion, new Rectangle());
        Texture texture = new Texture(Gdx.files.internal("../core/assets/turtle-1.png"));
        actor.setTexture(texture);

        Mockito.verify(textureRegion).setRegion(texture);
    }

    @Test
    public void whenSetTexture_setSize_withRespectiveWidthAndHeightParameters() {
        TextureRegion textureRegion = mock(TextureRegion.class);
        actor = Mockito.spy(new ActorBeta(textureRegion, new Rectangle()));
        Texture texture = new Texture(Gdx.files.internal("../core/assets/turtle-1.png"));
        actor.setTexture(texture);

        Mockito.verify(actor, times(1)).setSize(texture.getWidth(), texture.getHeight());
    }

    @Test
    public void whenSetTexture_setsRectangleSize_withParamterTextureWidthAndHeight() {
        TextureRegion textureRegion = mock(TextureRegion.class);
        Rectangle rectangle = mock(Rectangle.class);
        actor = new ActorBeta(textureRegion, rectangle);
        Texture texture = new Texture(Gdx.files.internal("../core/assets/turtle-1.png"));
        actor.setTexture(texture);

        Mockito.verify(rectangle).setSize(texture.getWidth(), texture.getHeight());
    }

    @Test
    public void overlaps_whenRectangleXAndRectangleYOverlaps_ReturnsTrue() {
        ActorBeta mainActor = createValidActor(10, 10);
        ActorBeta otherActor = createValidActor(10, 10);
        assertTrue(mainActor.overlaps(otherActor));
    }

    @Test
    public void overlaps_whenRectangleXAndRectangleYDoesNotOverlaps_ReturnsFalse() {
        ActorBeta mainActor = createValidActor(10, 10);
        ActorBeta otherActor = createValidActor(999, 999);
        assertFalse(mainActor.overlaps(otherActor));
    }

    @Test
    public void draw_shouldSetColor() {
        Batch batch = mock(Batch.class);
        actor.draw(batch, 100f);
        Color c = actor.getColor();
        Mockito.verify(actor).setColor(c.r, c.g, c.b, c.a);
    }

    @Test
    public void draw_whenVisible_drawTexture() {
        Batch batch = mock(Batch.class);
        InOrder orderVerifier = inOrder(batch);

        TextureRegion textureRegion = new TextureRegion();
        Rectangle rectangle = new Rectangle();
        actor = new ActorBeta(textureRegion, rectangle);
        actor.setPosition(1, 2);
        actor.setOrigin(3, 4);
        actor.setSize(5, 6);
        actor.setScale(7, 8);
        actor.setRotation(9);
        actor.draw(batch, 100f);
        orderVerifier.verify(batch).draw(textureRegion,
                actor.getX(), actor.getY() ,
                actor.getOriginX(),actor.getOriginY(),
                actor.getWidth(),actor.getHeight(),
                actor.getScaleX(),actor.getScaleY(), actor.getRotation());
    }

    @Test
    public void draw_whenNotVisible_shouldNotDrawTexture() {
        Batch batch = mock(Batch.class);
        actor.setVisible(false);
        actor.draw(batch, 100f);

        Color c = actor.getColor();
        Mockito.verify(actor).setColor(c.r, c.g, c.b, c.a);
        Mockito.verifyNoMoreInteractions(batch);
    }

    private ActorBeta createValidActor(float posX, float posY) {
        ActorBeta validActor = new ActorBeta();
        Texture texture = new Texture(Gdx.files.internal("../core/assets/turtle-1.png"));
        validActor.setTexture(texture);
        validActor.setPosition(posX, posY);

        return validActor;
    }
}
