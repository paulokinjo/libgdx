package com.starfishcollector.game.flows;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameFlow extends Game {
    protected Stage mainStage;

    @Override
    public void create() {
        initialize(new Stage());
    }

    public abstract void initialize(Stage mainStage);

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        this.mainStage.act(delta);

        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.mainStage.draw();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public abstract void update(float delta);
}
