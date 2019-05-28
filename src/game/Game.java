package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    // Constants
    static final int w = 800;
    static final int h = 640;

    @Override
    public void start(Stage stage) throws Exception {

        // Main stage
        stage.setTitle("Tetris");
        stage.setResizable(true);
        // End main stage

        // World
        TetrominoWorld world = new TetrominoWorld(((long)1e9));
        // End world

        // Back button
        Image backLogo = new Image("file:assets/Backgrounds/backlogo.png");
        ImageView miv2 = new ImageView();
        miv2.setImage(backLogo);
        miv2.setFitHeight(50);
        miv2.setFitWidth(50);
        // End back button


        // Binds scene
        Pane bindsRoot = new Pane();
        Scene bindScene = new Scene(bindsRoot, w, h, Color.BLACK);
        bindsRoot.setStyle("-fx-background-color: #ffffff");
        bindsRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Image im = new Image("file:assets/Backgrounds/cursornormal.png");
                bindScene.setCursor(new ImageCursor(im));
            }
        });

        VBox vbox1 = new VBox();
        vbox1.setLayoutX(w / 2 - 100);
        vbox1.setLayoutY(h / 2 - 100);

        Text bindTitle = new Text("BINDS");
        bindTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
        bindTitle.setFill(Color.BLACK);

        vbox1.getChildren().add(bindTitle);

        bindsRoot.getChildren().add(vbox1);
        // End binds scene


        // Music scene
        Pane musicRoot = new Pane();
        Scene musicScene = new Scene(musicRoot, w, h, Color.BLACK);
        musicRoot.setStyle("-fx-background-color: #000000");

        //hard to see tetromino cursor on sliderso keep default cursor
        VBox vbox2 = new VBox();
        vbox2.setSpacing(50);
        vbox2.setLayoutX(w / 2 - 100);
        vbox2.setLayoutY(h / 2 - 100);

        Text musicTitle = new Text("MUSIC");
        musicTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
        musicTitle.setFill(Color.WHITE);

        /*
		Slider slide = new Slider();
		slide.setMin(0);
		slide.setMax(100);
		slide.setShowTickMarks(true);
		slide.setMajorTickUnit(10);
		slide.setShowTickLabels(true);
         */
				
		//vbox2.getChildren().addAll(musicTitle, slide);

        musicRoot.getChildren().addAll(vbox2);
        // End music scene


        // Modes menu scene
        StackPane menuRoot = new StackPane();
        Scene menuScene = new Scene(menuRoot, w, h, Color.BLACK);
        Image im = new Image("file:assets/Backgrounds/cursornormal.png");
        menuRoot.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> menuScene.setCursor(new ImageCursor(im)));
        Image menubg = new Image("file:assets/Backgrounds/menumodebackground.jpg");
        ImageView miv1 = new ImageView();
        miv1.setFitHeight(h);
        miv1.setFitWidth(w);
        miv1.setImage(menubg);

        StackPane.setAlignment(miv2, Pos.TOP_LEFT);
        menuRoot.getChildren().add(miv2);

        Text title = new Text("MODES");
        title.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
        title.setFill(Color.WHITE);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        menuRoot.getChildren().add(title);

        Image regular = new Image("file:assets/Backgrounds/regular.png");
        Image regularfilled = new Image("file:assets/Backgrounds/regularfilled.png");
        ImageView mreg = new ImageView();
        mreg.setImage(regular);
        mreg.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> mreg.setImage(regularfilled));
        mreg.addEventHandler(MouseEvent.MOUSE_EXITED, e -> mreg.setImage(regular));
        StackPane.setAlignment(mreg, Pos.CENTER_LEFT);
        menuRoot.getChildren().add(mreg);

        Image blitz = new Image("file:assets/Backgrounds/blitz.png");
        Image blitzfilled = new Image("file:assets/Backgrounds/blitzfilled.png");
        ImageView mblitz = new ImageView();
        mblitz.setImage(blitz);
        mblitz.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> mblitz.setImage(blitzfilled));
        mblitz.addEventHandler(MouseEvent.MOUSE_EXITED, e -> mblitz.setImage(blitz));
        StackPane.setAlignment(mblitz, Pos.CENTER_RIGHT);
        menuRoot.getChildren().add(mblitz);

        Image multiplayer = new Image("file:assets/Backgrounds/multiplayer.png");
        Image multiplayerfilled = new Image("file:assets/Backgrounds/multiplayerfilled.png");
        ImageView mmultiplayer = new ImageView();
        mmultiplayer.setImage(multiplayer);
        mmultiplayer.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> mmultiplayer.setImage(multiplayerfilled));
        mmultiplayer.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> mmultiplayer.setImage(multiplayer));
        StackPane.setAlignment(mmultiplayer, Pos.BOTTOM_CENTER);
        menuRoot.getChildren().add(mmultiplayer);
        // End modes scene

        // Start menu scene
        StackPane root = new StackPane();
        Scene s = new Scene(root, w, h, Color.BLACK);

        Image bg = new Image("file:assets/Backgrounds/tetrisstartmenu.png");
        ImageView iv1 = new ImageView();

        iv1.setFitWidth(w);
        iv1.setFitHeight(h);
        iv1.setImage(bg);
        root.getChildren().add(iv1);

        ImageView iv2 = new ImageView();
        HBox hb1 = new HBox();
        Image modes = new Image("file:assets/Backgrounds/modes.png");
        Image modesfilled = new Image("file:assets/Backgrounds/modesfilled.png");
        iv2.setImage(modes);
        hb1.getChildren().add(iv2);

        ImageView iv3 = new ImageView();
        Image music = new Image("file:assets/Backgrounds/music.png");
        Image musicfilled = new Image("file:assets/Backgrounds/musicfilled.png");
        iv3.setImage(music);
        hb1.getChildren().add(iv3);

        ImageView iv4 = new ImageView();
        Image binds = new Image("file:assets/Backgrounds/binds.png");
        Image bindsfilled = new Image("file:assets/Backgrounds/bindsfilled.png");
        iv4.setImage(binds);
        hb1.getChildren().add(iv4);

        hb1.setPadding(new Insets(100, 100, 400, 100));
        hb1.setSpacing(75);
            // Mouse enter events
        iv2.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> iv2.setImage(modesfilled));
        iv3.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> iv3.setImage(musicfilled));
        iv4.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> iv4.setImage(bindsfilled));
            // Mouse exit events
        iv2.addEventHandler(MouseEvent.MOUSE_EXITED, e -> iv2.setImage(modes));
        iv3.addEventHandler(MouseEvent.MOUSE_EXITED, e -> iv3.setImage(music));
        iv4.addEventHandler(MouseEvent.MOUSE_EXITED, e -> iv4.setImage(binds));
            // Back button event
        miv2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(s));
            // Click events
        iv2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(menuScene));
        iv3.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(musicScene));
        iv4.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> stage.setScene(bindScene));

        root.getChildren().addAll(hb1);
        // End start menu

        // Set cursor
        root.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Image im = new Image("file:assets/Backgrounds/cursornormal.png");
                s.setCursor(new ImageCursor(im));
            }
        });
        // End set cursor

        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
