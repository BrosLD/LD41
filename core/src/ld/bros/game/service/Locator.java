package ld.bros.game.service;

import ld.bros.game.utils.Log;

public class Locator {
    private static Resources resources;
    private static Log log;
    private static Controls controls;
    private static Event event;

    public static void setResources(Resources resources) {
        Locator.resources = resources;
    }

    public static Resources res() {
        return resources;
    }

    public static void setLog(Log log) {
        Locator.log = log;
    }

    public static Log log() {
        return log;
    }

    public static void setControls(Controls controls) {
        Locator.controls = controls;
    }

    public static Controls controls() {
        return controls;
    }

    public static void setEvent(Event event) {
        Locator.event = event;
    }

    public static Event event() {
        return event;
    }
}
