package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class Transform extends Component {
    private Vector2D localPosition;
    private Vector2D globalPosition;

    private Vector2D localScale;
    private Vector2D globalScale;

    private float localRotation;
    private float globalRotation;

    private Transform parent;
    List<Transform> children;

    public Transform getParent() {
        return parent;
    }

    static Transform addComponent(GameObject gameObject) {
        if (gameObject == null) {
            Log.e("Transform", "null game object");

            return null;
        }

        return new Transform(gameObject);
    }

    private Transform(GameObject gameObject) {
        super(gameObject);

        // init transform components
        localScale = new Vector2D();
        localScale.setOnes();
        globalScale = new Vector2D();
        globalScale.setOnes();

        localPosition = new Vector2D();
        globalPosition = new Vector2D();

        children = new CopyOnWriteArrayList<>();
    }

    private void updateGlobalTransform() {
        if (parent != null) {
            globalPosition = parent.globalPosition.getAdded(localPosition.getMultiplied(parent.globalScale));
            globalScale = parent.globalScale.getMultiplied(localScale);
            globalRotation = parent.globalRotation + localRotation;
        }
        else {
            globalPosition.set(localPosition);
            globalScale.set(localScale);
            globalRotation = localRotation;
        }

        for (Transform child : children)
            child.updateGlobalTransform();
    }

    void addChild(Transform child) {
        children.add(child);
        child.parent = this;
        child.updateGlobalTransform();
    }

    public void setTransform(Vector2D localPosition, Vector2D localScale, float localRotation) {
        this.localPosition.set(localPosition);
        this.localScale.set(localScale);
        this.localRotation = localRotation;
        updateGlobalTransform();
     }
    public void setLocalPosition(Vector2D localPosition) {
        this.localPosition.set(localPosition);
        updateGlobalTransform();
    }
    public void setLocalScale(Vector2D localScale) {
        this.localScale.set(localScale);
        updateGlobalTransform();
    }
    public void setLocalScale(float localScale) {
        this.localScale.set(localScale, localScale);
        updateGlobalTransform();
    }
    public void setLocalRotation(float localRotation) {
        this.localRotation = localRotation;
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
