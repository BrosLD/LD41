package ld.bros.game.service;

public interface Event {
    void call(String type);
    void register(EventHandler handler, String type);
    void unregister(EventHandler handler, String type);
}
