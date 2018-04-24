package ld.bros.game.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Log {
    void showMessages(boolean b);
    void print(String msg);
    void render(SpriteBatch batch);
}
