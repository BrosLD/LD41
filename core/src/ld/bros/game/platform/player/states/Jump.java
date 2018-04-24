package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Jump extends PlayerState {

    private float jumpTimer;
    private float highTimer;
    private boolean reachedHigh;

    public Jump(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0f,
                Locator.res().atlas("jump").findRegions( "jump"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // handle user input
        if(Locator.controls().left())
            manager.vel.x = -manager.getSpeed();
        else if(Locator.controls().right())
            manager.vel.x = manager.getSpeed();
        else
            manager.vel.x = 0f;

        if(!reachedHigh) {
            if(Locator.controls().jump()) {
                // check if player can jump any higher/longer
                jumpTimer += delta;
                if(jumpTimer >= manager.MAX_JUMP_TIME) {
                    reachedHigh = true;
                    highTimer = 0f;
                }

                // apply jump speed
                manager.vel.y = manager.getJumpSpeed();
            } else {
                // jump button released - jump ends
                reachedHigh = true;
            }
        } else {
            // reached high - stay some time in midair (better feeling)
            manager.vel.y = 0f;

            highTimer += delta;
            if(highTimer >= manager.MAX_HIGH_TIME) {
                // set to Fall-state
                manager.set(new Fall(manager));
            }
        }

        manager.move();

        // if we hit wall -> wall climbing (if possible)
        if(manager.isHorizontal()
                && manager.getConfig().canWallClimb()) {
            manager.set(new WallClimb(manager));
        }

        if(Locator.controls().dashTapped()
                && manager.getConfig().canDash()) {
            manager.set(new Dash(manager));
        }

        handleFacing();
    }

    @Override
    public String toString() {
        return "Jump";
    }
}
