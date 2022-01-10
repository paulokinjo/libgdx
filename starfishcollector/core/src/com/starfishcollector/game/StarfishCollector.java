package com.starfishcollector.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.starfishcollector.game.actors.ActorBeta;
import com.starfishcollector.game.actors.Turtle;
import com.starfishcollector.game.flows.GameFlow;

public class StarfishCollector extends GameFlow {
    private Turtle turtle;
    private ActorBeta ocean;
    private ActorBeta starfish;
    private ActorBeta winMessage;

    private boolean win;

    @Override
    public void create() {
        this.initialize(new Stage());
    }

    @Override
    public void update(float delta) {
        this.getMainStage().act(delta);

        Turtle turtle = this.getTurtle();
        ActorBeta starfish = this.getStarfish();

        if(!this.win && turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
            this.win = true;
        }
    }

    @Override
    public void render() {
        update(1 / 60f);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.getMainStage().draw();
    }

    @Override
    public void initialize(Stage stage) {
        mainStage = stage;

        ocean = new ActorBeta();
        ocean.setTexture(new Texture(Gdx.files.internal(Assets.OCEAN_JPG)));
        mainStage.addActor(ocean);

        starfish = new ActorBeta();
        starfish.setTexture(new Texture(Gdx.files.internal(Assets.STARFISH_PNG)));
        starfish.setPosition(380, 380);
        mainStage.addActor(starfish);

        turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal(Assets.TURTLE_1_PNG)));
        turtle.setPosition(20, 20);
        mainStage.addActor(turtle);

        winMessage = new ActorBeta();
        winMessage.setTexture(new Texture(Gdx.files.internal(Assets.WIN_PNG)));
        winMessage.setPosition(180, 180);
        winMessage.setVisible(false);
        mainStage.addActor(winMessage);

        win = false;
    }

    public ActorBeta getOcean() {
        return ocean;
    }

    public ActorBeta getStarfish() {
        return starfish;
    }

    public ActorBeta getWinMessage() {
        return winMessage;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public Stage getMainStage() {
        return this.mainStage;
    }
}
