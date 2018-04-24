package ld.bros.game.platform.player;

public class PlayerConfig {
    public boolean jump;
    public boolean dash;
    public boolean teleport;
    public boolean wallClimb;

    public float speedMod;

    public PlayerConfig() {

    }

    public PlayerConfig setJump(boolean b) {
        jump = b;
        return this;
    }

    public PlayerConfig setDash(boolean b) {
        dash = b;
        return this;
    }

    public PlayerConfig setTeleport(boolean b) {
        teleport = b;
        return this;
    }

    public PlayerConfig setWallClimb(boolean b) {
        wallClimb = b;
        return this;
    }

    public PlayerConfig setSpeedMod(float f) {
        speedMod = f;
        return this;
    }

    public boolean canJump() {
        return jump;
    }

    public boolean canDash() {
        return dash;
    }

    public boolean canTeleport() {
        return teleport;
    }

    public boolean canWallClimb() {
        return wallClimb;
    }

    public float getSpeedMod() {
        return speedMod;
    }
}
