package engine;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TetrominoT extends Tetromino {

//	public TetrominoT() {
//		image = new Image("https://t5.rbxcdn.com/0affe2ebea58132423540072cecdc010");
//		iv.setImage(image);
//		al = new ArrayList<ImageView>();
//		iv.setX(50);
//		al.add(iv);
//		al.add(iv);
//		al.add(iv);
//		al.add(iv);
//	}
	
	public TetrominoT() {
		
		super();
		al = new ArrayList<ImageView>();
		super.setImage(new Image("https://t5.rbxcdn.com/0affe2ebea58132423540072cecdc010"));
		al.add(e);
	}
	
	@Override
	void rotate() {

	}

	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}

}
