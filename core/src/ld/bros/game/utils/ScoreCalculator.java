package ld.bros.game.utils;

public class ScoreCalculator {

    private static final long STAGE_CLEAR = 1000000L;
    private static final long CARD = 10L;
    private static final long CLEAR_HAND = 1000L;

    public static long stageClear() {
        return STAGE_CLEAR;
    }

    public static long cardsUsed(int numCards) {
        return (long) Math.pow(CARD, numCards);
    }

    public static long clearHand() {
        return CLEAR_HAND;
    }
}
