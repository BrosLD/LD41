package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Idle extends PlayerState {
    public Idle(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.2f,
                Locator.res().atlas("idle").findRegions( "idle"),
                Animation.PlayMode.LOOP
        );

        manager.setDashed(false);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // apply gravity
        manager.vel.y = manager.GRAVITY;
        manager.vel.x = 0f;

        // left / right
        if(Locator.controls().left() || Locator.controls().right()) {
            manager.set(new Run(manager));
        }

        // check jump IF POSSIBLE
        if(Locator.controls().jumpTapped()
                && manager.getConfig().canJump()) {
            manager.set(new Jump(manager));
        }

        // if not on ground -> falling
        if(!manager.isOnGround()) {
            manager.set(new Fall(manager));
        }

        if(Locator.controls().dashTapped()
                && manager.getConfig().canDash()) {
            manager.set(new Dash(manager));
        }

        handleFacing();
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
