package com.starfishcollector.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.starfishcollector.game.actors.ActorBeta;
import com.starfishcollector.game.actors.Turtle;
import com.starfishcollector.game.fixtures.GdxTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class StarfishCollectorTests {
    public static final String CORE_ASSETS = "../core/assets/";

    private StarfishCollector starfishCollectorGame;
    private static Gdx mockGdx;

    private Input input;
    private Stage mainStage;

    private final float initialTurtleX = 20.0f;
    private final float initialTurtleY = 20.0f;
    private final float initialStarfishX = 380f;
    private final float initialStarfishY = 380f;
    private final float initialWinX = 180f;
    private final float initialWinY = 180f;
    private final float initialOceanX = 0f;
    private final float initialOceanY = 0f;
    private final float deltaPrecision = 0.0f;

    @BeforeClass
    public static void beforeAll() {
        mockGdx = mock(Gdx.class);
    }

    @Before
    public void beforeEach() {
        starfishCollectorGame = Mockito.spy(new StarfishCollector());
        mainStage = mock(Stage.class);
        input = mock(Input.class);
        Gdx.input = input;
    }

    private void setupForRender() {
        starfishCollectorGame.initialize(mainStage);
        starfishCollectorGame.render();
    }

    @AfterClass
    public static void cleanup() {
        mockGdx = null;
    }

    @Test
    public void oceanAsset_exists() {
        FileHandle oceanAsset = Gdx.files.internal(CORE_ASSETS + Assets.OCEAN_JPG);
        assertTrue("Ocean asset does not exist at " + CORE_ASSETS, oceanAsset.exists());
    }

    @Test
    public void starfishAsset_exists() {
        FileHandle starfishAsset = Gdx.files.internal(CORE_ASSETS + Assets.STARFISH_PNG);
        assertTrue("Starfish asset does not exist at " + CORE_ASSETS, starfishAsset.exists());
    }

    @Test
    public void winAsset_exists() {
        FileHandle starfishAsset = Gdx.files.internal(CORE_ASSETS + Assets.WIN_PNG);
        assertTrue("Win asset does not exist at " + CORE_ASSETS, starfishAsset.exists());
    }

    @Test
    public void initialize_createsOcean() {
        starfishCollectorGame.initialize(mainStage);
        assertNotNull(starfishCollectorGame.getOcean());
    }

    @Test
    public void initialize_createsStarfish() {
        starfishCollectorGame.initialize(mainStage);
        assertNotNull(starfishCollectorGame.getStarfish());
    }

    @Test
    public void initialize_createsTurtle() {
        starfishCollectorGame.initialize(mainStage);
        assertNotNull(starfishCollectorGame.getTurtle());
    }

    @Test
    public void initialize_createsWinMessage() {
        starfishCollectorGame.initialize(mainStage);
        assertNotNull(starfishCollectorGame.getWinMessage());
    }

    @Test
    public void initialize_setOceanTexture() {
        starfishCollectorGame.initialize(mainStage);
        assertTrue(starfishCollectorGame
                .getOcean()
                .getTextureRegion()
                .getTexture()
                .toString().endsWith(Assets.OCEAN_JPG));
    }

    @Test
    public void initialize_setStarfishTexture() {
        starfishCollectorGame.initialize(mainStage);
        assertTrue(starfishCollectorGame
                .getStarfish()
                .getTextureRegion()
                .getTexture()
                .toString().endsWith(Assets.STARFISH_PNG));
    }

    @Test
    public void initialize_setTurtleTexture() {
        starfishCollectorGame.initialize(mainStage);
        assertTrue(starfishCollectorGame.getTurtle()
                .getTextureRegion()
                .getTexture()
                .toString()
                .endsWith(Assets.TURTLE_1_PNG));
    }

    @Test
    public void initialize_setWinMessageTexture() {
        starfishCollectorGame.initialize(mainStage);
        assertTrue(starfishCollectorGame.getWinMessage()
                .getTextureRegion()
                .getTexture()
                .toString()
                .endsWith(Assets.WIN_PNG));
    }

    @Test
    public void initialize_putsOceanOnInitialPosition() {
        starfishCollectorGame.initialize(mainStage);
        ActorBeta ocean = starfishCollectorGame.getOcean();
        assertEquals(initialOceanX, ocean.getX(), deltaPrecision);
        assertEquals(initialOceanY, ocean.getY(), deltaPrecision);
    }

    @Test
    public void initialize_putsStarfishOnInitialPosition() {
        starfishCollectorGame.initialize(mainStage);
        ActorBeta starfish = starfishCollectorGame.getStarfish();
        assertEquals(initialStarfishX, starfish.getX(), deltaPrecision);
        assertEquals(initialStarfishY, starfish.getY(), deltaPrecision);
    }

    @Test
    public void initialize_putsTurtleOnInitialPosition() {
        starfishCollectorGame.initialize(mainStage);
        Turtle turtle = starfishCollectorGame.getTurtle();
        assertEquals(initialTurtleX, turtle.getX(), deltaPrecision);
        assertEquals(initialTurtleY, turtle.getY(), deltaPrecision);
    }

    @Test
    public void initialize_putsWinMessageOnInitialPosition() {
        starfishCollectorGame.initialize(mainStage);
        ActorBeta winMessage = starfishCollectorGame.getWinMessage();
        assertEquals(initialWinX, winMessage.getX(), deltaPrecision);
        assertEquals(initialWinY, winMessage.getY(), deltaPrecision);
    }

    @Test
    public void initialize_addOceanToMainStage() {
        starfishCollectorGame.initialize(mainStage);
        verify(mainStage).addActor(starfishCollectorGame.getOcean());
    }

    @Test
    public void initialize_addStarfishToMainStage() {
        starfishCollectorGame.initialize(mainStage);
        verify(mainStage).addActor(starfishCollectorGame.getStarfish());
    }

    @Test
    public void initialize_addTurtleToMainStage() {
        starfishCollectorGame.initialize(mainStage);
        verify(mainStage).addActor(starfishCollectorGame.getTurtle());
    }

    @Test
    public void initialize_addWinMessageToMainStage() {
        starfishCollectorGame.initialize(mainStage);
        verify(mainStage).addActor(starfishCollectorGame.getWinMessage());
    }

    @Test
    public void initialize_hidesWinMessage() {
        starfishCollectorGame.initialize(mainStage);
        assertFalse(starfishCollectorGame.getWinMessage().isVisible());
    }

    @Test
    public void initialize_ensureCorrectOrder() {
        starfishCollectorGame.initialize(mainStage);
        InOrder verifyOrder = inOrder(mainStage);

        verifyOrder.verify(mainStage).addActor(starfishCollectorGame.getOcean());
        verifyOrder.verify(mainStage).addActor(starfishCollectorGame.getStarfish());
        verifyOrder.verify(mainStage).addActor(starfishCollectorGame.getTurtle());
        verifyOrder.verify(mainStage).addActor(starfishCollectorGame.getWinMessage());
        verifyOrder.verifyNoMoreInteractions();
    }

    @Test
    public void whenUpdate_starfishOverlapsTurtle_shouldRemoveStarfish() {
        Turtle turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal(CORE_ASSETS + Assets.TURTLE_1_PNG)));
        turtle.setPosition(10, 10);

        ActorBeta starfish = Mockito.spy(new  ActorBeta());
        starfish.setTexture(new Texture(Gdx.files.internal(CORE_ASSETS + Assets.STARFISH_PNG)));
        starfish.setPosition(10, 10);
        when(starfish.remove()).thenReturn(true);

        when(starfishCollectorGame.getTurtle()).thenReturn(turtle);
        when(starfishCollectorGame.getStarfish()).thenReturn(starfish);

        setupForRender();
        starfishCollectorGame.update(1f);

        verify(starfish, times(1)).remove();
    }

    @Test
    public void whenUpdate_playerWins_shouldDrawWinMessage() {
        Turtle turtle = new Turtle();
        turtle.setTexture(new Texture(Gdx.files.internal(CORE_ASSETS + Assets.TURTLE_1_PNG)));
        turtle.setPosition(10, 10);

        ActorBeta starfish = Mockito.spy(new  ActorBeta());
        starfish.setTexture(new Texture(Gdx.files.internal(CORE_ASSETS + Assets.STARFISH_PNG)));
        starfish.setPosition(10, 10);
        when(starfish.remove()).thenReturn(true);

        when(starfishCollectorGame.getTurtle()).thenReturn(turtle);
        when(starfishCollectorGame.getStarfish()).thenReturn(starfish);

        setupForRender();
        starfishCollectorGame.update(1f);

        assertTrue(starfishCollectorGame.getWinMessage().isVisible());
    }
}
