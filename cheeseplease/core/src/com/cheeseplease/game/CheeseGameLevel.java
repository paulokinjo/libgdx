package com.cheeseplease.game;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;
import java.security.Key;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.cheeseplease.actors.AnimatedActor;
import com.cheeseplease.actors.BaseActor;
import com.cheeseplease.menu.CheeseMenu;

public class CheeseGameLevel implements Screen {

    private Game game;
    private Stage mainStage;
    private Stage uiStage;

    private AnimatedActor mousey;
    private BaseActor cheese;
    private BaseActor floor;
    private BaseActor winText;

    private float timeElapsed;
    private Label timeLabel;

    final int mapWidth = 800;
    final int mapHeight = 800;

    final int viewWidth = 640;
    final int viewHeight = 480;

    public CheeseGameLevel(Game game) {
        this.game = game;
        this.create();
    }

    public void create() {
        this.mainStage = new Stage();
        this.uiStage = new Stage();

        this.floor = new BaseActor();
        this.floor.setTexture(new Texture(files.internal("tiles-800-800.jpg")));
        this.floor.setPosition(0, 0);
        this.mainStage.addActor(this.floor);

        this.cheese = new BaseActor();
        this.cheese.setTexture(new Texture(files.internal("cheese.png")));
        this.cheese.setPosition(400, 300);
        this.cheese.setOrigin(this.cheese.getWidth() / 2, this.cheese.getHeight() / 2);
        this.mainStage.addActor(this.cheese);

        TextureRegion[] frames = new TextureRegion[4];
        for (int n = 0; n < 4; n++) {
            String fileName = "mouse" + n + ".png";
            Texture texture = new Texture(files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            frames[n] = new TextureRegion(texture);
        }
        Array<TextureRegion> framesArray = new Array<>(frames);
        Animation animation = new Animation(0.1f, framesArray, Animation.PlayMode.LOOP_PINGPONG);
        this.mousey = new AnimatedActor();
        this.mousey.setAnimation(animation);
        this.mousey.setOrigin(this.mousey.getWidth() / 2, this.mousey.getHeight() / 2);
        this.mousey.setPosition(20, 20);
        this.mainStage.addActor(this.mousey);

        this.winText = new BaseActor();
        this.winText.setTexture(new Texture(files.internal("you-win.png")));
        this.winText.setPosition(170, 60);
        this.winText.setVisible(false);
        this.winText.setColor(new Color(0, 0, 0, 0));
        this.uiStage.addActor(this.winText);


        BitmapFont font = new BitmapFont();
        String text = "Time: 0";
        Label.LabelStyle style = new Label.LabelStyle(font, Color.NAVY);

        timeLabel = new Label(text, style);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(500, 440);
        this.uiStage.addActor(timeLabel);

        timeElapsed = 0;
    }


    @Override
    public void render(float delta) {
        this.mousey.setVelocityX(0);
        this.mousey.setVelocityY(0);
        int speed = 100;

        if (input.isKeyPressed(Keys.LEFT)) {
            this.mousey.addVelocityX(-speed);
        } else if (input.isKeyPressed(Keys.RIGHT)) {
            this.mousey.addVelocityX(speed);
        }

        if (input.isKeyPressed(Keys.UP)) {
            this.mousey.addVelocityY(100);
        } else if (input.isKeyPressed(Keys.DOWN)) {
            this.mousey.addVelocityY(-100);
        }

        if (this.mousey.getX() < 0)
            this.mousey.setX(0);

        this.mousey.setX(MathUtils.clamp(this.mousey.getX(), 0, this.mapWidth - this.mousey.getWidth()));
        this.mousey.setY(MathUtils.clamp(this.mousey.getY(), 0, this.mapHeight - this.mousey.getHeight()));

        boolean win = this.winText.isVisible();
        if (!win) {

            if (checkWin()) {
                handleWinText();
                handleCheese();
            }

            timeElapsed += delta;
            timeLabel.setText("Time: " + (int) timeElapsed);
        }

        if (input.isKeyPressed(Keys.M)) {
            this.game.setScreen(new CheeseMenu(this.game));
        }


        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        adjustCamera();
        this.mainStage.act(delta);
        this.mainStage.draw();

        this.uiStage.act(delta);
        this.uiStage.draw();
    }

    private void handleCheese() {
        Action spinShrinkFadeOut = Actions.parallel(
                Actions.alpha(1),
                Actions.rotateBy(360, 1),
                Actions.scaleTo(0, 0, 1),
                Actions.fadeOut(1)
        );
        this.cheese.addAction(spinShrinkFadeOut);
    }

    private boolean checkWin() {
        Rectangle cheeseRectangle = this.cheese.getBoundingRectangle();
        Rectangle mouseyRectangle = this.mousey.getBoundingRectangle();

        return cheeseRectangle.contains(mouseyRectangle);
    }

    private void adjustCamera() {
        Camera camera = this.mainStage.getCamera();

        camera.position.set(this.mousey.getX() + this.mousey.getOriginX(),
                this.mousey.getY() + this.mousey.getOriginY(), 0);

        camera.position.x = MathUtils.clamp(camera.position.x, this.viewWidth / 2, this.mapWidth - this.viewWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, this.viewHeight / 2, this.mapHeight - this.viewHeight / 2);

        camera.update();
    }

    @Override
    public void dispose() {
        this.cheese = null;
        this.mousey = null;
        this.floor = null;
        this.winText = null;
        this.mainStage.dispose();
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
    public void show() {

    }

    @Override
    public void hide() {

    }

    private void handleWinText() {
        Action fadeInColorCycleForever = Actions.sequence(
                Actions.alpha(0),
                Actions.show(),
                Actions.fadeIn(2),
                Actions.forever(
                        Actions.sequence(
                                Actions.color(new Color(1, 0, 0, 1), 1),
                                Actions.color(new Color(0, 0, 1, 1), 1)
                        )
                )

        );
        this.winText.addAction(fadeInColorCycleForever);
        this.winText.setVisible(true);
    }
}
