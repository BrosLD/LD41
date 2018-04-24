package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Fall extends PlayerState {

    public Fall(Player manager) {
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

        manager.vel.y = manager.GRAVITY;
        manager.move();

        // stop falling if we're on ground
        if(manager.isOnGround()) {
            manager.set(new Idle(manager));
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

        handleFacing();
    }

    @Override
    public String toString() {
        return "Fall";
    }
}
