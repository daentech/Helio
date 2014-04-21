package uk.co.daentech.helio.level.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.daentech.helio.HelioGame;
import uk.co.daentech.helio.base.Entity;
import uk.co.daentech.helio.character.Helicopter;
import uk.co.daentech.helio.controllers.CollisionController;
import uk.co.daentech.helio.controllers.InputHandler;
import uk.co.daentech.helio.level.LevelManager;
import uk.co.daentech.helio.level.Levels;
import uk.co.daentech.helio.utils.InputDebug;

/**
 * Created by dangilbert on 15/04/2014.
 */
public class BaseGameScreen implements Screen {

    private int tileWidth = 16;
    private int tileHeight = 16;

    protected OrthographicCamera camera;
    protected float cameraShakeTime;
    protected final float cameraShakeLimit = 0.3f;
    private boolean isShakingCamera;
    private float cameraShakeViolence = 0.04f;


    protected HelioGame game;
    protected Helicopter character;
    protected InputDebug inputDebug;

    protected List<Entity> entities = new ArrayList<Entity>();

    // Scene background
    protected TiledMap map;
    protected TiledMapRenderer tiledMapRenderer;
    protected AssetManager assetManager;

    // Physics
    protected World world;
    protected Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();;

    private Vector2 unitVector = new Vector2(1,1);

    public BaseGameScreen() {
        // Get game instance
        game = HelioGame.getInstance();

        // Character
        character = new Helicopter();
        inputDebug = new InputDebug();

        // Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);

        entities.add(character);

        // Setup the input processor
        InputHandler.getInstance().setCamera(camera);
        Gdx.input.setInputProcessor(InputHandler.getInstance());

        assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        // Physics init
        // Don't let them sleep as we're moving them
        world = new World(Vector2.Zero, false);
        // Add each wall to the world
        for (Entity e : entities) {
            e.initBodyDef(world);
        }

        world.setContactListener(CollisionController.getInstance());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Entity entity : entities) {
            entity.update(delta);
        }

        camera.position.set(character.position.x + 5, character.position.y + 50, 0);
        if (isShakingCamera) {
            cameraShakeTime += delta;
            if((cameraShakeTime * 100) % 10 > 8) {
                camera.translate(cameraShakeViolence, -cameraShakeViolence);
            } else {
                camera.translate(-cameraShakeViolence, cameraShakeViolence);
            }

            if (cameraShakeTime > cameraShakeLimit) {
                isShakingCamera = false;
            }
        } else {
            cameraShakeTime = 0f;
        }
        camera.update();

        if (tiledMapRenderer != null) {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (Entity entity : entities) {
            entity.render();
        }

        game.batch.end();
        //debugRenderer.render(world, camera.combined);

        world.step(delta, 1, 1);

        if(CollisionController.getInstance().hasCollided) {
            isShakingCamera = true;
            character.bounceBack();
            character.flipDirection();
        } else {

        }

    }

    public void setupWalls() {
        MapLayers layers = map.getLayers();
        MapLayer wallObjects = layers.get("WallObjects");

        // If we don't have a wallobjects layer, return
        if (wallObjects == null) return;

        for (MapObject o : wallObjects.getObjects()) {
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);

            FixtureDef fd = new FixtureDef();
            PolygonShape ps = new PolygonShape();
            if (o instanceof RectangleMapObject) {
                Rectangle r = ((RectangleMapObject) o).getRectangle();
                Vector2 center = new Vector2(
                        (r.getX() + r.getWidth()/2) / tileWidth,
                        (r.getY() + r.getHeight() / 2)/ tileHeight);
                ps.setAsBox(r.getWidth() / tileWidth / 2,
                        r.getHeight() / tileHeight / 2,
                        center,
                        0);
            } else if (o instanceof PolygonMapObject) {
                Polygon p = ((PolygonMapObject) o).getPolygon();
                p.setScale(0.0625f, 0.0625f);
                p.setPosition(p.getX()/tileWidth,
                        p.getY()/tileHeight);
                ps.set(p.getTransformedVertices());
            }

            fd.shape = ps;
            fd.isSensor = true;
            fd.density = 10.0f;

            Fixture f = body.createFixture(fd);
            body.setUserData("wall");
            f.setUserData("wall");

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
