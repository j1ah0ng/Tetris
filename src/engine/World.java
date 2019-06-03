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
    /** Set of all keys awaiting a response. Once a key is acknowledged, it
     * should be removed from the list. */
    private HashSet<KeyCode> keys;
    private long startTimeMs;   // world start time in ms
    private AnimationTimer t;

    // Constructor
    public World() {
        keys = new HashSet<KeyCode>();
        newTimer();
    }

    // Methods
    protected abstract void act(long now);

    public void start() {
        t.start();
        startTimeMs = System.currentTimeMillis();
    }

    public void stop() {
        t.stop();
        newTimer();
    }

    private void newTimer() {
        t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
            }
        };
    }

    public void addKey(KeyCode key) { keys.add(key); }
    public void removeKey(KeyCode key) { keys.remove(key); }
    public boolean hasKey(KeyCode key) { return keys.contains(key); }
    public int secondsElapsed() {
        return (int) ((System.currentTimeMillis() - startTimeMs) / 1000);
    }
    public long msElapsed() {
        return System.currentTimeMillis() - startTimeMs;
    }
}
