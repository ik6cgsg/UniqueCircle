package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameObject {
    List<Component> components;
    private String name;
    private Transform transform;
    private List<GameObject> children;

    public GameObject(String name) {
        this.name = name;
        children = new ArrayList<>();
        components = new ArrayList<>();
        transform = Transform.addComponent(this);
    }

    public GameObject getParent() {
        return transform.getParent().gameObject;
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

    public void addChild(GameObject child) {
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

    public <T extends Component> Component getComponent() {
        for (Component component : components)
            try {
                return (T)component;
            } catch (ClassCastException e) {}

        return null;
    }
}
