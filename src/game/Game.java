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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris");
		stage.setResizable(true);

		int w = 800;
		int h = 640;

		// Modes menu scene

		StackPane menuRoot = new StackPane();
		Scene menuScene = new Scene(menuRoot, w, h, Color.BLACK);

		Image menubg = new Image("file:assets/Backgrounds/menumodebackground.jpg");
		ImageView miv1 = new ImageView();

		miv1.setFitHeight(h);
		miv1.setFitWidth(w);
		miv1.setImage(menubg);

		Image backLogo = new Image("file:assets/Backgrounds/backlogo.png");
		ImageView miv2 = new ImageView();
		miv2.setImage(backLogo);
		miv2.setFitHeight(50);
		miv2.setFitWidth(50);

		HBox back = new HBox();
		back.getChildren().add(miv2);
		back.setPadding(new Insets(0, 0, 0, 0));
		

		HBox modeTitle = new HBox();
		Text title = new Text("MODES");
		title.setFont(Font.font ("Impact", FontWeight.EXTRA_BOLD, 80));
		title.setFill(Color.WHITE);
		
		modeTitle.setPadding((new Insets(50, 0, 0, w/2 - 110)));

		modeTitle.getChildren().add(title);
		

		menuRoot.getChildren().addAll(miv1, modeTitle, back);

		
		
		
		
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
		// setfitwidth and height
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
