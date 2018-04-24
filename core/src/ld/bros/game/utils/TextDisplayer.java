package ld.bros.game.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import ld.bros.game.LudumDare41;
import ld.bros.game.service.Locator;

import java.util.ArrayList;
import java.util.List;

// class for printing messages onto the screen
public class TextDisplayer implements Log {

    private boolean display;

    private OrthographicCamera camera;
    private Matrix4 previous;

    private List<String> messages;
    private final float x = 15f;
    private final float y = LudumDare41.HEIGHT - 15f;

    private final float padding = 5f;
    private final float lineHeight;

    private BitmapFont font;

    public TextDisplayer() {
        messages = new ArrayList<String>(10);

        font = Locator.res().font("night_machine");
        lineHeight = font.getCapHeight();

        camera = new OrthographicCamera(LudumDare41.WIDTH, LudumDare41.HEIGHT);
        camera.setToOrtho(false);
    }

    public void render(SpriteBatch batch) {

        if(!display) return;

        previous = batch.getProjectionMatrix();
        batch.setProjectionMatrix(camera.combined);

        for(int i = 0; i < messages.size(); i++) {
            font.draw(batch, messages.get(i), x, y - (padding+lineHeight)*i);
        }

        messages.clear();

        batch.setProjectionMatrix(previous);
    }

    @Override
    public void showMessages(boolean b) {
        this.display = b;
    }

    public void print(String msg) {
        messages.add(msg);
    }
}