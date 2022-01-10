package com.starfishcollector.game.flows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.starfishcollector.game.fixtures.GdxTestRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class GameFlowTests {
    private GameFlow gameFlow;
    private Stage mainStage;
    private static Graphics graphics;
    private static Gdx mockGdx;

    @BeforeClass
    public static void beforeAll() {
        graphics = mock(Gdx.graphics.getClass());
        Gdx.graphics = graphics;

        mockGdx = mock(Gdx.class);
    }

    @Before
    public void beforeEach() {
        gameFlow = Mockito.spy(new GameFlowMock());
        mainStage = mock(Stage.class);
    }

    @Test
    public void initialize_createsMainStage() {
        gameFlow.initialize(mainStage);
        assertNotNull(gameFlow.getMainStage());
    }

    @Test
    public void render_ensuredUseOfDeltaTime() {
        verify(graphics, atLeastOnce()).getDeltaTime();
    }

    @Test
    public void render_whenStageAct_passDeltaTimeParameter() {
        float delta = 11f;
        prepareGraphicsDeltaTime(delta);

        setupForRender();

        verify(mainStage).act(delta);
    }

    @Test
    public void render_whenUpdate_passDeltaTimeParameter() {
        float delta = 88f;
        prepareGraphicsDeltaTime(delta);
        setupForRender();

        verify(gameFlow).update(delta);
    }

    @Test
    public void whenRender_afterStageAct_callForUpdate() {
        float delta = 88f;

        prepareGraphicsDeltaTime(delta);

        setupForRender();

        InOrder orderVerifier = inOrder(mainStage, gameFlow);

        orderVerifier.verify(mainStage).act(delta);
        orderVerifier.verify(gameFlow).update(delta);
    }

    @Test
    public void render_whenAfterUpdate_clearScreen() {
        float delta = 99f;
        prepareGraphicsDeltaTime(delta);

        setupForRender();
        InOrder orderVerifier = inOrder(mockGdx.gl, gameFlow);

        orderVerifier.verify(gameFlow).update(delta);
        orderVerifier.verify(mockGdx.gl).glClearColor(0, 0, 0, 1);
        orderVerifier.verify(mockGdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Test
    public void render_mainStageDraw_afterClearScreen() {
        float delta = 66f;
        prepareGraphicsDeltaTime(delta);

        setupForRender();
        InOrder orderVerifier = inOrder(mockGdx.gl, mainStage);

        orderVerifier.verify(mockGdx.gl).glClearColor(0, 0, 0, 1);
        orderVerifier.verify(mockGdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        orderVerifier.verify(mainStage).draw();
    }

    private void setupForRender() {
        gameFlow.initialize(mainStage);
        gameFlow.render();
    }

    private void prepareGraphicsDeltaTime(float delta) {
        when(graphics.getDeltaTime()).thenReturn(delta);
    }

    class GameFlowMock extends GameFlow {
        @Override
        public void initialize(Stage mainStage) {
            this.mainStage = mainStage;
        }

        @Override
        public void update(float delta) {}
    }
}
