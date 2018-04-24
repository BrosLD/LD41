package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Dash extends PlayerState {

    private static final float DASH_TIME = 0.2f;
    private static final float DASH_MUL = 5f;
    private float timer;

    public Dash(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.2f,
                Locator.res().atlas("run").findRegions( "run"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void enter() {
        super.enter();

        manager.makeSmoke();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(manager.hasDashed()) {
            // already dashed! return
            manager.set(new Fall(manager));
            return;
        }

        // dash ends, if wall was hit
        if(manager.isHorizontal()) {
            manager.set(new Fall(manager));
            return;
        }

        timer += delta;
        if(timer >= DASH_TIME) {
            timer = 0f;
            manager.setDashed(true);
            // dash done, switch to fall
            manager.set(new Fall(manager));
        }

        if(manager.facingRight)
            manager.vel.x = manager.getSpeed() * DASH_MUL;
        else
            manager.vel.x = -manager.getSpeed() * DASH_MUL;

        // only left right dashing
        manager.vel.y = 0f;

        manager.move();

        handleFacing();
    }

    @Override
    public String toString() {
        return "Dash";
    }
}
