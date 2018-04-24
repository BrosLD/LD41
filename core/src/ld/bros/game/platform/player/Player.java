package ld.bros.game.platform.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;
import ld.bros.game.platform.Entity;
import ld.bros.game.platform.EntityManager;
import ld.bros.game.platform.Smoke;
import ld.bros.game.platform.player.states.Idle;
import ld.bros.game.service.Locator;

import java.util.Deque;
import java.util.LinkedList;

public class Player extends Entity implements StateManager {

    private float speed = 5f;

    private float jumpSpeed = 10f;
    public final float MAX_JUMP_TIME = 0.25f;
    public final float MAX_HIGH_TIME = 0.1f;

    public final float GRAVITY = -9f;

    public boolean facingRight;

    private Deque<State<?>> states;

    private PlayerConfig config;

    private boolean dashed;

    public Player(EntityManager manager) {
        super(manager);

        width = 32f;
        height = 32f;

//        pos = new Vector2(100f, 300f);

        states = new LinkedList<State<?>>();
        set(new Idle(this));
    }

    @Override
    public void update(float delta) {
        current().update(delta);

        // check level bounds
        if(pos.x < 0) {
            pos.x = 0;
        }
        if(pos.x + width >= manager.getMapWidth()) {
            // we're at the end of the level!
            pos.x = manager.getMapWidth()-width;
        }
        if(pos.y - height < 0) {
            // falling down -> dead
            //pos.y = 0;
//            dead = true;
        }
        if(pos.y + height >= manager.getMapHeight()) {
            pos.y = manager.getMapHeight() - height;
        }


        Locator.log().print("Player:" + current());
    }

    @Override
    public void render(SpriteBatch batch) {
        current().render(batch);
    }

    public void makeSmoke() {

        Smoke smoke = new Smoke(manager);

        if(facingRight) {
            smoke.pos.x = pos.x - smoke.width;
            smoke.pos.y = pos.y;
        } else {
            smoke.pos.x = pos.x + width;
            smoke.pos.y = pos.y;
        }

        smoke.setFacingRight(facingRight);
    }

    public void removeFromList() {
        manager.remove(this);

        // call event
        Locator.event().call("dead");
    }

    @Override
    public void removeFirst() {
        states.removeFirst();
        states.peekFirst().enter();
    }

    @Override
    public void set(State<?> s) {
        if(!states.isEmpty())
            states.removeFirst();

        push(s);
    }

    @Override
    public void push(State<?> s) {
        states.addFirst(s);
        states.peekFirst().enter();
    }

    @Override
    public State<?> current() {
        return states.peekFirst();
    }

    public float getSpeed() {
        return speed + (speed * config.getSpeedMod());
    }

    public float getJumpSpeed() {
        return jumpSpeed;
    }

    public PlayerConfig getConfig() {
        return config;
    }

    public void setConfig(PlayerConfig config) {
        this.config = config;
    }

    public boolean hasDashed() {
        return dashed;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }
}
