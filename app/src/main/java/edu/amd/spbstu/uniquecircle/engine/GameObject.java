package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameObject {
    List<Component> components;
    private String name;
    private Transform transform;
    private List<GameObject> children;
    private Scene scene;

    GameObject(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
        children = new ArrayList<>();
        components = new ArrayList<>();
        transform = Transform.addComponent(this);
    }

     public GameObject getParent() {
        if (transform.getParent() != null)
            return transform.getParent().gameObject;
        return null;
    }

     public List<GameObject> getChildren() {
        return children;
    }

     public String getName() {
        return name;
    }

     public Transform getTransform() {
        return transform;
    }

     public GameObject addChild(String name) {
        if (name == null) {
            Log.e("GameObject", "null name");
            return null;
        }

        GameObject child = new GameObject(name, scene);

        children.add(child);
        transform.addChild(child.name, child.transform);

        return child;
    }

     void addChild(GameObject child) {
        if (child == null) {
            Log.e("GameObject", "null child in package private method");
            return;
        }

        children.add(child);
        transform.addChild(child.name, child.transform);
    }

     void update() {
        // update self
        for (Component component : components)
            component.update();

        // update children
        for (Map.Entry<String, Transform> entry : transform.children.entrySet())
            entry.getValue().gameObject.update();
    }

     void render(Canvas canvas) {
        // render self
        for (Component component : components)
            component.render(canvas);

        // render children
        for (Map.Entry<String, Transform> entry : transform.children.entrySet())
            entry.getValue().gameObject.render(canvas);
    }

    public GameObject getChild(String name) {
        Transform tmp;
        if ((tmp = transform.children.get(name)) != null)
            return tmp.gameObject;

        GameObject output;
        for (Map.Entry<String, Transform> entry : transform.children.entrySet())
            if ((output = entry.getValue().gameObject.getChild(name)) != null)
                return output;

        return null;
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        for (Component component : components)
            if (clazz.isInstance(component))
                return (T)component;

        return null;
    }

    public <T extends Component> List<T> getComponents(Class<T> clazz) {
        List<T> output = new LinkedList<>();

        for (Component component : components)
            if (clazz.isInstance(component))
                output.add((T)component);

        return output;
    }

    public Scene getScene() {
        return scene;
    }
}
