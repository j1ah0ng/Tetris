package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        launch(args);
    }
}
