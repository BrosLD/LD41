package ld.bros.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.State;
import ld.bros.game.platform.UI;
import ld.bros.game.service.Locator;
import ld.bros.game.utils.HelpText;

public class GameOverState extends State<MainState> {

    private TextureRegion gameOver;

    private final float TIME = 2f;
    private float timer = 0f;

    private float y;

    private HelpText help;

    private float scoreX, scoreY, currScoreY;
    private String score;
    private BitmapFont scoreFont;

    public GameOverState(MainState manager) {
        super(manager);

        gameOver = Locator.res().region("game_over");

        help = new HelpText("[R]: Quick Reset\n[Enter]: Back To Title");

        scoreFont = Locator.res().font("large");

        score = "Your Score: " + UI.SCORE_FORMAT.format(manager.getScore());
        Vector2 dim = ld.bros.game.utils.MathUtils.calcDimensions(scoreFont, score);
        scoreX = (LudumDare41.WIDTH - dim.x)/2f;
        scoreY = (LudumDare41.HEIGHT - dim.y)/2f + 30f;
    }

    @Override
    public void enter() {
        super.enter();

        Locator.event().call("pause_timer");
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        timer += delta;
        if(timer >= TIME)
            timer = TIME;

        y = MathUtils.lerp(
                -gameOver.getRegionHeight(),
                (LudumDare41.HEIGHT + gameOver.getRegionHeight())/2f,
                timer/TIME
        );

        currScoreY = MathUtils.lerp(
                -gameOver.getRegionHeight(),
                scoreY,
                timer/TIME
        );

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Locator.event().call("reset");
        } else if(Locator.controls().startTapped()) {
            Locator.event().call("to_title");
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.draw(gameOver, (LudumDare41.WIDTH - gameOver.getRegionWidth())/2f, y);

        scoreFont.draw(batch, score, scoreX, currScoreY);

        help.render(batch);
    }
}
