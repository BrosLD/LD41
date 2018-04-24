package ld.bros.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.State;
import ld.bros.game.service.EventHandler;
import ld.bros.game.service.Locator;
import ld.bros.game.utils.HelpText;
import ld.bros.game.utils.MathUtils;

public class StartState extends State<GameStateManager> {

    private static class Option {
        public String caption;
        public Vector2 pos;
        public EventHandler callback;
    }

    private TextureRegion image;

    private HelpText help;

    private Option start;
    private Option howToPlay;
    private Option credits;
    private Option[] all;

    private int curr;

    private BitmapFont font;

    private TextureRegion selection;
    private Vector2 pos;

    private Color marker;

    public StartState(final GameStateManager manager) {
        super(manager);

        image = Locator.res().region("start");
        selection = Locator.res().region("pixel");

        help = new HelpText("[^]/[v]: Cursor\n[X]: Select Option");

        font = Locator.res().font("large");

        pos = new Vector2();
        marker = new Color(0x4dd8e6ff);

        start = new Option();
        start.caption = "Start";
        start.pos = new Vector2();
        start.pos.x = (LudumDare41.WIDTH - MathUtils.calcDimensions(font, start.caption).x)/2f;
        start.pos.y = 290f - 8f;
        start.callback = new EventHandler() {
            @Override
            public void onEvent() {
                manager.set(new MainState(manager));
            }
        };

        howToPlay = new Option();
        howToPlay.caption = "How To Play";
        howToPlay.pos = new Vector2();
        howToPlay.pos.x = (LudumDare41.WIDTH - MathUtils.calcDimensions(font, howToPlay.caption).x)/2f;
        howToPlay.pos.y = 290f - 30f - 8f;
        howToPlay.callback = new EventHandler() {
            @Override
            public void onEvent() {
                manager.set(new HowToPlay(manager));
            }
        };

        credits = new Option();
        credits.caption = "Credits";
        credits.pos = new Vector2();
        credits.pos.x = (LudumDare41.WIDTH - MathUtils.calcDimensions(font, credits.caption).x)/2f;
        credits.pos.y = 290f - 60f - 8f;
        credits.callback = new EventHandler() {
            @Override
            public void onEvent() {
                manager.set(new CreditsState(manager));
            }
        };

        all = new Option[3];
        all[0] = start;
        all[1] = howToPlay;
        all[2] = credits;

        curr = 0;
        select();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(Locator.controls().upTapped()) {
            curr--;
            if(curr < 0) curr = all.length-1;
        } else if(Locator.controls().downTapped()) {
            curr++;
            curr %= all.length;
        }

        select();

        if(Locator.controls().jumpTapped()) {
            // handle confirm
            all[curr].callback.onEvent();
        }
    }

    private void select() {
        pos.x = 0;
        pos.y = all[curr].pos.y - 26f + 8f;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.draw(image, 0, 0, LudumDare41.WIDTH, LudumDare41.HEIGHT);

        // render options
        for(int i = 0; i < all.length; i++) {
            if(i == curr) {
                // draw marker
                batch.setColor(marker);
                batch.draw(selection, pos.x, pos.y, LudumDare41.WIDTH, 26f);
                batch.setColor(Color.WHITE);
            }

            font.draw(batch, all[i].caption, all[i].pos.x, all[i].pos.y);
        }

        help.render(batch);
    }

}
