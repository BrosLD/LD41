package ld.bros.game.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class GameResources implements Resources {

    private Map<String, TextureRegion> regions;
    private Map<String, BitmapFont> fonts;
    private Map<String, TextureAtlas> atlases;

    public GameResources() {
        regions = new HashMap<String, TextureRegion>();
        fonts = new HashMap<String, BitmapFont>();
        atlases = new HashMap<String, TextureAtlas>();
    }

    @Override
    public BitmapFont font(String which) {
        BitmapFont f = fonts.get(which);

        if(f == null)
            throw new ResourceNotFoundException("Requested font ['" + which + "'] was not found!");

        return f;
    }

    @Override
    public TextureAtlas atlas(String which) {
        TextureAtlas a = atlases.get(which);

        if(a == null)
            throw new ResourceNotFoundException("Requested atlas ['" + which + "'] was not found!");

        return a;
    }

    @Override
    public TextureRegion region(String which) {
        TextureRegion r = regions.get(which);

        if(r == null)
            throw new ResourceNotFoundException("Requested texture region ['" + which + "'] was not found!");

        return r;
    }

    @Override
    public void load() {
        // load texture regions
        generateRegion("player", "image/player/player.png");
        generateRegion("green", "green.png");
        generateRegion("pixel", "pixel.png");
        generateRegion("description_box", "image/ui/description_box.png");
        generateRegion("enhancement", "image/ui/enhancement.png");
        generateRegion("score", "image/ui/score.png");
        generateRegion("time", "image/ui/time.png");
        generateRegion("game_over", "image/ui/game_over.png");
        generateRegion("clear", "image/ui/clear.png");
        generateRegion("finish", "image/finish.png");

        generateRegion("title", "image/special/title_screen.png");
        generateRegion("start", "image/special/title_screen_blanko.png");
        generateRegion("how_to_play", "image/special/how_to_play.png");
        generateRegion("credits", "image/special/credits.png");


        // load atlases
        generateAtlas("idle", "image/player/idle.atlas");
        generateAtlas("run", "image/player/run.atlas");
        generateAtlas("jump", "image/player/jump.atlas");
        generateAtlas("fall", "image/player/jump.atlas");
        generateAtlas("wall", "image/player/wall.atlas");
        generateAtlas("smoke", "image/player/smoke.atlas");

        generateAtlas("card", "image/cards/cards.atlas");

        // load fonts
        loadFonts();
    }

    private void generateRegion(String name, String path) {
        regions.put(
                name,
                new TextureRegion(new Texture(path))
        );
    }

    private void generateAtlas(String name, String path) {
        atlases.put(
                name,
                new TextureAtlas(Gdx.files.internal(path))
        );
    }

    private void loadFonts() {
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 18;
        params.color = Color.WHITE;
        generateFont("night_machine", "night_machine.otf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        params.color = new Color(0xff8926ff);
        generateFont("header", "aldothe_apache.ttf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 20;
        params.color = new Color(Color.WHITE);
        generateFont("large", "aldothe_apache.ttf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 18;
        params.color = new Color(Color.WHITE);
        generateFont("medium", "aldothe_apache.ttf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 16;
        params.color = new Color(0xffffff99);
        generateFont("text", "prototype.ttf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 18;
        params.color = new Color(0xff8926ff);
        generateFont("help", "prototype.ttf", params);
    }

    private void generateFont(String name, String path, FreeTypeFontGenerator.FreeTypeFontParameter params) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/" + path));
        fonts.put(name, generator.generateFont(params));
        generator.dispose();
    }

    @Override
    public void dispose() {
        // dispose regions
        for(Map.Entry<String, TextureRegion>  entry : regions.entrySet()) {
            entry.getValue().getTexture().dispose();
        }

        // dispose atlases
        for(Map.Entry<String, TextureAtlas>  entry : atlases.entrySet()) {
            entry.getValue().dispose();
        }

        // dispose fonts
        for(Map.Entry<String, BitmapFont>  entry : fonts.entrySet()) {
            entry.getValue().dispose();
        }
    }
}
