package ld.bros.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.service.BasicEvent;
import ld.bros.game.service.GameResources;
import ld.bros.game.service.KeyboardControls;
import ld.bros.game.service.Locator;
import ld.bros.game.states.MainState;
import ld.bros.game.states.TitleState;
import ld.bros.game.utils.TextDisplayer;

public class LudumDare41 extends ApplicationAdapter {

	public static final String TITLE = "Ludum Dare 41";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int FPS = 60;

	private OrthographicCamera camera;
	private FitViewport viewport;

	private SpriteBatch batch;

	private GameStateManager gsm;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		Locator.setResources(new GameResources());
		Locator.res().load();

		Locator.setLog(new TextDisplayer());
		Locator.log().showMessages(false);

		Locator.setControls(new KeyboardControls());

		Locator.setEvent(new BasicEvent());

		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.setToOrtho(false);
		viewport = new FitViewport(WIDTH, HEIGHT, camera);

		gsm = new GameStateManager();
		gsm.setCamera(camera);

//		gsm.set(new MainState(gsm));
		gsm.set(new TitleState(gsm));
	}

	public void update(float delta) {
		Locator.log().print("FPS: " + Gdx.graphics.getFramesPerSecond());
		gsm.update(delta);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(Gdx.graphics.getDeltaTime());

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		gsm.render(batch);

		Locator.log().render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		Locator.res().dispose();
	}
}
