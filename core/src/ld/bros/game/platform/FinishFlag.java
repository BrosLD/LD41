package ld.bros.game.platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.service.Locator;

public class FinishFlag extends Entity {

    private TextureRegion image;

    public FinishFlag(EntityManager manager) {
        super(manager);

        image = Locator.res().region("finish");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        if(manager.collisionWithEntity(this) != null) {
            // player touches this flag -> clear
            Locator.event().call("clear");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y);
    }
}
