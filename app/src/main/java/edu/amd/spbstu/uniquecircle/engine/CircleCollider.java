package edu.amd.spbstu.uniquecircle.engine;

import android.util.Log;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class CircleCollider extends Collider {
    private float radiusSq;

    private CircleCollider(GameObject gameObject, float radius) {
        super(gameObject);
        this.radiusSq = radius * radius;
    }

    public static CircleCollider addComponent(GameObject gameObject, float radius) {
        if (gameObject == null) {
            Log.e("CircleCollider", "null game object");

            return null;
        }

        return new CircleCollider(gameObject, radius);
    }

    @Override
    protected boolean checkCollision(float x, float y) {
        if (radiusSq == 0)
            return false;

        try {
            return world2Local(new Vector2D(x, y)).getLengthSq() <= radiusSq;
        } catch (Exception e) {
            return false;
        }
    }
}
