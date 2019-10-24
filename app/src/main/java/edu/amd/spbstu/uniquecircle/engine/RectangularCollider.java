package edu.amd.spbstu.uniquecircle.engine;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public class RectangularCollider extends Collider {
    public float width;
    public float height;

    private RectangularCollider(GameObject gameObject,
                                float width, float height) {
        super(gameObject);

        this.width = width;
        this.height = height;
    }

    @Override
    protected boolean checkCollision(Vector2D worldPoint) {
        if (width == 0 || height == 0)
            return false;

        try {
            Vector2D local = world2Local(worldPoint);

            return local.x >= -width / 2.0f && local.x <= width / 2.0f &&
                    local.y >= -height / 2.0f && local.y <= height / 2.0f;
        } catch (Exception e) {
            return false;
        }
    }
}
