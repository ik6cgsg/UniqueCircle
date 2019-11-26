package edu.amd.spbstu.uniquecircle.engine;

import android.graphics.Canvas;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject {
    List<Component> components;
    private String name;
    private Transform transform;
    private List<GameObject> children;
    private Scene scene;

    GameObject(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
        children = new CopyOnWriteArrayList<>();
        components = new CopyOnWriteArrayList<>();
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
        transform.addChild(child.transform);

        return child;
    }

     void addChild(GameObject child) {
        if (child == null) {
            Log.e("GameObject", "null child in package private method");
            return;
        }

        children.add(child);
        transform.addChild(child.transform);
    }

     void update() {
        // update self
        for (Component component : components)
            component.update();

        // update children
        for (Transform child : transform.children)
            child.gameObject.update();
    }

     void render(Canvas canvas) {
        // render self
        for (Component component : components)
            component.render(canvas);

        // render children
         for (Transform child : transform.children)
            child.gameObject.render(canvas);
    }

    public GameObject getChild(String name) {
        // search children
        for (Transform child : transform.children)
            if (child.gameObject.getName().equals(name))
                return child.gameObject;

        // recursive search
        GameObject output;
        for (Transform child : transform.children)
            if ((output = child.gameObject.getChild(name)) != null)
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

    private <T extends Component> void appendComponentsInChildren(Class<T> clazz, List<T> output) {
        output.addAll(getComponents(clazz));

        for (GameObject child : children)
            child.appendComponentsInChildren(clazz, output);
    }

    public <T extends Component> List<T> getComponentsInChildren(Class<T> clazz) {
        List<T> output = new LinkedList<>();

        appendComponentsInChildren(clazz, output);

        return output;
    }

    public Scene getScene() {
        return scene;
    }

    public void remove() {
        // remove all personal components
        for (Component component : components)
            if (component != transform)
                component.remove();

        // remove self from
        GameObject parent = getParent();
        if (parent != null)
            parent.children.remove(this);

        // recursively remove children
        for (GameObject child : children)
            child.remove();
    }
}
