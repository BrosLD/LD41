package ld.bros.game.card;

import com.badlogic.gdx.math.MathUtils;
import ld.bros.game.platform.player.PlayerConfig;
import ld.bros.game.service.Locator;

public class CardFactory {

    private static class JumpCard extends Card {
        public JumpCard() {
            super();

            width = 80f;
            height = 140f;

            title = "Jumpman Alpha";
            description = "Lets you jump by pressing [X].";
            image = Locator.res().atlas("card").findRegion("jump");
            config = new PlayerConfig().setJump(true);
        }
    }

    private static class DashCard extends Card {
        public DashCard() {
            super();

            width = 80f;
            height = 140f;

            title = "Dash Lightning";
            description = "Dash forward with highspeed by pressing [C].";
            image = Locator.res().atlas("card").findRegion("dash");
            config = new PlayerConfig().setDash(true);
        }
    }

    private static class WallCard extends Card {
        public WallCard() {
            super();

            width = 80f;
            height = 140f;

            title = "Wall Vertical";
            description = "Run along vertical walls by pressing [UP] or [DOWN].";
            image = Locator.res().atlas("card").findRegion("wall");
            config = new PlayerConfig().setWallClimb(true);
        }
    }

    private static class Mul2Card extends Card {
        public Mul2Card() {
            super();

            width = 80f;
            height = 140f;

            title = "Multiplier 2.0";
            description = "Speeds up your general speed by factor 2.";
            image = Locator.res().atlas("card").findRegion("mul2");
            config = new PlayerConfig().setSpeedMod(1f);
        }
    }

    private static class Mul15Card extends Card {
        public Mul15Card() {
            super();

            width = 80f;
            height = 140f;

            title = "Multiplier 1.5";
            description = "Speeds up your general speed by factor 1.5.";
            image = Locator.res().atlas("card").findRegion("mul15");
            config = new PlayerConfig().setSpeedMod(0.5f);
        }
    }

    private static class AllCard extends Card {
        public AllCard() {
            super();

            width = 80f;
            height = 140f;

            title = "Ultra";
            description = "Go for pro. Enables every enhancement!";
            image = Locator.res().atlas("card").findRegion("all");
            config = new PlayerConfig()
                    .setJump(true)
                    .setDash(true)
                    .setWallClimb(true)
                    .setSpeedMod(1f);
        }
    }

    private static class RngCard extends Card {
        public RngCard() {
            super();

            width = 80f;
            height = 140f;

            title = "???";
            description = "Everything is possible. Or is it?";
            image = Locator.res().atlas("card").findRegion("rng");
            config = new PlayerConfig()
                    .setJump(MathUtils.randomBoolean())
                    .setDash(MathUtils.randomBoolean())
                    .setWallClimb(MathUtils.randomBoolean())
                    .setSpeedMod(MathUtils.random());
        }
    }

    public static Card createCard() {
        int type = MathUtils.random(0, 5);

        // AllCard should be rare...
        if(MathUtils.random() < 0.1) {
            type = 6;
        }

        switch (type) {
            case 0: return new JumpCard();
            case 1: return new DashCard();
            case 2: return new WallCard();
            case 3: return new Mul2Card();
            case 4: return new Mul15Card();
            case 5: return new RngCard();
            default: return new AllCard();
        }
    }
}
