package engine;

import java.util.ArrayList;
import java.lang.Class;
import java.util.List;
import java.util.HashSet;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public abstract class World extends javafx.scene.layout.GridPane {

    // Attributes
    private HashSet<KeyCode> keys;
    private AnimationTimer t;

    // Constructor
    public World() {
        keys = new HashSet<KeyCode>();
        t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
            }
        };
    }

    // Methods
    public abstract void act(long now);

    public void start() {
        t.start();
    }

    public void stop() {
        t.stop();
        t = null;
    }

    public void addKey(KeyCode key) { keys.add(key); }
    public void removeKey(KeyCode key) { keys.remove(key); }
    public boolean hasKey(KeyCode key) { return keys.contains(key); }
}
