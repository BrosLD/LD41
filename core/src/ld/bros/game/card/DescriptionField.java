package ld.bros.game.card;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import ld.bros.game.LudumDare41;
import ld.bros.game.service.Locator;

public class DescriptionField {
    private Vector2 pos;
    private float width;
    private float height;

    private TextureRegion image;
    private BitmapFont header;
    private BitmapFont font;

    private String text;
    private String title;

    public DescriptionField() {

        image = Locator.res().region("description_box");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
        pos = new Vector2(
                LudumDare41.WIDTH - width,
                LudumDare41.HEIGHT - 12f - height
        );

        header = Locator.res().font("header");
        font = Locator.res().font("text");

        text = "";
        title = "";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void render(SpriteBatch batch) {
        // background
        batch.draw(image, pos.x, pos.y, width, height);

        // text
        header.draw(batch, title, pos.x + 12f, pos.y + height - 6f, width - 16f, Align.left, false);
        font.draw(batch, text, pos.x + 12f, pos.y + height - 36f, width - 16f, Align.right, true);
    }
}
