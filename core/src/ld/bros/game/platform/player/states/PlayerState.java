package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.platform.EntityState;
import ld.bros.game.platform.player.Player;
import ld.bros.game.utils.MathUtils;

public class PlayerState extends EntityState<Player> {

    protected Animation<TextureRegion> animation;
    protected float animationTimer;

    public PlayerState(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        animationTimer += delta;

        if(manager.dead) {
            manager.set(new Dead(manager));
        }
    }

    protected void handleFacing() {
        // check facing direction of player
        int direction = MathUtils.direction(manager.vel.x);
        if(direction == -1) {
            manager.facingRight = false;
        } else if(direction == 1) {
            manager.facingRight = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(animationTimer, true);
        if(manager.facingRight) {
            // flip image
            batch.draw(
                    frame,
                    manager.pos.x + frame.getRegionWidth(),
                    manager.pos.y,
                    -frame.getRegionWidth(),
                    frame.getRegionHeight()
            );
        } else {
            // draw regular
            batch.draw(
                    frame,
                    manager.pos.x,
                    manager.pos.y
            );
        }
    }
}
