package ld.bros.game.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyboardControls implements Controls {
    @Override
    public boolean left() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    @Override
    public boolean leftTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.LEFT);
    }

    @Override
    public boolean right() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public boolean rightTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT);
    }

    @Override
    public boolean up() {
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }

    @Override
    public boolean upTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.UP);
    }

    @Override
    public boolean down() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    @Override
    public boolean downTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.DOWN);
    }

    @Override
    public boolean jump() {
        return Gdx.input.isKeyPressed(Input.Keys.X);
    }

    @Override
    public boolean jumpTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.X);
    }

    @Override
    public boolean dash() {
        return Gdx.input.isKeyPressed(Input.Keys.C);
    }

    @Override
    public boolean dashTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.C);
    }

    @Override
    public boolean start() {
        return Gdx.input.isKeyPressed(Input.Keys.ENTER);
    }

    @Override
    public boolean startTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
    }
}
