package com.starfishcollector.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class StarfishCollectorGame extends ApplicationAdapter {
    private SpriteBatch spriteBatch;

    private Texture turtleTexture;
    private Rectangle turtleRectangle;
    private float turtleX;
    private float turtleY;

    private Texture oceanTexture;
    private float oceanX;
    private float oceanY;

    private Texture starfishTexture;
    private float starfishX;
    private float starfishY;
    private Rectangle starfishRectangle;

    private Texture winTexture;
    private boolean win;
    private float winX;
    private float winY;

    @Override
    public void create() {
        this.initialize(new SpriteBatch());
    }

    @Override
    public void render() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            turtleX--;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            turtleX++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            turtleY++;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            turtleY--;
        }

        this.turtleRectangle.setPosition(turtleX, turtleY);

        if(turtleRectangle.overlaps(starfishRectangle)) {
            win = true;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(oceanTexture, oceanX, oceanY);

        if(!win) {
            spriteBatch.draw(starfishTexture, starfishX, starfishY);
            spriteBatch.draw(turtleTexture, turtleX, turtleY);
        } else {
            spriteBatch.draw(winTexture, winX, winY);
        }

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    public void initialize(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;

        turtleTexture = new Texture(Gdx.files.internal(Assets.TURTLE_1_PNG));
        turtleX = 20;
        turtleY = 20;
        turtleRectangle = new Rectangle(turtleX, turtleY, turtleTexture.getWidth(), turtleTexture.getHeight());

        oceanTexture = new Texture(Gdx.files.internal(Assets.OCEAN_JPG));
        oceanX = 0;
        oceanY = 0;

        starfishTexture = new Texture(Gdx.files.internal(Assets.STARFISH_PNG));
        starfishX = 380;
        starfishY = 380;
        starfishRectangle = new Rectangle(starfishX, starfishY, starfishTexture.getWidth(), starfishTexture.getHeight());

        winTexture = new Texture(Gdx.files.internal(Assets.WIN_PNG));
        winX = 100;
        winY = 180;

        win = false;
    }

    public Texture getTurtleTexture() {
        return turtleTexture;
    }

    public Rectangle getTurtleRectangle() {
        return turtleRectangle;
    }

    public Texture getOceanTexture() {
        return oceanTexture;
    }

    public Texture getStarfishTexture() {
        return starfishTexture;
    }

    public Rectangle getStarfishRectangle() {
        return starfishRectangle;
    }

    public Texture getWinTexture() {
        return winTexture;
    }
}
