package com.cheeseplease.game;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChessePleaseGame extends ApplicationAdapter {
	private SpriteBatch spriteBatch;

	private Texture mouseyTexture;
	private float mouseyX;
	private float mouseyY;

	private Texture cheeseTexture;
	private float cheeseX;
	private float cheeseY;

	private Texture floorTexture;
	private Texture winMessage;

	private boolean win;

	@Override
	public void create () {
		this.spriteBatch = new SpriteBatch();

		this.mouseyTexture = new Texture(files.internal("mouse.png"));
		this.mouseyX = 20;
		this.mouseyY = 20;

		this.cheeseTexture = new Texture(files.internal("cheese.png"));
		this.cheeseX = 400;
		this.cheeseY = 300;

		this.floorTexture = new Texture(files.internal("tiles.jpg"));
		this.winMessage = new Texture(files.internal("you-win.png"));

		win = false;
	}

	@Override
	public void render () {
		if (input.isKeyPressed(Keys.LEFT)) {
			this.mouseyX--;
		} else if(input.isKeyPressed(Keys.RIGHT)) {
			this.mouseyX++;
		}

		if (input.isKeyPressed(Keys.UP)) {
			this.mouseyY++;
		} else if(input.isKeyPressed(Keys.DOWN)) {
			this.mouseyY--;
		}

		this.win = checkWin();

		Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.spriteBatch.begin();
		this.spriteBatch.draw( this.floorTexture, 0, 0);
		this.spriteBatch.draw(this.cheeseTexture, this.cheeseX, this.cheeseY);
		this.spriteBatch.draw(this.mouseyTexture, this.mouseyX, this.mouseyY);

		if (this.win) {
			this.spriteBatch.draw(this.winMessage, 170, 60);
		}
		this.spriteBatch.end();
	}

	private boolean checkWin() {
		return (this.mouseyX > this.cheeseX) &&
				(this.mouseyX + this.mouseyTexture.getWidth() < this.cheeseX + this.cheeseTexture.getWidth()) &&
				(this.mouseyY > this.cheeseY) &&
				(this.mouseyY + this.mouseyTexture.getHeight() < this.cheeseY + this.cheeseTexture.getHeight());
	}
	
	@Override
	public void dispose () {
		this.spriteBatch.dispose();
		this.mouseyTexture.dispose();
		this.cheeseTexture.dispose();
	}
}
