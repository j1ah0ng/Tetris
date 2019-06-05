package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
 * <PRE>
 * The Test.java class is a class which tests the implementation of a TetrominoWorld into a scene.
 * 
 * @author Ryan
 * </PRE>
 */
public class Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        TetrominoWorld world = new TetrominoWorld(null , (long)1e9);
        world.start();

        stage.setScene(new Scene(world));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> world.addKey(e.getCode()));
        stage.setTitle("Tetris");
        stage.setResizable(false);
        stage.show();

        world.requestFocus();
    }
    /**
	 * <PRE>
	 * The main method launches the game, specifically through the start method, which shows the GUI.
	 * 
	 * @param args Contains the supplied command line arguments through an array of strings.
	 * </PRE>
	 */
    public static void main(String[] args) {
        launch(args);
    }
}
