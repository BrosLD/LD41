package ld.bros.game.platform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ld.bros.game.LudumDare41;
import ld.bros.game.main.State;
import ld.bros.game.platform.player.PlayerConfig;
import ld.bros.game.service.Locator;
import ld.bros.game.states.MainState;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class UI extends State<MainState> {

    public static final DecimalFormat SCORE_FORMAT = new DecimalFormat("000,000,000,000");

    private OrthographicCamera camera;

    private BitmapFont top;
    private BitmapFont bottom;

    private static final float SCORE_TIME = 1f;
    private float scoreTimer = 0f;
    private long currScore;
    private long endScore;
    private String score;
    private TextureRegion scoreBG;

    private String time;
    private TextureRegion timeBG;

    private List<String> enhancements;
    private TextureRegion enhancementBG;

    public UI(MainState manager) {
        super(manager);

        camera = new OrthographicCamera(LudumDare41.WIDTH, LudumDare41.HEIGHT);
        camera.setToOrtho(false);

        scoreBG = Locator.res().region("score");
        timeBG = Locator.res().region("time");
        enhancementBG = Locator.res().region("enhancement");

        enhancements = new LinkedList<String>();

        top = Locator.res().font("large");
        bottom = Locator.res().font("medium");

        score = SCORE_FORMAT.format(manager.getScore());
        time = "";
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        camera.position.x = LudumDare41.WIDTH/2f;
        camera.position.y = LudumDare41.HEIGHT/2f;
        camera.update();

        // update enhancements
        PlayerConfig c = manager.getPlayerConfig();
        enhancements.clear();
        if(c.canJump()) enhancements.add("+ Jump");
        if(c.canDash()) enhancements.add("+ Dash");
        if(c.canWallClimb()) enhancements.add("+ Wall Climb");
        if(c.getSpeedMod() > 0f) enhancements.add(String.format("+ Speed Mod %.1f", 1f+c.getSpeedMod()));

        // update score
        endScore = manager.getScore();
        if(endScore != currScore) {
            scoreTimer += delta;

            currScore = (long) MathUtils.lerp((float)currScore, (float)endScore, scoreTimer/SCORE_TIME);

            if(scoreTimer >= SCORE_TIME) {
                currScore = endScore;
                scoreTimer = 0f;
            }
        }

        score = SCORE_FORMAT.format(currScore);

        // update time
        time = String.format("%.2f", manager.getRemainingTime());
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.end();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw score
        batch.draw(
                scoreBG, 0, LudumDare41.HEIGHT - scoreBG.getRegionHeight()
        );
        top.draw(batch, score, 110f, LudumDare41.HEIGHT - scoreBG.getRegionHeight() + 46f);

        // draw time
        batch.draw(
                timeBG, LudumDare41.WIDTH - timeBG.getRegionWidth(), LudumDare41.HEIGHT - 50f - timeBG.getRegionHeight()
        );
        top.draw(batch, time, LudumDare41.WIDTH - 50f, LudumDare41.HEIGHT - scoreBG.getRegionHeight() + 46f);

        // draw enhancements
        for(int i = 0; i < enhancements.size(); i++) {
            float y = LudumDare41.HEIGHT - (130f + (i*(26 + 12)));

            batch.draw(
                    enhancementBG, 0, y
            );
            bottom.draw(batch, enhancements.get(i), 10f, y + 20f);
        }

        batch.end();
        batch.begin();
    }
}
