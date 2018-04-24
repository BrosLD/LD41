package ld.bros.game.platform.player.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Run extends PlayerState {

    public Run(Player manager) {
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

        // QUICK HACK...
        update(Gdx.graphics.getDeltaTime());

        // create smoke effect
        manager.makeSmoke();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // apply gravity
        manager.vel.y = manager.GRAVITY;

        // handle user input
        if(Locator.controls().left())
            manager.vel.x = -manager.getSpeed();
        else if(Locator.controls().right())
            manager.vel.x = manager.getSpeed();
        else {
            // switch to Idle-state
            manager.set(new Idle(manager));
        }

        // check if we're jumping
        if(Locator.controls().jumpTapped()
                && manager.getConfig().canJump()) {
            manager.set(new Jump(manager));
        }

        // apply velocity
        manager.move();

        // if not on ground -> falling
        if(!manager.isOnGround()) {
            manager.set(new Fall(manager));
        }

        // if we hit wall -> wall climbing (if possible)
        if(manager.isHorizontal()
                && manager.getConfig().canWallClimb()) {
            manager.set(new WallClimb(manager));
        }

        if(Locator.controls().dashTapped()
                && manager.getConfig().canDash()) {
            manager.set(new Dash(manager));
        }

        boolean prev = manager.facingRight;
        handleFacing();
        if(manager.facingRight != prev)
            manager.makeSmoke();
    }

    @Override
    public String toString() {
        return "Run";
    }
}
