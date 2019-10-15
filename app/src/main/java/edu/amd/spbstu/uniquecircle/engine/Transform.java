package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Transform extends Component {
    private Vector2D localPosition;
    private Vector2D globalPosition;

    private Vector2D localScale;
    private Vector2D globalScale;

    private float localRotation;
    private float globalRotation;

    private Transform parent;
    Map<String, Transform> children;

    public Transform getParent() {
        return parent;
    }

    static Transform addComponent(GameObject parentObject) {
        if (parentObject == null) {
            Log.e("Transform", "null parent object");

            return null;
        }

        return new Transform(parentObject);
    }

    private Transform(GameObject gameObject) {
        super(gameObject);

        // find parent transform
        if (gameObject.getParent() != null)
            parent = gameObject.getParent().getTransform();

        // init transform components
        localScale = new Vector2D();
        localScale.setOnes();
        globalScale = new Vector2D();
        globalScale.setOnes();

        localPosition = new Vector2D();
        globalPosition = new Vector2D();

        children = new HashMap<>();
    }

    private void updateGlobalTransform() {
        if (parent != null) {
            globalPosition = parent.globalPosition.getAdded(localPosition);
            globalScale = parent.globalScale.getMultiplied(localScale);
            globalRotation = parent.globalRotation + localRotation;
        }
        else {
            globalPosition.set(localPosition);
            globalScale.set(localScale);
            globalRotation = localRotation;
        }

        for (Map.Entry<String, Transform> entry : children.entrySet())
            entry.getValue().updateGlobalTransform();
    }

    void addChild(String name, Transform child) {
        children.put(name, child);
        child.parent = this;
        child.updateGlobalTransform();
    }

    public void setTransform(Vector2D position, Vector2D scale, float rotation) {
        localPosition.set(position);
        localScale.set(scale);
        localRotation = rotation;
        updateGlobalTransform();
     }
    public void setPosition(Vector2D position) {
        localPosition.set(position);
        updateGlobalTransform();
    }
    public void setScale(Vector2D scale) {
        localScale.set(scale);
        updateGlobalTransform();
    }
    public void setRotation(float rotation) {
        localRotation = rotation;
        updateGlobalTransform();
    }

    public Vector2D getLocalPosition() {
      return localPosition.clone();
    }
    public Vector2D getLocalScale() {
        return localScale.clone();
    }
    public float getLocalRotation() {
        return localRotation;
    }

    public Vector2D getGlobalPosition() {
        return globalPosition.clone();
    }
    public Vector2D getGlobalScale() {
        return globalScale.clone();
    }
    public float getGlobalRotation() {
        return globalRotation;
    }

    @Override
    public void remove() {
        Log.e("Transform", "trying to remove transform");
    }
}
