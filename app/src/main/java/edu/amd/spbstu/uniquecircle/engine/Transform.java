package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class Transform extends Component {
    private float localScale;
    private float localRotation;
    //private vector localPosition;

    private float globalScale;
    private float globalRotation;
    //private vector localPosition;


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

    private Transform(GameObject parentObject) {
        super(parentObject);
        localScale = 1;
        children = new HashMap<>();
    }

    private void updateGlobalTransform() {
        if (parent != null) {
            globalScale = parent.globalScale * localScale;
            globalRotation = parent.globalRotation + localRotation;
            //globalPosition = parent.globalPosition + localPosition;
        }
        else {
            globalScale = localScale;
            globalRotation = localRotation;
            //globalPosition = localPosition;
        }

        for (Map.Entry<String, Transform> entry : children.entrySet())
            entry.getValue().updateGlobalTransform();
    }

    void addChild(String name, Transform child) {
        children.put(name, child);
        child.parent = this;
        child.updateGlobalTransform();
    }

    //public void setTransform(vector position, float scale, float rotation) {
    //    localScale = scale;
    //    localRotation = rotation;
    //    localPosition = position.copy();
    //    updateGlobalTransform();
    // }
    //public void setPosition(vector position) {
    //    localPosition = position.copy();
    //    updateGlobalTransform();
    //}
    public void setScale(float scale) {
        localScale = scale;
        updateGlobalTransform();
    }
    public void setRotation(float rotation) {
        localRotation = rotation;
        updateGlobalTransform();
    }

    //public vector getLocalPosition() {
    //  return localPosition;
    // }
    public float getLocalScale() {
        return localScale;
    }
    public float getLocalRotation() {
        return localRotation;
    }

    //public vector getGlobalPosition() {
    //  return globalPosition;
    // }
    public float getGlobalScale() {
        return globalScale;
    }
    public float getGlobalRotation() {
        return globalRotation;
    }

    @Override
    public void remove() {
        Log.e("Transform", "trying to remove transform");
    }
}
