import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.starfishcollector.game.Assets;
import com.starfishcollector.game.StarfishCollectorGame;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class StarfishCollectorTests {
    public static final String CORE_ASSETS = "../core/assets/";

    private static StarfishCollectorGame starfishCollectorGame;

    private static SpriteBatch spriteBatch;
    private static Gdx mockGdx;

    private static Input input;

    private final float initialTurtleX = 20.0f;
    private final float initialTurtleY = 20.0f;
    private final float initialStarfishX = 380f;
    private final float initialStarfishY = 380f;
    private final float initialWinX = 100f;
    private final float initialWinY = 180f;
    private final float  initialOceanX = 0f;
    private final float  initialOceanY = 0f;
    private final float deltaPrecision = 0.0f;

    @BeforeClass
    public static void beforeAll() {
        mockGdx = mock(Gdx.class);
        starfishCollectorGame = Mockito.spy(new StarfishCollectorGame());
        spriteBatch = mock(SpriteBatch.class);
    }

    @Before
    public void beforeEach() {
        input = mock(Input.class);
        Gdx.input = input;
        starfishCollectorGame.initialize(spriteBatch);
        starfishCollectorGame.render();
    }

    @AfterClass
    public static void cleanup() {
        mockGdx = null;
        starfishCollectorGame.dispose();
        spriteBatch = null;
        input = null;
    }

    @Test
    public void render_clearScreen() {
        InOrder orderVerifier = inOrder(mockGdx.gl, spriteBatch);

        orderVerifier.verify(mockGdx.gl).glClearColor(0, 0, 0, 1);
        orderVerifier.verify(mockGdx.gl).glClear(GL20.GL_COLOR_BUFFER_BIT);
        orderVerifier.verify(spriteBatch).begin();

    }

    @Test
    public void turtleAsset_exists() {
        FileHandle turtleAsset = Gdx.files.internal(CORE_ASSETS + Assets.TURTLE_1_PNG);
        assertTrue("Turtle asset does not exist at " + CORE_ASSETS, turtleAsset.exists());
    }

    @Test
    public void initialize_createsTurtleTexture() {
        assertNotNull(starfishCollectorGame.getTurtleTexture());
    }

    @Test
    public void initialize_setTurtleRectangle_returnsTurtlePositionAndTurtleTextureDimensions() {
        Rectangle turtleRectangle = starfishCollectorGame.getTurtleRectangle();
        Texture turtle = starfishCollectorGame.getTurtleTexture();

        assertEquals(turtleRectangle.x, initialTurtleX, deltaPrecision);
        assertEquals(turtleRectangle.y, initialTurtleY, deltaPrecision);
        assertEquals(turtleRectangle.width, turtle.getWidth(), deltaPrecision);
        assertEquals(turtleRectangle.height, turtle.getHeight(), deltaPrecision);
    }

    @Test
    public void render_drawTurtle() {
        InOrder orderVerifier = inOrder(spriteBatch);
        orderVerifier.verify(spriteBatch).begin();
        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY, orderVerifier);
        orderVerifier.verify(spriteBatch).end();
    }

    @Test
    public void render_inputLeft_moveTurtle1pixelLeft() {
        prepareInputKeyCondition(Input.Keys.LEFT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition((initialTurtleX - 1), initialTurtleY);
    }

    @Test
    public void render_inputRight_moveTurtle1pixelRight() {
        prepareInputKeyCondition(Input.Keys.RIGHT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition((initialTurtleX + 1), initialTurtleY);
    }

    @Test
    public void render_inputUp_moveTurtle1pixelUp() {
        prepareInputKeyCondition(Input.Keys.UP);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY + 1);
    }

    @Test
    public void render_inputDown_moveTurtle1pixelDown() {
        prepareInputKeyCondition(Input.Keys.DOWN);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY - 1);
    }

    @Test
    public void render_inputRightDownDiagonal_moveTurtle1pixelDownAnd1PixelRight() {
        prepareInputKeyCondition(Input.Keys.DOWN);
        prepareInputKeyCondition(Input.Keys.RIGHT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX + 1, initialTurtleY - 1);
    }

    @Test
    public void render_inputDownLeftDiagonal_moveTurtle1pixelDownAnd1PixelLeft() {
        prepareInputKeyCondition(Input.Keys.DOWN);
        prepareInputKeyCondition(Input.Keys.LEFT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX - 1, initialTurtleY - 1);
    }

    @Test
    public void render_inputLeftUpDiagonal_moveTurtle1pixelUpAnd1PixelLeft() {
        prepareInputKeyCondition(Input.Keys.UP);
        prepareInputKeyCondition(Input.Keys.LEFT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX - 1, initialTurtleY + 1);
    }

    @Test
    public void render_inputRightUpDiagonal_moveTurtle1pixelUpAnd1PixelRight() {
        prepareInputKeyCondition(Input.Keys.UP);
        prepareInputKeyCondition(Input.Keys.RIGHT);

        starfishCollectorGame.render();

        verifyTurtleDrawPosition(initialTurtleX + 1, initialTurtleY + 1);
    }

    @Test
    public void render_updateTurtleRectangle_setToNewPositionAfterMoving() {
        prepareInputKeyCondition(Input.Keys.UP);
        prepareInputKeyCondition(Input.Keys.RIGHT);

        starfishCollectorGame.render();

        Rectangle turtleRectangle = starfishCollectorGame.getTurtleRectangle();
        Texture turtle = starfishCollectorGame.getTurtleTexture();

        assertEquals(turtleRectangle.x, initialTurtleX + 1, deltaPrecision);
        assertEquals(turtleRectangle.y, initialTurtleY + 1, deltaPrecision);
        assertEquals(turtleRectangle.width, turtle.getWidth(), deltaPrecision);
        assertEquals(turtleRectangle.height, turtle.getHeight(), deltaPrecision);
    }

    @Test
    public void oceanAsset_exists() {
        FileHandle oceanAsset = Gdx.files.internal(CORE_ASSETS + Assets.OCEAN_JPG);
        assertTrue("Ocean asset does not exist at " + CORE_ASSETS,oceanAsset.exists());
    }

    @Test
    public void initialize_createsOceanTexture() {
        assertNotNull(starfishCollectorGame.getOceanTexture());
    }

    @Test
    public void render_oceanTextureDrawOrder_firstToBeDraw() {
        Texture ocean = starfishCollectorGame.getOceanTexture();
        Texture starfish = starfishCollectorGame.getStarfishTexture();
        InOrder orderVerifier = inOrder(spriteBatch);

        orderVerifier.verify(spriteBatch).begin();
        orderVerifier.verify(spriteBatch).draw(ocean, initialOceanX,initialOceanY);
        orderVerifier.verify(spriteBatch).draw(starfish, initialStarfishX, initialStarfishY);
    }

    @Test
    public void starfishAsset_exists() {
        FileHandle starfishAsset = Gdx.files.internal(CORE_ASSETS + Assets.STARFISH_PNG);
        assertTrue("Starfish asset does not exist at " + CORE_ASSETS, starfishAsset.exists());
    }

    @Test
    public void initialize_createsStarfishTexture() {
        assertNotNull(starfishCollectorGame.getStarfishTexture());
    }

    @Test
    public void initialize_setStarfishRectangle_onInitialPosition() {
        Texture starfish = starfishCollectorGame.getStarfishTexture();
        Rectangle starfishRectangle = starfishCollectorGame.getStarfishRectangle();

        assertEquals(starfishRectangle.x, initialStarfishX, deltaPrecision);
        assertEquals(starfishRectangle.y, initialStarfishY, deltaPrecision);
        assertEquals(starfishRectangle.getWidth(), starfish.getWidth(), deltaPrecision);
        assertEquals(starfishRectangle.getHeight(), starfish.getHeight(), deltaPrecision);
    }

    @Test
    public void render_starfishTextureDrawOrder_firstToBeDraw() {
        Texture ocean = starfishCollectorGame.getOceanTexture();
        Texture starfish = starfishCollectorGame.getStarfishTexture();
        InOrder orderVerifier = inOrder(spriteBatch);

        orderVerifier.verify(spriteBatch).draw(ocean, 0,0);
        orderVerifier.verify(spriteBatch).draw(starfish, initialStarfishX, initialStarfishY);
        verifyTurtleDrawPosition(initialTurtleX, initialTurtleY, orderVerifier);
    }

    @Test
    public void winAsset_exists() {
        FileHandle starfishAsset = Gdx.files.internal(CORE_ASSETS + Assets.WIN_PNG);
        assertTrue("Win asset does not exist at " + CORE_ASSETS, starfishAsset.exists());
    }

    @Test
    public void initialize_createsWinTexture() {
        assertNotNull(starfishCollectorGame.getWinTexture());
    }

    @Test
    public void whenPlayerWins_renderDraw_shouldDrawOnlyWinMessage() {
        // makes player go to same position as starfish
        when(input.isKeyPressed(Input.Keys.UP)).thenReturn(true);
        while(starfishCollectorGame.getTurtleRectangle().y < 380) {
            starfishCollectorGame.render();
        }

        when(input.isKeyPressed(Input.Keys.UP)).thenReturn(false);
        when(input.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);
        while(starfishCollectorGame.getTurtleRectangle().x < 380) {
            starfishCollectorGame.render();
        }

        Texture win = starfishCollectorGame.getWinTexture();
        Texture ocean = starfishCollectorGame.getOceanTexture();

        InOrder orderVerifier = inOrder(spriteBatch);

        orderVerifier.verify(spriteBatch).draw(ocean, initialOceanX, initialOceanY);
        orderVerifier.verify(spriteBatch).draw(win, initialWinX, initialWinY);
        orderVerifier.verify(spriteBatch).end();
    }

    private void prepareInputKeyCondition(int key) {
        when(input.isKeyPressed(key)).thenReturn(true);
    }

    private void verifyTurtleDrawPosition(float x, float y) {
        verifyTurtleDrawPosition(x, y, null);
    }

    private void verifyTurtleDrawPosition(float x, float y, InOrder orderVerifier) {
        Texture turtle = starfishCollectorGame.getTurtleTexture();

        if (orderVerifier == null) {
            verify(spriteBatch).draw(turtle, x, y);
        } else {
            orderVerifier.verify(spriteBatch).draw(turtle, x, y);
        }
    }
}
