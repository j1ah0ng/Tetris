package game;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <PRE>
 * The Game class sets up the GUI environment for the entire project. It handles everything you see including all the menus, animations,
 *  backgrounds, icons, buttons and logic in Tetris (except for the playable GridPane). It is also responsible for controlling media and
 *  creating new Worlds. 
 * @author Mario
 *</PRE>
 */
public class Game extends Application {
    /** music file */
	File f = new File("tetris-classic.mp3");
    /** makes a media object out of the music file */
	Media media = new Media(f.toURI().toString());
    /** forms a new multimedia player object initialized to media */
    MediaPlayer mp = new MediaPlayer(media);
    /** creates a new StackPane for regular mode */
	StackPane regPane = new StackPane();
    /** creates a new StackPane for multiplayer mode */
	StackPane mPane = new StackPane();
    /** creates a new blitz Pane for regular mode */
	StackPane blitzPane = new StackPane();
    /** creates a new blur effect */
	BoxBlur blur = new BoxBlur();
    /** creates a new rectangle */
	Rectangle r = new Rectangle();
    /** creates a new text object */
	Text tgameOver = new Text("GAME OVER");
	
    /** image for the retry button */
	Image retry = new Image("file:assets/backgrounds/retryred.png");
    /** image for the filled retry button */
	Image retryfilled = new Image("file:assets/backgrounds/retryfilled.png");

    /** image for the home button */
	Image home = new Image("file:assets/backgrounds/homered.png");
    /** image for the filled home button */
	Image homefilled = new Image("file:assets/backgrounds/homefilled.png");

    /** imageview object for the retry button initialized to the retry button */
	ImageView ivretry = new ImageView(retry);
    /** imageview object for the home button initialized to the home button*/
	ImageView ivhome = new ImageView(home);
	
    /** boolean attribute that keeps track of whether you're in regular mode or not*/
	boolean isregular = false;
    /** boolean attribute that keeps track of whether you're in multiplayer mode or not*/
	boolean ismultiplayer = false;
    /** boolean attribute that keeps track of whether you're in blitz mode or not*/
	boolean isblitz = false;
	
    /** text attribute that says "PLAYER 1 WINS" (not initialized yet)*/
	Text player1wins;
    /** text attribute that says "PLAYER 2 WINS" (not initialized yet)*/
	Text player2wins;
    /** text attribute that says "TIE" (not initialized yet)*/
	Text tie;
	
    /** text attribute that says "SCORE" (not initialized yet)*/
	Text scoreText;
    /** text attribute that displays the score (not initialized yet)*/
	Score score1;
    /** extra text attribute that displays player 2's score (might not be needed)*/
	Score score2;
	
    /** image for regular mode background*/
	Image regbg;
    /** imageview that contains the regular mode background*/
	ImageView riv;
	
    /** image for multiplayer mode background*/
	Image multbg;
    /** imageview that contains the multiplayer mode background*/
	ImageView multiv;
	
    /** image for blitz mode background*/
	Image blitzbg;
    /** imageview that contains the blitz mode background*/
	ImageView biv;
	
    /** regular mode tetrominoWorld object*/
	TetrominoWorld regularWorld;
    /** blitz mode tetrominoWorld object*/
	TetrominoWorld blitzWorld;
    /** multiplayer player 1 tetrominoWorld object*/
	TetrominoWorld mpWorldA;
    /** multiplayer player 2 tetrominoWorld object*/
	TetrominoWorld mpWorldB;
	
	/**
	 * <PRE>
	 * sets up and contains the logic for music and everything visible in Tetris (except gridpane) and launches TetrominoWorld objects. 
	 * @author Mario
	 *</PRE>
	 */
	@Override
	public void start(Stage stage) throws Exception {
	    mp.setVolume(0.5);
	    mp.play();
	    mp.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				mp.seek(Duration.ZERO);
				mp.play();
			}
	    });

		int w = 800;
		int h = 640;
		
	    //default keycodes
	    /** player 1 default down keycode*/
	    KeyCode kdown = KeyCode.S;
	    /** player 1 default right keycode*/
	    KeyCode kright = KeyCode.D;
	    /** player 1 default left keycode*/
	    KeyCode kleft = KeyCode.A;
	    /** player 1 default rotate keycode*/
		KeyCode krotate = KeyCode.R;
	    /** player 1 default drop keycode*/
	    KeyCode kdrop = KeyCode.SPACE;
	    /** player 1 default frenzy keycode(only in multiplayer)*/
	    KeyCode kmpfrenzy = KeyCode.F;
	    /** player 1 default drop keycode(only in multiplayer)*/
	    KeyCode kmpdrop = KeyCode.G;

	    /** player 2 default down keycode*/
	    KeyCode kdown2 = KeyCode.DOWN;
	    /** player 2 default right keycode*/
	    KeyCode kright2 = KeyCode.RIGHT;
	    /** player 2 default left keycode*/
	    KeyCode kleft2 = KeyCode.LEFT;
	    /** player 2 default rotate keycode*/
	    KeyCode krotate2 = KeyCode.COMMA;
	    /** player 2 default drop keycode*/
	    KeyCode kdrop2 = KeyCode.PERIOD;
	    /** player 2 default frenzy keycode(only in multiplayer)*/
	    KeyCode kmpfrenzy2 = KeyCode.O;
	    /** player 2 default drop  keycode(only in multiplayer)*/
	    KeyCode kmpdrop2 = KeyCode.P;

	    player1wins = new Text("PLAYER 1 WINS");
		player2wins = new Text("PLAYER 2 WINS");
		tie = new Text ("TIE");
		
	    player1wins.setFill(Color.GOLD);
	    player2wins.setFill(Color.GOLD);
	    tie.setFill(Color.GOLD);

		player1wins.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 40));
		player2wins.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 40));
		tie.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 40));

		player1wins.setTranslateY(-1*h/10);
		player2wins.setTranslateY(-1*h/10);
		tie.setTranslateY(-1*h/10);
		
	    scoreText = new Text("SCORE");
	    score1 = new Score(); //score of player1
	    score2 = new Score(); //score player2 (if needed)
	    
	    scoreText.setFill(Color.GOLD);
		scoreText.setFont(new Font("Chewy", 25));
		score1.setTranslateX(-270);
		score1.setTranslateY(-160);
		scoreText.setTranslateX(-270);
		scoreText.setTranslateY(-210);
		
		stage.setTitle("Tetris");
		stage.setResizable(true);
		
	    /** backlogo image*/
		Image backLogo = new Image("file:assets/backgrounds/backlogoTransparent.png");
	    /** imageview object for backlogo image*/
		ImageView miv2 = new ImageView();
		miv2.setImage(backLogo);
		miv2.setFitHeight(50);
		miv2.setFitWidth(50);
		miv2.setTranslateX(25 + (-1 * w / 2));
		miv2.setTranslateY(25 + (-1 * h / 2));

	    /** 2nd imageview object for backlogo image*/
		ImageView miv3 = new ImageView();
		miv3.setImage(backLogo);
		miv3.setFitHeight(50);
		miv3.setFitWidth(50);
		miv3.setTranslateX(25 + (-1 * w / 2));
		miv3.setTranslateY(25 + (-1 * h / 2));

	    /** 3rd imageview object for backlogo image*/
		ImageView miv4 = new ImageView();
		miv4.setImage(backLogo);
		miv4.setFitHeight(50);
		miv4.setFitWidth(50);
		miv4.setTranslateX(25 + (-1 * w / 2));
		miv4.setTranslateY(25 + (-1 * h / 2));

		// Binds scene

	    /** default pane for binds*/
		StackPane bindsRoot = new StackPane();
	    /** represents the scene in binds*/
		Scene bindScene = new Scene(bindsRoot, w, h, Color.BLACK);
	    /**represents a textfield object that is needed to implement keycode functionality*/
		TextField t = new TextField();
		t.setTranslateY(-h);
		bindsRoot.getChildren().addAll(t);
		bindsRoot.setStyle("-fx-background-color: #000000");
		bindsRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				/** custom cursor image*/
				Image im = new Image("file:assets/backgrounds/cursornormal.png");
				bindScene.setCursor(new ImageCursor(im));
			}
		});

	    /** vbox that contains the bind title*/
		VBox vbox1 = new VBox();
		vbox1.setLayoutY(-h);
		
	    /** text that says "BINDS". Also, it's the title of the binds scene*/
		Text bindTitle = new Text("BINDS");
		bindTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 75));
		bindTitle.setFill(Color.GOLD);

		vbox1.getChildren().add(bindTitle);

		vbox1.setPadding(new Insets(h/30, w / 2, 0, w / 2));

		bindsRoot.getChildren().addAll(vbox1, miv3);
		
	    /** number 1 image*/
		Image n1= new Image("file:assets/backgrounds/1.png");
	    /** number 1 filled image*/
		Image n1filled = new Image("file:assets/backgrounds/1filled.png");
	    /** imageview object for number 1*/
		ImageView num1 = new ImageView(n1filled);
		
	    /** number 2 image*/
		Image n2 = new Image("file:assets/backgrounds/2.png");
	    /** number 2 filled image*/
		Image n2filled = new Image("file:assets/backgrounds/2filled.png");
	    /** imageview object for number 2*/
		ImageView num2 = new ImageView(n2);
		num2.setFitWidth(45);

		
		num1.setTranslateX(0.28125*w);
		num2.setTranslateX(0.3625*w);
		
		num1.setTranslateY(-0.390625*h);
		num2.setTranslateY(-0.390625*h);
		
		bindsRoot.getChildren().addAll(num1, num2);
		
	    /** text on the left in the bind scene that specifies which button is down*/
		Text down = new Text("DOWN");
	    /** text on the left in the bind scene that specifies which button is right*/
		Text right = new Text("RIGHT");
	    /** text on the left in the bind scene that specifies which button is left*/
		Text left = new Text("LEFT");
	    /** text on the left in the bind scene that specifies which button is rotate*/
		Text rotate = new Text("ROTATE");
	    /** text on the left in the bind scene that specifies which button is drop*/
		Text drop = new Text("DROP");
	    /** text on the left in the bind scene that specifies which button is mp frenzy*/
		Text mpfrenzy = new Text("MP FRENZY");
	    /** text on the left in the bind scene that specifies which button is mp drop*/
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

	    /** image for change button*/
		Image change = new Image("file:assets/backgrounds/change.png");
	    /** image for change button filled*/
		Image changefilled = new Image("file:assets/backgrounds/changefilled.png");
		
	    /** change button in binds scene #1*/
		ImageView ivchange1 = new ImageView(change);
	    /** change button in binds scene #2*/
		ImageView ivchange2 = new ImageView(change);
	    /** change button in binds scene #3*/
		ImageView ivchange3 = new ImageView(change);
	    /** change button in binds scene #4*/
		ImageView ivchange4 = new ImageView(change);
	    /** change button in binds scene #5*/
		ImageView ivchange5 = new ImageView(change);
	    /** change button in binds scene #6*/
		ImageView ivchange6 = new ImageView(change);
	    /** change button in binds scene #7*/
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

	    /** displays current down key for player 1*/
		Text tdown = new Text(kdown.getName());
	    /** displays current right key for player 1*/
		Text tright = new Text(kright.getName());
	    /** displays current left key for player 1*/
		Text tleft = new Text(kleft.getName());
	    /** displays current rotate key for player 1*/
		Text trotate = new Text(krotate.getName());
	    /** displays current drop key for player 1*/
		Text tdrop = new Text(kdrop.getName());
	    /** displays current mp frenzy key for player 1(only in multiplayer)*/
		Text tmpfrenzy = new Text(kmpfrenzy.getName());
	    /** displays current mp drop key for player 1(only in multiplayer)*/
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
		
	    /** displays current down key for player 2*/
		Text tdown2 = new Text(kdown2.getName());
	    /** displays current right key for player 2*/
		Text tright2 = new Text(kright2.getName());
	    /** displays current left key for player 2*/
		Text tleft2 = new Text(kleft2.getName());
	    /** displays current rotate key for player 2*/
		Text trotate2 = new Text(krotate2.getName());
	    /** displays current drop key for player 2*/
		Text tdrop2 = new Text(kdrop2.getName());
	    /** displays current mp frenzy key for player 2(only in multiplayer)*/
		Text tmpfrenzy2 = new Text(kmpfrenzy2.getName());
	    /** displays current mp drop key for player 2(only in multiplayer)*/
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

	    /** the type of pane in the music scene*/
		StackPane musicRoot = new StackPane();
	    /** scene of the music root*/
		Scene musicScene = new Scene(musicRoot, w, h, Color.BLACK);
	    /** background image of music*/
		Image musicim = new Image("file:assets/backgrounds/musicbackground.jpg");
	    /** imageview for background image of music*/
		ImageView ivmusic = new ImageView(musicim);
		ivmusic.setFitWidth(800);
		ivmusic.setFitHeight(640);
		musicRoot.getChildren().add(ivmusic);

		// hard to see tetromino cursor on slider so keep default cursor

	    /** vbox that contains the music scene title*/
		VBox vbox2 = new VBox();
		vbox2.setSpacing(50);
		vbox2.setLayoutX(w / 2 - 0.125*w);
		vbox2.setLayoutY(h / 2 - 0.16666667*h);

	    /** music scene title*/
		Text musicTitle = new Text("MUSIC");
		musicTitle.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		musicTitle.setFill(Color.WHITE);

	    /** slider to control volume*/
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(1);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.1);
		slider.setShowTickLabels(true);
		slider.setValue(mp.getVolume());
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
		
	    /** type of pane in the menu scene*/
		StackPane menuRoot = new StackPane();
	    /** scene for the menu*/
		Scene menuScene = new Scene(menuRoot, w, h, Color.BLACK);
		menuRoot.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {   
				/** custom cursor image*/
				Image im = new Image("file:assets/backgrounds/cursornormal.png");
				menuScene.setCursor(new ImageCursor(im));
			}
		});

	    /** background for the menu scene (where you chose modes)*/
		Image menubg = new Image("file:assets/backgrounds/menumodebackground.jpg");
	    /** imageview object for the background menu*/
		ImageView miv1 = new ImageView();

		miv1.setFitHeight(h);
		miv1.setFitWidth(w);
		miv1.setImage(menubg);

	    /** hbox for modes scene title*/
		HBox modeTitle = new HBox();
	    /** modes scene title text*/
		Text title = new Text("MODES");
		title.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 80));
		title.setFill(Color.WHITE);

		modeTitle.setPadding((new Insets(50, 0, 0, w / 2 - 110)));

		modeTitle.getChildren().add(title);

	    /** image of the regular button*/
		Image regular = new Image("file:assets/backgrounds/regular.png");
	    /** image of the regular button filled*/
		Image regularfilled = new Image("file:assets/backgrounds/regularfilled.png");
	    /** imageview of the regular button filled*/
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

	    /** image of the blitz button*/
		Image blitz = new Image("file:assets/backgrounds/blitz.png");
	    /** image of the blitz button filled*/
		Image blitzfilled = new Image("file:assets/backgrounds/blitzfilled.png");
	    /** imageview of the blitz button*/
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

	    /** image of the multiplayer button*/
		Image multiplayer = new Image("file:assets/backgrounds/multiplayer.png");
	    /** image of the multiplayer button filled*/
		Image multiplayerfilled = new Image("file:assets/backgrounds/multiplayerfilled.png");
		/** imageview of the multiplayer button filled*/
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

	    /** root pane for the start menu*/
		StackPane root = new StackPane();
		
	    /** stat menu scene*/
		Scene s = new Scene(root, w, h, Color.BLACK);

	    /** background image of start menu*/
		Image bg = new Image("file:assets/backgrounds/tetrisstartmenu2.png");
	    /** imageview object of the background of the start menu*/
		ImageView iv1 = new ImageView();

		iv1.setFitWidth(w);
		iv1.setFitHeight(h);
		iv1.setImage(bg);
		root.getChildren().add(iv1);

	    /** imageview object*/
		ImageView iv2 = new ImageView();
	    /** background image for the modes button*/
		Image modes = new Image("file:assets/backgrounds/modes.png");
	    /** background image for the modes button filled*/
		Image modesfilled = new Image("file:assets/backgrounds/modesfilled.png");
		iv2.setImage(modes);
		iv2.setTranslateY(0);

	    /** imageview object*/
		ImageView iv3 = new ImageView();
	    /** background image for the music button*/
		Image music = new Image("file:assets/backgrounds/music.png");
	    /** background image for the music button filled*/
		Image musicfilled = new Image("file:assets/backgrounds/musicfilled.png");
		iv3.setImage(music);
		iv3.setTranslateY(0.17578125*h);

	    /** imageview object*/
		ImageView iv4 = new ImageView();
	    /** background image for the binds button*/
		Image binds = new Image("file:assets/backgrounds/binds.png");
	    /** background image for the binds button filled*/
		Image bindsfilled = new Image("file:assets/backgrounds/bindsfilled.png");
		iv4.setImage(binds);
		iv4.setTranslateY(0.3515625*h);

		// regular mode
	    /** scene for the regular mode*/
		Scene regularScene = new Scene(regPane, w, h, Color.BLACK);

		regbg = new Image("file:assets/backgrounds/regularmodebackground.jpg");
		riv = new ImageView(regbg);
		riv.setImage(regbg);
		riv.setFitHeight(h);
		riv.setFitWidth(w);
		//regPane.getChildren().addAll(riv);

		mreg.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				// Create a world of regular gamemode and add it
				isregular=true;
				ismultiplayer=false;
				isblitz=false;
				regularWorld = new TetrominoWorld(Game.this, (long) 1e9, 0,
						TetrominoWorld.GameMode.GM_NORMAL);
				scoreText.setTranslateX(-270);
				scoreText.setTranslateY(-210);
				
				score1.setTranslateX(-270);
				score1.setTranslateY(-160);
				regPane.getChildren().addAll(riv, regularWorld, scoreText, score1);
				regularWorld.setAlignment(Pos.BASELINE_CENTER);
				regPane.setAlignment(regularWorld, Pos.CENTER);
				regPane.addEventHandler(KeyEvent.KEY_RELEASED, e -> regularWorld.addKey(e.getCode()));

				stage.setScene(regularScene);
				regPane.requestFocus();
				regularWorld.setLeft(KeyCode.getKeyCode(tleft.getText()));
				regularWorld.setRight(KeyCode.getKeyCode(tright.getText()));
				regularWorld.setDown(KeyCode.getKeyCode(tdown.getText()));
				regularWorld.setRot(KeyCode.getKeyCode(trotate.getText()));
				regularWorld.setDone(KeyCode.getKeyCode(tdrop.getText()));

				//regularWorld.setScoreText(score1);
				regularWorld.start();
			}
		});

		// multiplayer mode
	    /** Scene for the multiplayer pane*/
		Scene multiplayerScene = new Scene(mPane, w, h, Color.BLACK);

		multbg = new Image("file:assets/backgrounds/2playerbackground.png");
		multiv = new ImageView();
		multiv.setImage(multbg);
		multiv.setFitHeight(h);
		multiv.setFitWidth(w);

		//mPane.getChildren().addAll(multiv);

		mmultiplayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				// Create two multiplayer worlds
				isregular=false;
				ismultiplayer=true;
				isblitz=false;
				mpWorldA = new TetrominoWorld(Game.this, (long) 1e9, 0,
						TetrominoWorld.GameMode.GM_MULTIPLAYER);
				mpWorldB = new TetrominoWorld(Game.this, (long) 1e9, 0,
						TetrominoWorld.GameMode.GM_MULTIPLAYER);
				mpWorldA.setOpponent(mpWorldB);
				mpWorldB.setOpponent(mpWorldA);

				// Add them, setting alignment reasonably
				score1.setTranslateY(-h);	
				score2.setTranslateY(-h);
				mPane.getChildren().addAll(multiv, mpWorldA, mpWorldB, score1, score2);
				mpWorldA.setAlignment(Pos.BASELINE_LEFT);
				mpWorldB.setAlignment(Pos.BASELINE_RIGHT);

				mPane.setAlignment(mpWorldA, Pos.CENTER_LEFT);
				mPane.setAlignment(mpWorldB, Pos.CENTER_RIGHT);

				// Provision an event handler to pass key-events to worlds
				mPane.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
					mpWorldA.addKey(e.getCode());
					mpWorldB.addKey(e.getCode());
				});

				stage.setScene(multiplayerScene);
				mPane.requestFocus();
				mpWorldA.setLeft(KeyCode.getKeyCode(tleft.getText()));
				mpWorldA.setRight(KeyCode.getKeyCode(tright.getText()));
				mpWorldA.setDown(KeyCode.getKeyCode(tdown.getText()));
				mpWorldA.setRot(KeyCode.getKeyCode(trotate.getText()));
				mpWorldA.setDone(KeyCode.getKeyCode(tdrop.getText()));
				mpWorldA.setMPFrenzy(KeyCode.getKeyCode(tmpfrenzy.getText()));
				mpWorldA.setMPDrop(KeyCode.getKeyCode(tmpdrop.getText()));
				
				mpWorldB.setLeft(KeyCode.getKeyCode(tleft2.getText()));
				mpWorldB.setRight(KeyCode.getKeyCode(tright2.getText()));
				mpWorldB.setDown(KeyCode.getKeyCode(tdown2.getText()));
				mpWorldB.setRot(KeyCode.getKeyCode(trotate2.getText()));
				mpWorldB.setDone(KeyCode.getKeyCode(tdrop2.getText()));
				mpWorldB.setMPFrenzy(KeyCode.getKeyCode(tmpfrenzy2.getText()));
				mpWorldB.setMPDrop(KeyCode.getKeyCode(tmpdrop2.getText()));

//				mpWorldA.setScoreText(score1);
//				mpWorldB.setScoreText(score2);

				mpWorldA.start();
				mpWorldB.start();
			}
		});


		// StackPane regPane = new StackPane();

	    /** Scene for the Blitz Pane*/
		Scene blitzScene = new Scene(blitzPane, w, h, Color.BLACK);

		blitzbg = new Image("file:assets/backgrounds/blitzmodebackground.jpg");
		biv = new ImageView();
		biv.setImage(blitzbg);
		biv.setFitHeight(h);
		biv.setFitWidth(w);

		//blitzPane.getChildren().addAll(biv);

		mblitz.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				// Create a blitz world
				isregular=false;
				ismultiplayer=false;
				isblitz=true;
				blitzWorld = new TetrominoWorld(Game.this, (long) 1e9, 0,
						TetrominoWorld.GameMode.GM_BLITZ);

				// Add them and set alignment
				scoreText.setTranslateX(-270);
				scoreText.setTranslateY(-210);
				
				score1.setTranslateX(-270);
				score1.setTranslateY(-160);
				blitzPane.getChildren().addAll(biv, blitzWorld, scoreText, score1);
				blitzWorld.setAlignment(Pos.BASELINE_CENTER);
				blitzPane.setAlignment(blitzWorld, Pos.CENTER);

				// Provision event handlers
				blitzPane.addEventHandler(KeyEvent.KEY_RELEASED,
						e -> blitzWorld.addKey(e.getCode()));

				stage.setScene(blitzScene);
				blitzPane.requestFocus();
				blitzWorld.setLeft(KeyCode.getKeyCode(tleft.getText()));
				blitzWorld.setRight(KeyCode.getKeyCode(tright.getText()));
				blitzWorld.setDown(KeyCode.getKeyCode(tdown.getText()));
				blitzWorld.setRot(KeyCode.getKeyCode(trotate.getText()));
				blitzWorld.setDone(KeyCode.getKeyCode(tdrop.getText()));

				//blitzWorld.setScoreText(score1);
				blitzWorld.start();
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
				/** custom cursor image*/
				Image im = new Image("file:assets/backgrounds/cursornormal.png");
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
		
	    /** pop-up alert for changing binds in the bind scene*/
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
		
		blur.setHeight(50);
		blur.setWidth(50);
		blur.setIterations(2);

		r.setWidth(w/3);
		r.setHeight(h/3);
		r.setX((w/2) + w/6);
		r.setY((h/2) + h/6);
		r.setArcWidth(30);
		r.setArcHeight(30);
		r.setFill(Color.RED);

		tgameOver.setFont(Font.font("Impact", FontWeight.EXTRA_BOLD, 40));
		tgameOver.setTranslateY(-1*h/10);
		tgameOver.setFill(Color.GOLD);

		ivretry.setTranslateX(-1*w/12);
		ivhome.setTranslateX(w/12);
		
		ivretry.setTranslateY(h/9);
		ivhome.setTranslateY(h/9);
		
		ivretry.setPreserveRatio(true);
		ivhome.setPreserveRatio(true);
		
		ivretry.setFitWidth(w/7.5);
		ivhome.setFitWidth(w/7.5);
		
		ivretry.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivretry.setImage(retryfilled);
			}
		});

		ivretry.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivretry.setImage(retry);
			}
		});
		
		ivhome.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivhome.setImage(homefilled);
			}
		});

		ivhome.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ivhome.setImage(home);
			}
		});
		
		ivretry.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mp.play();
				if (ivretry.getParent().equals(regPane)) {
					for (int i=regPane.getChildren().size()-1;i>=1;i--) {
						regPane.getChildren().remove(i);
					}
					regPane.getChildren().get(0).setEffect(null);
					
					scoreText = new Text("SCORE");
				    score1 = new Score();
				    scoreText.setFill(Color.GOLD);
					scoreText.setFont(new Font("Chewy", 25));
					score1.setTranslateX(-0.3375*w);
					score1.setTranslateY(-0.25*h);
					scoreText.setTranslateX(-0.3375*w);
					scoreText.setTranslateY(-0.328125*h);
					regPane.getChildren().addAll(score1, scoreText);
					
					// Create a world of regular gamemode and add it
					regularWorld = new TetrominoWorld(Game.this, (long) 1e9, 0,
							TetrominoWorld.GameMode.GM_NORMAL);
					regPane.getChildren().addAll(regularWorld);
					regularWorld.setAlignment(Pos.BASELINE_CENTER);
					regPane.setAlignment(regularWorld, Pos.CENTER);
					regPane.addEventHandler(KeyEvent.KEY_RELEASED, e -> regularWorld.addKey(e.getCode()));
					regPane.requestFocus();
					regularWorld.setLeft(KeyCode.getKeyCode(tleft.getText()));
					regularWorld.setRight(KeyCode.getKeyCode(tright.getText()));
					regularWorld.setDown(KeyCode.getKeyCode(tdown.getText()));
					regularWorld.setRot(KeyCode.getKeyCode(trotate.getText()));
					regularWorld.setDone(KeyCode.getKeyCode(tdrop.getText()));
					regularWorld.start();
				} else if (ivretry.getParent().equals(mPane)) {
					for (int i=mPane.getChildren().size()-1;i>=1;i--) {
						mPane.getChildren().remove(i);
					}
					mPane.getChildren().get(0).setEffect(null);

				    score1 = new Score();
				    score2 = new Score();
					score1.setTranslateY(-h);
					score2.setTranslateY(-h);
										
					// Create two multiplayer worlds
					mpWorldA = new TetrominoWorld(Game.this, (long) 1e9, 0,
							TetrominoWorld.GameMode.GM_MULTIPLAYER);
					mpWorldB = new TetrominoWorld(Game.this, (long) 1e9, 0,
							TetrominoWorld.GameMode.GM_MULTIPLAYER);
					
					mpWorldA.setOpponent(mpWorldB);
					mpWorldB.setOpponent(mpWorldA);

					// Add them, setting alignment reasonably
					mPane.getChildren().addAll(mpWorldA, mpWorldB, score1, score2);
					mpWorldA.setAlignment(Pos.BASELINE_LEFT);
					mpWorldB.setAlignment(Pos.BASELINE_RIGHT);

					mPane.setAlignment(mpWorldA, Pos.CENTER_LEFT);
					mPane.setAlignment(mpWorldB, Pos.CENTER_RIGHT);

					// Provision an event handler to pass key-events to worlds
					mPane.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
						mpWorldA.addKey(e.getCode());
						mpWorldB.addKey(e.getCode());
					});
					mPane.requestFocus();
					mpWorldA.setLeft(KeyCode.getKeyCode(tleft.getText()));
					mpWorldA.setRight(KeyCode.getKeyCode(tright.getText()));
					mpWorldA.setDown(KeyCode.getKeyCode(tdown.getText()));
					mpWorldA.setRot(KeyCode.getKeyCode(trotate.getText()));
					mpWorldA.setDone(KeyCode.getKeyCode(tdrop.getText()));
					mpWorldA.setMPFrenzy(KeyCode.getKeyCode(tmpfrenzy.getText()));
					mpWorldA.setMPDrop(KeyCode.getKeyCode(tmpdrop.getText()));
					
					mpWorldB.setLeft(KeyCode.getKeyCode(tleft2.getText()));
					mpWorldB.setRight(KeyCode.getKeyCode(tright2.getText()));
					mpWorldB.setDown(KeyCode.getKeyCode(tdown2.getText()));
					mpWorldB.setRot(KeyCode.getKeyCode(trotate2.getText()));
					mpWorldB.setDone(KeyCode.getKeyCode(tdrop2.getText()));
					mpWorldB.setMPFrenzy(KeyCode.getKeyCode(tmpfrenzy2.getText()));
					mpWorldB.setMPDrop(KeyCode.getKeyCode(tmpdrop2.getText()));
					
					mpWorldA.start();
					mpWorldB.start();
				} else {
					for (int i=blitzPane.getChildren().size()-1;i>=1;i--) {
						blitzPane.getChildren().remove(i);
					}			
					blitzPane.getChildren().get(0).setEffect(null);
					
					scoreText = new Text("SCORE");
				    score1 = new Score();
				    scoreText.setFill(Color.GOLD);
					scoreText.setFont(new Font("Chewy", 25));
					score1.setTranslateX(-0.3375*w);
					score1.setTranslateY(-0.25*h);
					scoreText.setTranslateX(-0.3375*w);
					scoreText.setTranslateY(-0.328125*h);
					blitzPane.getChildren().addAll(score1, scoreText);
					// Create a blitz world
					blitzWorld = new TetrominoWorld(Game.this, (long) 1e9, 0,
							TetrominoWorld.GameMode.GM_BLITZ);

					// Add them and set alignment
					blitzPane.getChildren().addAll(blitzWorld);
					blitzWorld.setAlignment(Pos.BASELINE_CENTER);
					blitzPane.setAlignment(blitzWorld, Pos.CENTER);

					// Provision event handlers
					blitzPane.addEventHandler(KeyEvent.KEY_RELEASED,
							e -> blitzWorld.addKey(e.getCode()));
					blitzPane.requestFocus();
					blitzWorld.setLeft(KeyCode.getKeyCode(tleft.getText()));
					blitzWorld.setRight(KeyCode.getKeyCode(tright.getText()));
					blitzWorld.setDown(KeyCode.getKeyCode(tdown.getText()));
					blitzWorld.setRot(KeyCode.getKeyCode(trotate.getText()));
					blitzWorld.setDone(KeyCode.getKeyCode(tdrop.getText()));
					
					blitzWorld.start();
				}
			}
		});
		
		ivhome.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setScene(s);
				mp.play();
				if(isregular==true) {
					for (int i=regPane.getChildren().size()-1;i>=0;i--) {
						regPane.getChildren().remove(i);
					}
		    	} else if (ismultiplayer==true) {
		    		for (int i=mPane.getChildren().size()-1;i>=0;i--) {
						mPane.getChildren().remove(i);
					}
		    	} else if (isblitz==true) {
		    		for (int i=blitzPane.getChildren().size()-1;i>=0;i--) {
						blitzPane.getChildren().remove(i);
					}
		    	}isregular = false;
		    	ismultiplayer = false;
		    	isblitz = false;
		    	
		    	score1 = new Score();
		    	score2 = new Score();
		    	
		    	scoreText = new Text("SCORE");
		    	scoreText.setFill(Color.GOLD);
				scoreText.setFont(new Font("Chewy", 25));	
				
				riv = new ImageView(regbg);
				riv.setFitHeight(h);
				riv.setFitWidth(w);
				
				multiv = new ImageView(multbg);
				multiv.setFitHeight(h);
				multiv.setFitWidth(w);
				
				biv = new ImageView(blitzbg);
				biv.setFitHeight(h);
				biv.setFitWidth(w);
				//score1.resetScore();
				//score2.resetScore();
			}
		});
		stage.setScene(s);
		stage.show();
	}

	/**
	 * <PRE>
	 * launches the game. 
	 * @author Mario
	 *</PRE>
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * <PRE>
	 * The endGame() method is responsible for what happens when a player loses(e.g. displaying the correct score, pulling up the 
	 * game over menu, and the blur effect)  
	 * @author Mario
	 *</PRE>
	 */
	public void endGame() {
		mp.stop();
		Score scoreTemp = new Score();
//		if (ismultiplayer==true) {
//			if (TetrominoWorld.score1.getScore() < opponent.game.score1.getScore()) {
//				game.score2.setScore(opponent.game.score1.getScore());
//			}
//			if (game.score1.getScore() == opponent.game.score1.getScore()) {
//				game.score2.setScore(opponent.game.score1.getScore());
//			}
//		}
    	if(isregular==true) {
    		for (int i=0;i<regPane.getChildren().size();i++) {
    			regPane.getChildren().get(i).setEffect(blur);
    		}
    		scoreTemp.setScore(regularWorld.tetrominoScore.getScore());
    		regPane.getChildren().addAll(r, tgameOver, ivretry, ivhome, scoreTemp);
    	} else if (ismultiplayer==true) {
    		for (int i=0;i<mPane.getChildren().size();i++) {
    			mPane.getChildren().get(i).setEffect(blur);
    		} 
//    		if (score1.getScore()>score2.getScore()) {
//    			scoreTemp.setScore(score1.getScore());
//        		mPane.getChildren().addAll(r, player1wins, ivretry, ivhome, scoreTemp);
//    		} else if (score1.getScore()==score2.getScore()){
//    			scoreTemp.setScore(score1.getScore());
//        		mPane.getChildren().addAll(r, tie, ivretry, ivhome, scoreTemp);
//    		} else {
//    			scoreTemp.setScore(score2.getScore());
//        		mPane.getChildren().addAll(r, player2wins, ivretry, ivhome, scoreTemp);
//    		}
    		if (mpWorldA.lost==true && mpWorldB.lost==false) {
    			//scoreTemp.setScore(mpWorldB.tetrominoScore.getScore());
        		mPane.getChildren().addAll(r, player2wins, ivretry, ivhome);
    		} else if (mpWorldA.lost==false && mpWorldB.lost==true){
    			//scoreTemp.setScore(mpWorldA.tetrominoScore.getScore());
        		mPane.getChildren().addAll(r, player1wins, ivretry, ivhome);
    		} else {
    			//scoreTemp.setScore(score2.getScore());
        		mPane.getChildren().addAll(r, tie, ivretry, ivhome);
    		}
    	} else if (isblitz==true) {
    		for (int i=0;i<blitzPane.getChildren().size();i++) {
    			blitzPane.getChildren().get(i).setEffect(blur);
    		} 
    		scoreTemp.setScore(blitzWorld.tetrominoScore.getScore());
    		blitzPane.getChildren().addAll(r, tgameOver, ivretry, ivhome, scoreTemp);
    	}
	}
}
