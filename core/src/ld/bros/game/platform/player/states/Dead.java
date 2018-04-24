package ld.bros.game.platform.player.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.platform.player.Player;
import ld.bros.game.service.Locator;

public class Dead extends PlayerState {

    private static class Phantom {
        public TextureRegion image;
        public Vector2 dir;
        public Vector2 pos;
    }

    private Phantom[] phantoms;
    private static final float corner = (float) (Math.sqrt(2)/2.0);

    private Vector2[] directions = {
            new Vector2(-1f, 0f),
            new Vector2(1f, 0f),
            new Vector2(0f, 1f),
            new Vector2(0f, -1f),
            new Vector2(corner, corner),
            new Vector2(corner, -corner),
            new Vector2(-corner, corner),
            new Vector2(-corner, -corner)
    };

    public static final float DURATION = 0.4f;
    public static final float DISTANCE = 150f;
    private float timer;

    private Color tint;
    private float alpha;

    public Dead(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0f,
                Locator.res().atlas("jump").findRegions( "jump"),
                Animation.PlayMode.NORMAL
        );

        phantoms = new Phantom[directions.length];

        for(int i = 0; i < phantoms.length; i++) {
            phantoms[i] = new Phantom();
            phantoms[i].image = animation.getKeyFrame(animationTimer);
            phantoms[i].dir = directions[i];
            phantoms[i].pos = new Vector2();
        }

        tint = new Color(Color.BLUE);

        Locator.event().call("pause_timer");
    }

    @Override
    public void update(float delta) {
        // don't call super!

        timer += delta;
        if(timer > DURATION) {
            // dead animation finished, player is gone
            manager.set(new Gone(manager));
        }

        // calc distance
        for(int i = 0; i < phantoms.length; i++) {
            phantoms[i].pos = phantoms[i].dir.cpy()
                    .scl(MathUtils.lerp(0f, DISTANCE, timer / DURATION));
        }

        // calc alpha
        alpha = MathUtils.lerp(1f, 0f, timer/DURATION);
        tint.a = alpha;
    }

    @Override
    public void render(SpriteBatch batch) {
        // don't call super!

        batch.setColor(tint);

        for(int i = 0; i < phantoms.length; i++) {
            if(!manager.facingRight) {
                batch.draw(
                        phantoms[i].image, manager.pos.x + phantoms[i].pos.x, manager.pos.y + phantoms[i].pos.y
                );
            } else {
                // flip image
                batch.draw(
                        phantoms[i].image, manager.pos.x + phantoms[i].pos.x + phantoms[i].image.getRegionWidth(), manager.pos.y + phantoms[i].pos.y,
                        -phantoms[i].image.getRegionWidth(), phantoms[i].image.getRegionHeight()
                );
            }
        }
        batch.setColor(Color.WHITE);
    }

    @Override
    public String toString() {
        return "Dead";
    }
}
