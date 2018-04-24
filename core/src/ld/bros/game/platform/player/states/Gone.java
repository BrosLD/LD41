package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.platform.player.Player;

public class Gone extends PlayerState {

    public Gone(Player manager) {
        super(manager);
    }

    @Override
    public void enter() {
        manager.removeFromList();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public String toString() {
        return "Gone";
    }
}
