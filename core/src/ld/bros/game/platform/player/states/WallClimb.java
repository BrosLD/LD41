package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class WallClimb extends PlayerState {

    public WallClimb(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.2f,
                Locator.res().atlas("wall").findRegions( "wall"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(!manager.isHorizontal()) {
            // not on wall anymore
            manager.set(new Fall(manager));
            return;
        }

        // handle up down / movement
        if(Locator.controls().up()) {
            manager.vel.y = manager.getSpeed();
        } else if(Locator.controls().down()) {
            manager.vel.y = -manager.getSpeed();
        } else {
            manager.vel.y = 0f;
        }

        // handle left right movement
        if(Locator.controls().left())
            manager.vel.x = -manager.getSpeed();
        else if(Locator.controls().right())
            manager.vel.x = manager.getSpeed();
        else
            manager.vel.x = 0f;


        manager.move();

        handleFacing();
    }

    @Override
    public String toString() {
        return "WallClimb";
    }
}
