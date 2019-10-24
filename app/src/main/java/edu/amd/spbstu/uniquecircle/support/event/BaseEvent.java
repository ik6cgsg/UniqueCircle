package edu.amd.spbstu.uniquecircle.support.event;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseEvent<ListenerType> {
    protected List<ListenerType> listeners = new LinkedList<>();

    public void addListener(ListenerType newListener) {
        listeners.add(newListener);
    }

    public void addListener(List<ListenerType> newListeners) {
        listeners.addAll(newListeners);
    }

    public void removeListener(ListenerType listener) {
        listeners.remove(listener);
    }
}
