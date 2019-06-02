package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

	@Override
	public void start(Stage stage) throws Exception {
	    //Media m = new Media(null);//import needs verifying also
		//need audio files to check if this even works
		
		//MediaPlayer mp = new MediaPlayer(m); //import needs verifying
		
		TetrominoWorld world = new TetrominoWorld((long)(1e9));
//		world.start();
//
//		stage.setScene(new Scene(world));
//		stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> world.addKey(e.getCode()));
//		stage.setTitle("Tetris");
//		stage.setResizable(false);
//		stage.show();
//
//		world.requestFocus();
		
		stage.setTitle("Tetris");
		stage.setResizable(true);

		int w = 800;
		int h = 640;


		Image backLogo = new Image("file:assets/Backgrounds/backlogoTransparent.png");
		ImageView miv2 = new ImageView();
		miv2.setImage(backLogo);
		miv2.setFitHeight(50);
		miv2.setFitWidth(50);
		miv2.setPickOnBounds(true);
		miv2.setTranslateX(25 + (-1 * w / 2));
		miv2.setTranslateY(25 + (-1 * h / 2));

		ImageView miv3 = new ImageView();
		miv3.setImage(backLogo);
		miv3.setFitHeight(50);
		miv3.setFitWidth(50);
		miv3.setPickOnBounds(true);
		miv3.setTranslateX(25 + (-1 * w / 2));
		miv3.setTranslateY(25 + (-1 * h / 2));

		ImageView miv4 = new ImageView();
		miv4.setImage(backLogo);
		miv4.setFitHeight(50);
		miv4.setFitWidth(50);
		miv4.setPickOnBounds(true);
		miv4.setTranslateX(25 + (-1 * w / 2));
		miv4.setTranslateY(25 + (-1 * h / 2));

		// Binds scene

		StackPane bindsRoot = new StackPane();
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

		vbox1.setPadding(new Insets(h / 3, w / 2, 0, w / 2));

		bindsRoot.getChildren().addAll(vbox1, miv3);

		// Music scene

		StackPane musicRoot = new StackPane();
		Scene musicScene = new Scene(musicRoot, w, h, Color.BLACK);
		musicRoot.setStyle("-fx-background-color: #000000");

		// hard to see tetromino cursor on sliderso keep default cursor

		VBox vbox2 = new VBox();
		vbox2.setSpacing(50);
		vbox2.setLayoutX(w / 2 - 100);
		vbox2.setLayoutY(h / 2 - 100);

		Text musicTitle = new Text("MUSIC");
		musicTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		musicTitle.setFill(Color.WHITE);

		Slider slide = new Slider();
		slide.setMin(0);
		slide.setMax(100);
		slide.setShowTickMarks(true);
		slide.setMajorTickUnit(10);
		slide.setShowTickLabels(true);
		//slide.setValue(mp.getVolume());
		
		vbox2.getChildren().addAll(musicTitle, slide);

		vbox2.setPadding(new Insets(h / 3, w / 2, 0, w / 2));

		musicRoot.getChildren().addAll(vbox2, miv4);

		// Modes menu scene

		StackPane menuRoot = new StackPane();
		Scene menuScene = new Scene(menuRoot, w, h, Color.BLACK);
		menuRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Image im = new Image("file:assets/Backgrounds/cursornormal.png");
				menuScene.setCursor(new ImageCursor(im));
			}
		});

		Image menubg = new Image("file:assets/Backgrounds/menumodebackground.jpg");
		ImageView miv1 = new ImageView();

		miv1.setFitHeight(h);
		miv1.setFitWidth(w);
		miv1.setImage(menubg);

		HBox modeTitle = new HBox();
		Text title = new Text("MODES");
		title.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		title.setFill(Color.WHITE);

		modeTitle.setPadding((new Insets(50, 0, 0, w / 2 - 110)));

		modeTitle.getChildren().add(title);

		Image regular = new Image("file:assets/Backgrounds/regular.png");
		Image regularfilled = new Image("file:assets/Backgrounds/regularfilled.png");
		ImageView mreg = new ImageView();
		mreg.setImage(regular);
		mreg.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mreg.setImage(regularfilled);
			}
		});
		mreg.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mreg.setImage(regular);
			}
		});

		Image blitz = new Image("file:assets/Backgrounds/blitz.png");
		Image blitzfilled = new Image("file:assets/Backgrounds/blitzfilled.png");
		ImageView mblitz = new ImageView();
		mblitz.setImage(blitz);
		mblitz.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mblitz.setImage(blitzfilled);
			}
		});
		mblitz.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mblitz.setImage(blitz);
			}
		});

		Image multiplayer = new Image("file:assets/Backgrounds/multiplayer.png");
		Image multiplayerfilled = new Image("file:assets/Backgrounds/multiplayerfilled.png");
		ImageView mmultiplayer = new ImageView();
		mmultiplayer.setImage(multiplayer);
		mmultiplayer.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mmultiplayer.setImage(multiplayerfilled);
			}
		});
		mmultiplayer.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mmultiplayer.setImage(multiplayer);
			}
		});

		mreg.setTranslateX(-1 * w / 3);
		mblitz.setTranslateX(w / 3);
		mmultiplayer.setTranslateY(h / 3);

		menuRoot.getChildren().addAll(miv1, modeTitle, miv2, mreg, mblitz, mmultiplayer);

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
		// setfitwidth and height?
		iv2.setImage(modes);
		hb1.getChildren().add(iv2);

		ImageView iv3 = new ImageView();
		// HBox hb2 = new HBox();
		Image music = new Image("file:assets/Backgrounds/music.png");
		Image musicfilled = new Image("file:assets/Backgrounds/musicfilled.png");
		iv3.setImage(music);
		hb1.getChildren().add(iv3);

		ImageView iv4 = new ImageView();
		// HBox hb2 = new HBox();
		Image binds = new Image("file:assets/Backgrounds/binds.png");
		Image bindsfilled = new Image("file:assets/Backgrounds/bindsfilled.png");
		iv4.setImage(binds);
		hb1.getChildren().add(iv4);

		hb1.setPadding(new Insets(100, 100, 400, 100));
		hb1.setSpacing(75);

		// regular mode
		StackPane regPane = new StackPane();
		Scene regularScene = new Scene(regPane, w, h, Color.BLACK);

		Image regbg = new Image("file:assets/Backgrounds/regularmodebackground.jpg");
		ImageView riv = new ImageView();
		riv.setImage(regbg);
		riv.setFitHeight(h);
		riv.setFitWidth(w);

		regPane.getChildren().addAll(riv, world);

		mreg.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				world.start();
				stage.setScene(regularScene);
				stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> world.addKey(e.getCode()));
				
				world.requestFocus();
				
			}
		});

		// multiplayer mode
		StackPane mPane = new StackPane();
		Scene multiplayerScene = new Scene(mPane, w, h, Color.BLACK);

		Image multbg = new Image("file:assets/Backgrounds/2playerbackground.png");
		ImageView multiv = new ImageView();
		multiv.setImage(multbg);
		multiv.setFitHeight(h);
		multiv.setFitWidth(w);

		mPane.getChildren().addAll(multiv);

		mmultiplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(multiplayerScene);
			}
		});


		// StackPane regPane = new StackPane();
		StackPane blitzPane = new StackPane();

		Scene blitzScene = new Scene(blitzPane, w, h, Color.BLACK);

		Image blitzbg = new Image("file:assets/Backgrounds/blitzmodebackground.jpg");
		ImageView biv = new ImageView();
		biv.setImage(blitzbg);
		biv.setFitHeight(h);
		biv.setFitWidth(w);

		blitzPane.getChildren().addAll(biv);

		mblitz.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(blitzScene);
			}
		});

		iv2.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv2.setImage(modesfilled);
			}
		});

		iv2.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv2.setImage(modes);
			}
		});

		iv3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv3.setImage(musicfilled);
			}
		});

		iv3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv3.setImage(music);
			}
		});

		iv4.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv4.setImage(bindsfilled);
			}
		});
		iv4.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv4.setImage(binds);
			}
		});

		// Event handlers for buttons clicked

		iv2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				stage.setScene(menuScene);
			}
		});

		miv2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(s);
			}
		});

		miv3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(s);
			}
		});

		miv4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(s);
			}
		});

		iv3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				stage.setScene(musicScene);
			}
		});

		iv4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				stage.setScene(bindScene);

			}
		});

		root.getChildren().addAll(hb1);

		root.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Image im = new Image("file:assets/Backgrounds/cursornormal.png");
				s.setCursor(new ImageCursor(im));
			}
		});
		

		stage.setScene(s);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}