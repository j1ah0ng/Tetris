package engine;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a Tetromino object as a set of ordered pairs, in the
 * theta = 0 rotation. It will also handle the rotational translations of these
 * ordered pairs.
 */
public abstract class Tetromino extends javafx.scene.image.ImageView {
    ArrayList<ImageView> al;
    Image image;
    ImageView iv;
    abstract void rotate();
    public abstract void act(long now);
    public void move(double x, double y) {
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);
    }
}
