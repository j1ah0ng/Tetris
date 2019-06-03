package game;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
		Media media = new Media("file:/C:/Users/pc/Desktop/Tetris/assets/sound/tetris-classic.mp3");
	    MediaPlayer mp = new MediaPlayer(media); 
	    mp.play();
	       
	    KeyCode kdown = KeyCode.S;
	    KeyCode kright = KeyCode.D;
	    KeyCode kleft = KeyCode.A;
		KeyCode krotate = KeyCode.R;
	    KeyCode kdrop = KeyCode.SPACE;
	    KeyCode kmpfrenzy = KeyCode.F;
	    KeyCode kmpdrop = KeyCode.G;

	    KeyCode kdown2 = KeyCode.DOWN;
	    KeyCode kright2 = KeyCode.RIGHT;
	    KeyCode kleft2 = KeyCode.LEFT;
	    KeyCode krotate2 = KeyCode.COMMA;
	    KeyCode kdrop2 = KeyCode.PERIOD;
	    KeyCode kmpfrenzy2 = KeyCode.O;
	    KeyCode kmpdrop2 = KeyCode.P;

		stage.setTitle("Tetris");
		stage.setResizable(false);

		int w = 800;
		int h = 640;

		Image backLogo = new Image("file:assets/Backgrounds/backlogoTransparent.png");
		ImageView miv2 = new ImageView();
		miv2.setImage(backLogo);
		miv2.setFitHeight(50);
		miv2.setFitWidth(50);
		miv2.setTranslateX(25 + (-1 * w / 2));
		miv2.setTranslateY(25 + (-1 * h / 2));

		ImageView miv3 = new ImageView();
		miv3.setImage(backLogo);
		miv3.setFitHeight(50);
		miv3.setFitWidth(50);
		miv3.setTranslateX(25 + (-1 * w / 2));
		miv3.setTranslateY(25 + (-1 * h / 2));

		ImageView miv4 = new ImageView();
		miv4.setImage(backLogo);
		miv4.setFitHeight(50);
		miv4.setFitWidth(50);
		miv4.setTranslateX(25 + (-1 * w / 2));
		miv4.setTranslateY(25 + (-1 * h / 2));

		// Binds scene

		StackPane bindsRoot = new StackPane();
		Scene bindScene = new Scene(bindsRoot, w, h, Color.BLACK);
		TextField t = new TextField();
		t.setTranslateY(-h);
		bindsRoot.getChildren().addAll(t);
		bindsRoot.setStyle("-fx-background-color: #000000");
		bindsRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Image im = new Image("file:assets/Backgrounds/cursornormal.png");
				bindScene.setCursor(new ImageCursor(im));
			}
		});

		VBox vbox1 = new VBox();
		vbox1.setLayoutY(-h);

		Text bindTitle = new Text("BINDS");
		bindTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		bindTitle.setFill(Color.GOLD);

		vbox1.getChildren().add(bindTitle);

		vbox1.setPadding(new Insets(h/30, w / 2, 0, w / 2));

		bindsRoot.getChildren().addAll(vbox1, miv3);
		
		Image n1= new Image("file:assets/Backgrounds/1.png");
		Image n1filled = new Image("file:assets/Backgrounds/1filled.png");
		ImageView num1 = new ImageView(n1filled);
		
		Image n2 = new Image("file:assets/Backgrounds/2.png");
		Image n2filled = new Image("file:assets/Backgrounds/2filled.png");
		ImageView num2 = new ImageView(n2);
		num2.setFitWidth(45);

		
		num1.setTranslateX(0.28125*w);
		num2.setTranslateX(0.3625*w);
		
		num1.setTranslateY(-0.390625*h);
		num2.setTranslateY(-0.390625*h);
		
		bindsRoot.getChildren().addAll(num1, num2);
		
		Text down = new Text("DOWN");
		Text right = new Text("RIGHT");
		Text left = new Text("LEFT");
		Text rotate = new Text("ROTATE");
		Text drop = new Text("DROP");
		Text mpfrenzy = new Text("MP FRENZY");
		Text mpdrop = new Text("MP DROP");

		down.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		right.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		left.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		rotate.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		drop.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		mpfrenzy.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		mpdrop.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		
		down.setFill(Color.GOLD);
		right.setFill(Color.GOLD);
		left.setFill(Color.GOLD);
		rotate.setFill(Color.GOLD);
		drop.setFill(Color.GOLD);
		mpfrenzy.setFill(Color.GOLD);
		mpdrop.setFill(Color.GOLD);
		
		down.setTranslateX(-1*w/3);
		right.setTranslateX(-1*w/3);
		left.setTranslateX(-1*w/3);
		rotate.setTranslateX(-1*w/3);
		drop.setTranslateX(-1*w/3);
		mpfrenzy.setTranslateX(-1*w/3);
		mpdrop.setTranslateX(-1*w/3);

		down.setTranslateY(-0.265625*h);
		right.setTranslateY(-0.1484375*h);
		left.setTranslateY(-0.03125*h);
		rotate.setTranslateY(0.0859375*h);
		drop.setTranslateY(0.203125*h);
		mpfrenzy.setTranslateY(0.3203125*h);
		mpdrop.setTranslateY(0.4375*h);
		
		bindsRoot.getChildren().addAll(down, right, left, rotate, drop, mpfrenzy, mpdrop);

		Image change = new Image("file:assets/Backgrounds/change.png");
		Image changefilled = new Image("file:assets/Backgrounds/changefilled.png");
		
		ImageView ivchange1 = new ImageView(change);
		ImageView ivchange2 = new ImageView(change);
		ImageView ivchange3 = new ImageView(change);
		ImageView ivchange4 = new ImageView(change);
		ImageView ivchange5 = new ImageView(change);
		ImageView ivchange6 = new ImageView(change);
		ImageView ivchange7 = new ImageView(change);
		
		ivchange1.setPreserveRatio(true);
		ivchange2.setPreserveRatio(true);
		ivchange3.setPreserveRatio(true);
		ivchange4.setPreserveRatio(true);
		ivchange5.setPreserveRatio(true);
		ivchange6.setPreserveRatio(true);
		ivchange7.setPreserveRatio(true);
		
		ivchange1.setFitWidth(120);
		ivchange2.setFitWidth(120);
		ivchange3.setFitWidth(120);
		ivchange4.setFitWidth(120);
		ivchange5.setFitWidth(120);
		ivchange6.setFitWidth(120);
		ivchange7.setFitWidth(120);

		ivchange1.setTranslateX(1*w/3);
		ivchange2.setTranslateX(1*w/3);
		ivchange3.setTranslateX(1*w/3);
		ivchange4.setTranslateX(1*w/3);
		ivchange5.setTranslateX(1*w/3);
		ivchange6.setTranslateX(1*w/3);
		ivchange7.setTranslateX(1*w/3);

		ivchange1.setTranslateY(-0.265625*h);
		ivchange2.setTranslateY(-0.1484375*h);
		ivchange3.setTranslateY(-0.03125*h);
		ivchange4.setTranslateY(0.0859375*h);
		ivchange5.setTranslateY(0.203125*h);
		ivchange6.setTranslateY(0.3203125*h);
		ivchange7.setTranslateY(0.4375*h);
				
		bindsRoot.getChildren().addAll(ivchange1, ivchange2, ivchange3, ivchange4, ivchange5, ivchange6, ivchange7);

		Text tdown = new Text(kdown.getName());
		Text tright = new Text(kright.getName());
		Text tleft = new Text(kleft.getName());
		Text trotate = new Text(krotate.getName());
		Text tdrop = new Text(kdrop.getName());
		Text tmpfrenzy = new Text(kmpfrenzy.getName());
		Text tmpdrop = new Text(kmpdrop.getName());
		
		tdown.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tright.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tleft.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		trotate.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tdrop.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tmpfrenzy.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tmpdrop.setFont(Font.font("Impact", FontWeight.NORMAL, 30));

		tdown.setFill(Color.GOLD);
		tright.setFill(Color.GOLD);
		tleft.setFill(Color.GOLD);
		trotate.setFill(Color.GOLD);
		tdrop.setFill(Color.GOLD);
		tmpfrenzy.setFill(Color.GOLD);
		tmpdrop.setFill(Color.GOLD);

		tdown.setTranslateY(-0.265625*h);
		tright.setTranslateY(-0.1484375*h);
		tleft.setTranslateY(-0.03125*h);
		trotate.setTranslateY(0.0859375*h);
		tdrop.setTranslateY(0.203125*h);
		tmpfrenzy.setTranslateY(0.3203125*h);
		tmpdrop.setTranslateY(0.4375*h);
		
		Text tdown2 = new Text(kdown2.getName());
		Text tright2 = new Text(kright2.getName());
		Text tleft2 = new Text(kleft2.getName());
		Text trotate2 = new Text(krotate2.getName());
		Text tdrop2 = new Text(kdrop2.getName());
		Text tmpfrenzy2 = new Text(kmpfrenzy2.getName());
		Text tmpdrop2 = new Text(kmpdrop2.getName());
		
		tdown2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tright2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tleft2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		trotate2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tdrop2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tmpfrenzy2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));
		tmpdrop2.setFont(Font.font("Impact", FontWeight.NORMAL, 30));

		tdown2.setFill(Color.TRANSPARENT);
		tright2.setFill(Color.TRANSPARENT);
		tleft2.setFill(Color.TRANSPARENT);
		trotate2.setFill(Color.TRANSPARENT);
		tdrop2.setFill(Color.TRANSPARENT);
		tmpfrenzy2.setFill(Color.TRANSPARENT);
		tmpdrop2.setFill(Color.TRANSPARENT);

		tdown2.setTranslateY(-0.265625*h);
		tright2.setTranslateY(-0.1484375*h);
		tleft2.setTranslateY(-0.03125*h);
		trotate2.setTranslateY(0.0859375*h);
		tdrop2.setTranslateY(0.203125*h);
		tmpfrenzy2.setTranslateY(0.3203125*h);
		tmpdrop2.setTranslateY(0.4375*h);
		
		
				
		bindsRoot.getChildren().addAll(tdown, tright, tleft, trotate, tdrop, tmpfrenzy, tmpdrop, 
				tdown2, tright2, tleft2, trotate2, tdrop2, tmpfrenzy2, tmpdrop2);
		
		// Music scene

		StackPane musicRoot = new StackPane();
		Scene musicScene = new Scene(musicRoot, w, h, Color.BLACK);
		musicRoot.setStyle("-fx-background-color: #000000");

		// hard to see tetromino cursor on slider so keep default cursor

		VBox vbox2 = new VBox();
		vbox2.setSpacing(50);
		vbox2.setLayoutX(w / 2 - 100);
		vbox2.setLayoutY(h / 2 - 100);

		Text musicTitle = new Text("MUSIC");
		musicTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		musicTitle.setFill(Color.WHITE);

		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(1);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.1);
		slider.setShowTickLabels(true);
		slider.setValue(mp.getVolume());
		System.out.println(mp.getVolume());
		slider.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mp.setVolume(slider.getValue());
			}
		});
		
		vbox2.getChildren().addAll(musicTitle, slider);

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

		Image bg = new Image("file:assets/Backgrounds/tetrisstartmenu2.png");
		ImageView iv1 = new ImageView();

		iv1.setFitWidth(w);
		iv1.setFitHeight(h);
		iv1.setImage(bg);
		root.getChildren().add(iv1);

		ImageView iv2 = new ImageView();
		Image modes = new Image("file:assets/Backgrounds/modes.png");
		Image modesfilled = new Image("file:assets/Backgrounds/modesfilled.png");
		iv2.setImage(modes);
		iv2.setTranslateY(0);

		ImageView iv3 = new ImageView();
		Image music = new Image("file:assets/Backgrounds/music.png");
		Image musicfilled = new Image("file:assets/Backgrounds/musicfilled.png");
		iv3.setImage(music);
		iv3.setTranslateY(0.17578125*h);

		ImageView iv4 = new ImageView();
		Image binds = new Image("file:assets/Backgrounds/binds.png");
		Image bindsfilled = new Image("file:assets/Backgrounds/bindsfilled.png");
		iv4.setImage(binds);
		iv4.setTranslateY(0.3515625*h);

		// regular mode
		StackPane regPane = new StackPane();
		Scene regularScene = new Scene(regPane, w, h, Color.BLACK);

		Image regbg = new Image("file:assets/Backgrounds/regularmodebackground.jpg");
		ImageView riv = new ImageView();
		riv.setImage(regbg);
		riv.setFitHeight(h);
		riv.setFitWidth(w);

		regPane.getChildren().addAll(riv);

		mreg.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(regularScene);
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

		root.getChildren().addAll(iv2, iv3, iv4);

		root.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Image im = new Image("file:assets/Backgrounds/cursornormal.png");
				s.setCursor(new ImageCursor(im));
			}
		});
		
		ivchange1.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange1.setImage(changefilled);
			}
		});
		
		ivchange2.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange2.setImage(changefilled);
			}
		});
		
		ivchange3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange3.setImage(changefilled);
			}
		});
		
		ivchange4.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange4.setImage(changefilled);
			}
		});
		
		ivchange5.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange5.setImage(changefilled);
			}
		});
		
		ivchange6.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange6.setImage(changefilled);
			}
		});
		
		ivchange7.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange7.setImage(changefilled);
			}
		});
		
		ivchange1.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange1.setImage(change);
			}
		});
		
		ivchange2.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange2.setImage(change);
			}
		});
		
		ivchange3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange3.setImage(change);
			}
		});
		
		ivchange4.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange4.setImage(change);
			}
		});
		
		ivchange5.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange5.setImage(change);
			}
		});
		
		ivchange6.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange6.setImage(change);
			}
		});
		
		ivchange7.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivchange7.setImage(change);
			}
		});
		
		num1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (num1.getImage().equals(n1)) {
					num1.setImage(n1filled);
					num2.setImage(n2);
					tdown.setFill(Color.GOLD);
					tright.setFill(Color.GOLD);
					tleft.setFill(Color.GOLD);
					trotate.setFill(Color.GOLD);
					tdrop.setFill(Color.GOLD);
					tmpfrenzy.setFill(Color.GOLD);
					tmpdrop.setFill(Color.GOLD);
					tdown2.setFill(Color.TRANSPARENT);
					tright2.setFill(Color.TRANSPARENT);
					tleft2.setFill(Color.TRANSPARENT);
					trotate2.setFill(Color.TRANSPARENT);
					tdrop2.setFill(Color.TRANSPARENT);
					tmpfrenzy2.setFill(Color.TRANSPARENT);
					tmpdrop2.setFill(Color.TRANSPARENT);
				}
			}
		});
		
		num2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (num2.getImage().equals(n2)) {
					num2.setImage(n2filled);
					num1.setImage(n1);
					tdown.setFill(Color.TRANSPARENT);
					tright.setFill(Color.TRANSPARENT);
					tleft.setFill(Color.TRANSPARENT);
					trotate.setFill(Color.TRANSPARENT);
					tdrop.setFill(Color.TRANSPARENT);
					tmpfrenzy.setFill(Color.TRANSPARENT);
					tmpdrop.setFill(Color.TRANSPARENT);
					tdown2.setFill(Color.GOLD);
					tright2.setFill(Color.GOLD);
					tleft2.setFill(Color.GOLD);
					trotate2.setFill(Color.GOLD);
					tdrop2.setFill(Color.GOLD);
					tmpfrenzy2.setFill(Color.GOLD);
					tmpdrop2.setFill(Color.GOLD);
				}
			}
		});
		
		Alert alert = new Alert(AlertType.NONE, "Click OK then enter button on keyboard",
				ButtonType.OK);
		alert.setTitle("Button Change");
		
		ivchange1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kdown = ke.getCode();
							tdown.setText(kdown.getName());
							t.setDisable(true);
						} else {
							KeyCode kdown2 = ke.getCode();
							tdown2.setText(kdown2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kright = ke.getCode();
							tright.setText(kright.getName());
							t.setDisable(true);
						} else {
							KeyCode kright2 = ke.getCode();
							tright2.setText(kright2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kleft = ke.getCode();
							tleft.setText(kleft.getName());
							t.setDisable(true);
						} else {
							KeyCode kleft2 = ke.getCode();
							tleft2.setText(kleft2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode krotate = ke.getCode();
							trotate.setText(krotate.getName());
							t.setDisable(true);
						} else {
							KeyCode krotate2 = ke.getCode();
							trotate2.setText(krotate2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kdrop = ke.getCode();
							tdrop.setText(kdrop.getName());
							t.setDisable(true);
						} else {
							KeyCode kdrop2 = ke.getCode();
							tdrop2.setText(kdrop2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange6.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kmpfrenzy = ke.getCode();
							tmpfrenzy.setText(kmpfrenzy.getName());
							t.setDisable(true);
						} else {
							KeyCode kmpfrenzy2 = ke.getCode();
							tmpfrenzy2.setText(kmpfrenzy2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		ivchange7.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				alert.showAndWait();
				t.setDisable(false);
				t.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (num1.getImage().equals(n1filled)) {
							KeyCode kmpdrop = ke.getCode();
							tmpdrop.setText(kmpdrop.getName());
							t.setDisable(true);
						} else {
							KeyCode kmpdrop2 = ke.getCode();
							tmpdrop2.setText(kmpdrop2.getName());
							t.setDisable(true);
						}
					}
				});
			}
		});
		
		stage.setScene(s);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void endGame() {
		Platform.exit();
	}
}