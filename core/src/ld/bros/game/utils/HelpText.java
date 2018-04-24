package ld.bros.game.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.LudumDare41;
import ld.bros.game.service.Locator;

public class HelpText {

    private static final float PADDING = 8f;

    private String help;
    private BitmapFont font;

    private TextureRegion background;

    private float width;
    private float height;
    private float x;
    private float y;

    public HelpText() {
        this("");
    }

    public HelpText(String help) {
        font = Locator.res().font("help");
        background = Locator.res().region("pixel");

        setHelp(help);
    }

    public void render(SpriteBatch batch) {
        // draw box
        batch.setColor(0f, 0f, 0f, 0.75f);
        batch.draw(background, x, y, width, height);

        // draw text
        batch.setColor(Color.WHITE);
        font.draw(batch, help, x + PADDING, y + height - PADDING);
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;

        Vector2 dimensions = MathUtils.calcDimensions(font, help);
        width = dimensions.x + 2*PADDING;
        height = dimensions.y + 2*PADDING;

        x = LudumDare41.WIDTH - PADDING - width;
        y = PADDING;
    }
}
