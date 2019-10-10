package edu.amd.spbstu.uniquecircle.engine;

public abstract class Component {
    GameObject gameObject;

    public GameObject getGameObject() {
        return gameObject;
    }

    protected Component(GameObject gameObject) {
        this.gameObject = gameObject;
        gameObject.components.add(this);
    }

    protected void update() {}
    protected void render() {}

    public void remove() {
        gameObject.components.remove(this);
    }
}
