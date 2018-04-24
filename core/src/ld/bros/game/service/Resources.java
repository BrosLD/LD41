package ld.bros.game.service;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Resources {

    BitmapFont font(String which);
    TextureAtlas atlas(String which);
    TextureRegion region(String which);

    void load();
    void dispose();
}
