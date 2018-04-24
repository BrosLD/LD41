package ld.bros.game.card;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.platform.player.PlayerConfig;
import ld.bros.game.service.Locator;

public class Card {
    public Vector2 pos;
    private Vector2 currPos;

    private TextureRegion base;
    protected TextureRegion image;
    protected String description;
    protected String title;

    protected float width;
    protected float height;

    private float currWidth, currHeight;

    protected static final float SCALE_FACTOR = 1.4f;

    protected Color color;
    protected boolean marked;
    protected boolean selected;

    protected PlayerConfig config;

    public Card() {
        pos = new Vector2();
        currPos = new Vector2();

        color = new Color(Color.WHITE);

        base = Locator.res().atlas("card").findRegion("card_template");
    }

    public void update(float delta) {
        currWidth = width;
        currHeight = height;

        currPos.set(pos);

        if(marked) {
            currWidth = width * SCALE_FACTOR;
            currHeight = height * SCALE_FACTOR;

            float x = (currWidth - width)/2f;
            currPos.x = pos.x - x;

            float y = (currHeight - height)/2f;
            currPos.y = pos.y - y;
        }

        // set color properties
        if(selected)
            color.set(Color.BLUE);
        else
            color.set(Color.WHITE);
        if(!marked)
            color.a = 0.5f;
        else
            color.a = 1f;
    }

    public void render(SpriteBatch batch) {

        batch.setColor(color);

        batch.draw(image, currPos.x, currPos.y, currWidth, currHeight);
        batch.draw(base, currPos.x, currPos.y, currWidth, currHeight);

        batch.setColor(Color.WHITE);
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public TextureRegion getImage() {
        return image;
    }

    public void setImage(TextureRegion image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public PlayerConfig getConfig() {
        return config;
    }

    public void setConfig(PlayerConfig config) {
        this.config = config;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
