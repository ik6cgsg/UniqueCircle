package edu.amd.spbstu.uniquecircle.support;

import java.util.LinkedList;
import java.util.List;

public class Event {
    private List<EventListener> listeners = new LinkedList<>();

    public void addListener(EventListener newListener) {
        listeners.add(newListener);
    }

    public void addListener(List<EventListener> newListeners) {
        listeners.addAll(newListeners);
    }

    public void fireEvent() {
        for (EventListener listener : listeners)
            listener.onEvent();
    }
}
