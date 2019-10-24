package edu.amd.spbstu.uniquecircle.engine;

import java.util.Vector;

import edu.amd.spbstu.uniquecircle.support.Vector2D;

public abstract class Collider extends Component {
    protected Collider(GameObject gameObject) {
        super(gameObject);
        gameObject.getScene().physicsScene.add(this);
    }

    abstract protected boolean checkCollision(Vector2D worldPoint);

    protected Vector2D world2Local(Vector2D worldPos) throws Exception {
        Transform transform = gameObject.getTransform();

        Vector2D scale = transform.getGlobalScale();

        if (scale.x == 0 || scale.y == 0)
            throw new Exception();

        Vector2D output = worldPos.getSubtracted(transform.getGlobalPosition());
        output.rotateBy(-transform.getGlobalRotation());
        output.multiply(new Vector2D(1 / scale.x, 1 / scale.y));

        return output;
    }

    @Override
    public void remove() {
        gameObject.getScene().physicsScene.remove(this);
        super.remove();
    }
}
