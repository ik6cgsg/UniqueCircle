package edu.amd.spbstu.uniquecircle.support.event;

public class Event extends BaseEvent<EventListener> {
    public void fireEvent() {
        for (EventListener listener : listeners)
            listener.onEvent();
    }
}
