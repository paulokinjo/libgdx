package com.starfishcollector.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.starfishcollector.game.Assets;
import com.starfishcollector.game.fixtures.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class TurtleTests {
    public static final String CORE_ASSETS = "../core/assets/";

    private Input input;

    private final float initialTurtleX = 0.0f;
    private final float initialTurtleY = 0.0f;

    private final float deltaPrecision = 0.0f;
    private Turtle turtle;

    @Before
    public void beforeEach() {
        input = mock(Input.class);
        Gdx.input = input;
        turtle = Mockito.spy(new Turtle());
    }

    @Test
    public void turtleAsset_exists() {
        FileHandle turtleAsset = Gdx.files.internal(CORE_ASSETS + Assets.TURTLE_1_PNG);
        assertTrue("Turtle asset does not exist at " + CORE_ASSETS, turtleAsset.exists());
    }

    @Test
    public void act_inputLeft_moveTurtle1pixelLeft() {
        prepareInputKeyCondition(Input.Keys.LEFT);

        turtle.act(1);

        verifyTurtleDrawPosition((initialTurtleX - 1), initialTurtleY);
    }

    @Test
    public void act_inputRight_moveTurtle1pixelRight() {
        prepareInputKeyCondition(Input.Keys.RIGHT);

        turtle.act(1);

        verifyTurtleDrawPosition((initialTurtleX + 1), initialTurtleY);
    }

    @Test
    public void act_inputUp_moveTurtle1pixelUp() {
        prepareInputKeyCondition(Input.Keys.UP);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY + 1);
    }

    @Test
    public void act_inputDown_moveTurtle1pixelDown() {
        prepareInputKeyCondition(Input.Keys.DOWN);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY - 1);
    }

    @Test
    public void act_inputRightDownDiagonal_moveTurtle1pixelDownAnd1PixelRight() {
        prepareInputKeyCondition(Input.Keys.DOWN);
        prepareInputKeyCondition(Input.Keys.RIGHT);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX + 1, initialTurtleY - 1);
    }

    @Test
    public void act_inputDownLeftDiagonal_moveTurtle1pixelDownAnd1PixelLeft() {
        prepareInputKeyCondition(Input.Keys.DOWN);
        prepareInputKeyCondition(Input.Keys.LEFT);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX - 1, initialTurtleY - 1);
    }

    @Test
    public void act_inputLeftUpDiagonal_moveTurtle1pixelUpAnd1PixelLeft() {
        prepareInputKeyCondition(Input.Keys.UP);
        prepareInputKeyCondition(Input.Keys.LEFT);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX - 1, initialTurtleY + 1);
    }

    @Test
    public void act_inputRightUpDiagonal_moveTurtle1pixelUpAnd1PixelRight() {
        prepareInputKeyCondition(Input.Keys.UP);
        prepareInputKeyCondition(Input.Keys.RIGHT);

        turtle.act(1);

        verifyTurtleDrawPosition(initialTurtleX + 1, initialTurtleY + 1);
    }

    private void prepareInputKeyCondition(int key) {
        when(input.isKeyPressed(key)).thenReturn(true);
    }

    private void verifyTurtleDrawPosition(float x, float y) {
        assertEquals(turtle.getX(), x, deltaPrecision);
        assertEquals(turtle.getY(), y, deltaPrecision);
    }
}
