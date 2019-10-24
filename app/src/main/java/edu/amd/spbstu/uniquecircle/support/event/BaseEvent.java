package edu.amd.spbstu.uniquecircle.support.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BaseEvent<ListenerType> {
    protected List<ListenerType> listeners = new CopyOnWriteArrayList<>();

    public void addListener(ListenerType newListener) {
        listeners.add(newListener);
    }

    public void addListeners(List<ListenerType> newListeners) {
        listeners.addAll(newListeners);
    }

    public void addListeners(BaseEvent<ListenerType> event) {
        listeners.addAll(event.listeners);
    }

    public void removeListener(ListenerType listener) {
        listeners.remove(listener);
    }

    public void removeAllListeners() {
        listeners.clear();
    }
}
