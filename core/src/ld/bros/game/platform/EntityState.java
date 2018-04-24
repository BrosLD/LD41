package ld.bros.game.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;

// States for specific entity T
public abstract class EntityState<T extends StateManager> extends State<T> {

    public EntityState(T manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
}
