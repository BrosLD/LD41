package ld.bros.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.State;
import ld.bros.game.platform.EntityManager;
import ld.bros.game.platform.FinishFlag;
import ld.bros.game.platform.LevelFactory;
import ld.bros.game.platform.UI;
import ld.bros.game.platform.player.Player;

public class PlatformState extends State<MainState> {

    private EntityManager entities;
    private Player player;

    private OrthographicCamera camera;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private UI ui;

    public PlatformState(MainState manager) {
        super(manager);

        map = LevelFactory.getNextLevel();
        renderer = new OrthogonalTiledMapRenderer(map);

        entities = new EntityManager();
        entities.setMap(map);

        // get object layer for spawn points
        MapLayer spawnPoints = map.getLayers().get("entity");

        // create entities
        MapObjects objects = spawnPoints.getObjects();
        for(MapObject o : objects) {
            RectangleMapObject curr = (RectangleMapObject) o;

            if ("spawn".equals(curr.getName())) {
                player = new Player(entities);
                player.pos.set(curr.getRectangle().x, curr.getRectangle().y);
            }

            if("finish".equals(curr.getName())) {
                FinishFlag flag = new FinishFlag(entities);
                flag.pos.set(curr.getRectangle().x, curr.getRectangle().y);
            }
        }

        camera = new OrthographicCamera(LudumDare41.WIDTH, LudumDare41.HEIGHT);
        camera.setToOrtho(false);

        updateCamera();

        ui = new UI(manager);
    }

    @Override
    public void enter() {
        super.enter();

        // get current player config from MainState
        player.setConfig(manager.getPlayerConfig());
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        entities.update(delta);

        
        updateCamera();
//        camera.position.x = (LudumDare41.WIDTH/2f);
//        camera.position.y = (LudumDare41.HEIGHT/2f);x
//        camera.update();

        ui.update(delta);
    }

    private void updateCamera() {
        camera.position.x = player.pos.x;
        camera.position.y = player.pos.y;

        int mapPixelWidth = entities.getMapWidth();
        int mapPixelHeight = entities.getMapHeight();

        // bound camera to map bounds
        // horizontal
        if(camera.position.x - LudumDare41.WIDTH/2f < 0) {
            camera.position.x = LudumDare41.WIDTH/2f;
        } else if(camera.position.x + LudumDare41.WIDTH/2f > mapPixelWidth) {
            camera.position.x = mapPixelWidth - LudumDare41.WIDTH/2f;
        }
        // vertical
        if(camera.position.y - LudumDare41.HEIGHT/2f < 0) {
            camera.position.y = LudumDare41.HEIGHT/2f;
        } else if(camera.position.y + LudumDare41.HEIGHT/2f > mapPixelHeight) {
            camera.position.y = mapPixelHeight - LudumDare41.HEIGHT/2f;
        }

        camera.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // render map
        renderer.setView(camera);
        renderer.render();
        batch.end();

        batch.begin();

        // render entities
        entities.render(batch);

        batch.end();
        batch.begin();

        ui.render(batch);
    }
}
