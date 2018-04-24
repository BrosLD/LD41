package ld.bros.game.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BasicEvent implements Event{

    private Map<String, List<EventHandler>> listener;

    public BasicEvent() {
        listener = new HashMap<String, List<EventHandler>>();
    }

    @Override
    public void call(String type) {

        List<EventHandler> handlerList = listener.get(type);

        if(handlerList == null) return;

        for(int i = 0; i < handlerList.size(); i++)
            handlerList.get(i).onEvent();
    }

    @Override
    public void register(EventHandler handler, String type) {
        List<EventHandler> handlerList = listener.get(type);
        if(handlerList == null) {
            // create new list
            handlerList = new LinkedList<EventHandler>();
            handlerList.add(handler);
            listener.put(type, handlerList);
            return;
        }

        handlerList.add(handler);
    }

    @Override
    public void unregister(EventHandler handler, String type) {
        List<EventHandler> handlerList = listener.get(type);
        if(handlerList == null) {
            return;
        }

        handlerList.remove(handler);
    }
}
