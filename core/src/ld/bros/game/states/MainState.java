package ld.bros.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.card.Card;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;
import ld.bros.game.platform.LevelFactory;
import ld.bros.game.platform.player.PlayerConfig;
import ld.bros.game.service.Event;
import ld.bros.game.service.EventHandler;
import ld.bros.game.service.Locator;
import ld.bros.game.utils.ScoreCalculator;

import java.util.LinkedList;
import java.util.List;

public class MainState extends State<GameStateManager> implements StateManager {

    // true = endless platformer
    public static final boolean NO_CARD = false;

    private List<State<?>> substates;
    private int curr;

    private PlayerConfig playerConfig;

    private List<Card> remainingCards;

    public static final float CARD_TIME = 10f;
    private float timer = CARD_TIME;
    private boolean pauseTimer;

    private long score;

    private EventHandler gameOverHandler = new EventHandler() {
        @Override
        public void onEvent() {
            push(new GameOverState(MainState.this));
        }
    };

    private EventHandler pauseTimerHandler = new EventHandler() {
        @Override
        public void onEvent() {
            pauseTimer = true;
        }
    };

    private EventHandler unpauseTimerHandler = new EventHandler() {
        @Override
        public void onEvent() {
            pauseTimer = false;
            timer = CARD_TIME;
        }
    };

    private EventHandler clearHandler = new EventHandler() {
        @Override
        public void onEvent() {
            push(new ClearState(MainState.this));
        }
    };

    private EventHandler nextLevelHandler = new EventHandler() {
        @Override
        public void onEvent() {
            nextLevel();
        }
    };

    private EventHandler resetHandler = new EventHandler() {
        @Override
        public void onEvent() {

            LevelFactory.reset();

            score = 0L;

            timer = CARD_TIME;
            setPlayerConfig(new PlayerConfig());
            pauseTimer = false;

            setRemainingCards(null);

            substates.clear();
            set(new PlatformState(MainState.this));
            push(new CardState(MainState.this));
        }
    };

    private EventHandler toTitleHandler = new EventHandler() {
        @Override
        public void onEvent() {
            LevelFactory.reset();
            manager.set(new TitleState(manager));
        }
    };

    public MainState(GameStateManager manager) {
        super(manager);

        Locator.event().register(gameOverHandler, "dead");
        Locator.event().register(pauseTimerHandler, "pause_timer");
        Locator.event().register(unpauseTimerHandler, "un_pause_timer");
        Locator.event().register(clearHandler, "clear");
        Locator.event().register(nextLevelHandler, "next_level");
        Locator.event().register(resetHandler, "reset");
        Locator.event().register(toTitleHandler, "to_title");


        if (NO_CARD) {
            setPlayerConfig(new PlayerConfig()
                    .setJump(true)
                    .setDash(true)
                    .setWallClimb(true)
            );
        }

        substates = new LinkedList<State<?>>();
        set(new PlatformState(this));

        if(!NO_CARD)
            push(new CardState(this));
    }

    @Override
    public void finish() {
        super.finish();

        Locator.event().unregister(gameOverHandler, "dead");
        Locator.event().unregister(pauseTimerHandler, "pause_timer");
        Locator.event().unregister(unpauseTimerHandler, "un_pause_timer");
        Locator.event().unregister(clearHandler, "clear");
        Locator.event().unregister(nextLevelHandler, "next_level");
        Locator.event().unregister(resetHandler, "reset");
        Locator.event().unregister(toTitleHandler, "to_title");
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        current().update(delta);

        if(!NO_CARD) {
            if (!pauseTimer) {
                timer -= delta;
                if (timer < 0f) {
                    timer = CARD_TIME;
                    push(new CardState(this));
                }
            }
        }

        Locator.log().print("CardState: " + pauseTimer);

        Locator.log().print(String.format("Time: %.02f", timer));
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        for(int i = 0; i < substates.size(); i++) {
            substates.get(i).render(batch);
        }
    }

    @Override
    public void removeFirst() {
        if(substates.size() > 0) {
            substates.get(curr).finish();
            substates.remove(curr);
            curr--;

            if(curr >= 0)
                substates.get(curr).enter();
        }
    }

    @Override
    public void set(State<?> s) {
        if(substates.isEmpty()) {
            substates.add(s);
            curr = 0;
        } else {
            substates.get(curr).finish();
            substates.set(curr, s);
        }

        substates.get(curr).enter();
    }

    @Override
    public void push(State<?> s) {
        substates.add(s);
        curr++;

        substates.get(curr).enter();
    }

    @Override
    public State<?> current() {
        return substates.get(curr);
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

    public void setPlayerConfig(PlayerConfig playerConfig) {
        this.playerConfig = playerConfig;
    }

    public List<Card> getRemainingCards() {
        return remainingCards;
    }

    public void setRemainingCards(List<Card> remainingCards) {
        this.remainingCards = remainingCards;
    }

    public float getRemainingTime() {
        return timer;
    }

    public long getScore() {
        return score;
    }

    public void nextLevel() {

        increaseScore(ScoreCalculator.stageClear());

        timer = CARD_TIME;
        setPlayerConfig(new PlayerConfig());
        pauseTimer = false;

        substates.clear();
        set(new PlatformState(this));
        push(new CardState(this));
    }

    public void reduceScore(long l) {
        score -= l;
    }

    public void increaseScore(long l) {
        score += l;
    }
}
