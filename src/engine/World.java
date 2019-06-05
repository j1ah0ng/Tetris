package engine;

import java.util.ArrayList;
import java.lang.Class;
import java.util.List;
import java.util.HashSet;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * <PRE>
 * 
 * The abstract World.java class extends javafx.scene.layout.GridPane and creates a World which
 * can do many things such as act, respond to keycodes, and create a timer. This class is 
 * mainly used a super class for the TetrominoWorld class to inherit.
 * 
 * @author Ryan
 *
 *</PRE>
 */
public abstract class World extends javafx.scene.layout.GridPane {

    // Attributes
    /** Set of all keys awaiting a response. Once a key is acknowledged, it
     * should be removed from the list. */
    private HashSet<KeyCode> keys;
    private long startTimeMs;   // world start time in ms
    private AnimationTimer t;

    // Constructor
    /**
     * Default constructor that creates a new world when called. Initializes keys and sets a timer.
     */
    public World() {
        keys = new HashSet<KeyCode>();
        newTimer();
    }

    // Methods
    /**
     * <PRE>
     * An abstract class that has actors act.
     * 
     * @param now Represents the current time.
     * </PRE>
     */
    protected abstract void act(long now);

    /**
     * Starts the timer.
     */
    public void start() {
        t.start();
        startTimeMs = System.currentTimeMillis();
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        t.stop();
        newTimer();
    }

    /**
     * Creates a new timer.
     */
    private void newTimer() {
        t = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
            }
        };
    }

    /**
     * <PRE>
     * Creates a keycode for the given key.
     * @param key represents the key being added to keycode.
     * </PRE>
     */
    public void addKey(KeyCode key) { keys.add(key); }
    /**
     * <PRE>
     * removes a keycode for the given key.
     * @param key represents the key being removed from keycode.
     * </PRE>
     */
    public void removeKey(KeyCode key) { keys.remove(key); }
    /**
     * <PRE>
     * Checks whether a keycode of a certain key is already created.
     * @param key represents the key to be checked.
     * </PRE>
     */
    public boolean hasKey(KeyCode key) { return keys.contains(key); }
    /**
     * <PRE>
     * Represents the time in seconds that has elapsed.
     * @return returns the time elapsed in seconds.
     * </PRE>
     */
    public int secondsElapsed() {
        return (int) ((System.currentTimeMillis() - startTimeMs) / 1000);
    }
    /**
     * <PRE>
     * Represents the time in ms that has elapsed.
     * @return returns the time elapsed in ms.
     * </PRE>
     */
    public long msElapsed() {
        return System.currentTimeMillis() - startTimeMs;
    }
}
