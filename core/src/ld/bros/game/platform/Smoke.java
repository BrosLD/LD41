package ld.bros.game.platform;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.service.Locator;

public class Smoke extends Entity {

    private Animation<TextureRegion> animation;
    private float animationTimer;

    private boolean facingRight;

    public Smoke(EntityManager manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.1f,
                Locator.res().atlas("smoke").findRegions("smoke"),
                Animation.PlayMode.NORMAL
        );
        width = animation.getKeyFrame(animationTimer).getRegionWidth();
        height = animation.getKeyFrame(animationTimer).getRegionHeight();
    }

    @Override
    public void update(float delta) {
        animationTimer += delta;

        if(animation.isAnimationFinished(animationTimer)) {
            // animation done, remove effect
            manager.remove(this);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(animationTimer);
        if(facingRight) {
            // flip image
            batch.draw(
                    frame,
                    pos.x + frame.getRegionWidth(),
                    pos.y,
                    -frame.getRegionWidth(),
                    frame.getRegionHeight()
            );
        } else {
            // draw regular
            batch.draw(
                    frame,
                    pos.x,
                    pos.y
            );
        }
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }
}
