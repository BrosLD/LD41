package ld.bros.game.service;

public interface Controls {
    boolean left();
    boolean leftTapped();

    boolean right();
    boolean rightTapped();

    boolean up();
    boolean upTapped();

    boolean down();
    boolean downTapped();

    boolean jump();
    boolean jumpTapped();

    boolean dash();
    boolean dashTapped();

    boolean start();
    boolean startTapped();
}
