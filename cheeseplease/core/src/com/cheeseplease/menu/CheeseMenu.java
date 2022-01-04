package com.cheeseplease.menu;

import static com.badlogic.gdx.Gdx.files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.cheeseplease.actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.cheeseplease.game.CheeseGameLevel;

public class CheeseMenu implements Screen {
    private Stage uiStage;
    private Game game;


    public CheeseMenu(Game game) {
        this.game = game;
        this.create();
    }

    private void create() {
        this.uiStage = new Stage();

        BaseActor background = new BaseActor();
        background.setTexture(new Texture(files.internal("tiles-menu.jpg")));
        uiStage.addActor(background);

        BaseActor titleText = new BaseActor();
        titleText.setTexture(new Texture(files.internal("cheese-please.png")));
        titleText.setPosition(20, 100);
        uiStage.addActor(titleText);

        BitmapFont font = new BitmapFont();
        String text = " Press S to start, M for main menu ";
        LabelStyle style = new LabelStyle(font, Color.YELLOW);
        Label instructions = new Label(text, style);
        instructions.setFontScale(2);
        instructions.setPosition(100, 50);

        instructions.addAction(
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1, 1, 0, 1), 0.5f),
                                Actions.delay(0.5f),
                                Actions.color(new Color(0.5f, 0.5f, 0, 1), 0.5f)
                        )
                )
        );
        uiStage.addActor(instructions);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.game.setScreen(new CheeseGameLevel(this.game));
        }

        this.uiStage.act(delta);

        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
