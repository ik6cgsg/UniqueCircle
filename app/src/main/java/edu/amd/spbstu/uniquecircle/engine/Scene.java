package edu.amd.spbstu.uniquecircle.engine;

public class Scene {
    private GameObject rootObject;

    public Scene() {
        rootObject = new GameObject("");
    }

    public void update() {
        rootObject.update();
    }

    public void render() {
        rootObject.render();
    }

    public void addGameObject(GameObject gameObject) {
        rootObject.addChild(gameObject);
    }

    public void getGameObject(String name) {
        rootObject.getChild(name);
    }
}
