package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class CircleCollider extends Collider {
    private float radiusSq;

    private CircleCollider(GameObject gameObject, float diameter) {
        super(gameObject);
        setDiameter(diameter);
    }

    public static CircleCollider addComponent(GameObject gameObject, float radius) {
        if (gameObject == null) {
            Log.e("CircleCollider", "null game object");

            return null;
        }

        return new CircleCollider(gameObject, radius);
    }

    public void setDiameter(float diameter) {
        radiusSq = diameter * diameter / 4;
    }

    @Override
    protected boolean checkCollision(Vector2D worldPoint) {
        if (radiusSq == 0)
            return false;

        try {
            return world2Local(worldPoint).getLengthSq() <= radiusSq;
        } catch (Exception e) {
            return false;
        }
    }
}
