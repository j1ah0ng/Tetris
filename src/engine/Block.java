package engine;

import javafx.scene.image.Image;

/**
 * <PRE>
 * Enumeration Block represents all the different Block appearances a Tetromino
 * object can take on. Each discrete Block is represented by a different Image.
 * 
 * </PRE>
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

    /**
     * <PRE>
     * Gets the filename.
     * @return Returns file name.
     * </PRE>
     */
    public String getFilename() {
        return filename;
    }
    /**
     * <PRE>
     * Gets the image.
     * @return Returns the image.
     * </PRE>
     */
    public Image getImage() { return image; }
}
