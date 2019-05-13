package engine;

import java.util.List;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.image.Image;

public abstract class Actor extends javafx.scene.image.ImageView {

    // Attributes
    
    // Constructor
    public Actor() {
        super();
        super.imageProperty().addListener((observable, oldValue, newValue) -> {
            super.setFitHeight(newValue.getHeight());
            super.setFitWidth(newValue.getWidth());
        });
    }

    public Actor(String url) {
        super(url);
        super.setFitHeight(super.getImage().getHeight());
        super.setFitWidth(super.getImage().getWidth());
        super.imageProperty().addListener((observable, oldValue, newValue) -> {
            super.setFitHeight(newValue.getHeight());
            super.setFitWidth(newValue.getWidth());
        });
    }

    public Actor(Image image) {
        super(image);
        super.setFitHeight(super.getImage().getHeight());
        super.setFitWidth(super.getImage().getWidth());
        super.imageProperty().addListener((observable, oldValue, newValue) -> {
            super.setFitHeight(newValue.getHeight());
            super.setFitWidth(newValue.getWidth());
        });
    }

    // Methods
    public abstract void act(long now);

    public double getHeight() { return super.getFitHeight(); }

    public double getWidth() { return super.getFitWidth(); }

    public <A extends Actor> List<A> getIntersectingObjects(java.lang.Class<A>
            cls) {
        World k = this.getWorld();

        // Return empty list if no engine.World exists
        if (k == null) return new ArrayList<A>();

        List<A> allActors = k.getObjects(cls);
        List<A> intersectors = new ArrayList<A>();
        
        for (A a : allActors) 
            if (super.intersects(a.getBoundsInLocal())) intersectors.add(a);

        return intersectors;
    }

    public World getWorld() { Parent p = super.getParent();

        return (p instanceof World) ? (World)p : null;

        /* TODO @sushisharkjl: Implement recursive traceback for nodes to trace
         * the real node parents. For example, if an engine.Actor has a parent Node
         * that has a parent Node which is then parented by engine.World, the uppermost
         * engine.World node gets returned. Currently, this is difficult because the
         * standard JavaFX classes may not support this. Research is needed.
         */
    }

    public void move(double x, double y) {
        super.setX(super.getX() + x);
        super.setY(super.getY() + y);
    }

    public double getXCenter() {
        return super.getX() + 0.5 * getWidth();
    }

    public double getYCenter() {
        return super.getY() + 0.5 * getHeight();
    }
}
