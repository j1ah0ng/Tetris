package engine;

import javafx.scene.image.Image;

/**
 * Enumeration Block represents all the different Block appearances a Tetromino
 * object can take on. Each discrete Block is represented by a different Image.
 */
public enum Block {

    DARK_RED("32darkredblock2.png"),
    GREEN("32greenblock2.png"),
    ORANGE("32orangeblock2.png"),
    PURPLE("32purpleblock2.png"),
    RED("32redblock2.png"),
    TURQUOISE("32turquoiseblock2.png"),
    YELLOW("32yellowblock2.png") ;

    String filename;
    Image image;

    Block(String filename) {
        this.filename = filename;
        this.image = new Image("file:assets/blocks/" + filename);
    }

    public String getFilename() {
        return filename;
    }

    public Image getImage() { return image; }
}
