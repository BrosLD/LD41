package ld.bros.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.State;
import ld.bros.game.service.Locator;
import ld.bros.game.utils.HelpText;

public class ClearState extends State<MainState> {

    private TextureRegion clear;

    private final float TIME = 0.7f;
    private float timer = 0f;

    private float y;

    private HelpText help;

    public ClearState(MainState manager) {
        super(manager);

        clear = Locator.res().region("clear");

        help = new HelpText("[X]: Next Level");
    }

    @Override
    public void enter() {
        super.enter();

        Locator.event().call("pause_timer");
        timer = 0f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        timer += delta;
        if(timer >= TIME) {
            timer = TIME;
        }

        y = MathUtils.lerp(
                -clear.getRegionHeight(),
                (LudumDare41.HEIGHT + clear.getRegionHeight())/2f,
                timer/TIME
        );

        if(Locator.controls().jumpTapped()) {
            // next level
            Locator.event().call("next_level");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.draw(clear, (LudumDare41.WIDTH - clear.getRegionWidth())/2f, y);

        help.render(batch);
    }
}
