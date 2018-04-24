package ld.bros.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.State;
import ld.bros.game.service.Locator;

public class TitleState extends State<GameStateManager> {

    private TextureRegion image;

    public TitleState(GameStateManager manager) {
        super(manager);

        image = Locator.res().region("title");
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            // switch to start state / selection
            manager.set(new StartState(manager));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.draw(image, 0, 0, LudumDare41.WIDTH, LudumDare41.HEIGHT);
    }
}
