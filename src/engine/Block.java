package engine;

import javafx.scene.image.Image;

/**
 * Enumeration Block represents all the different Block appearances a Tetromino
 * object can take on. Each discrete Block is represented by a different Image.
 */
public enum Block {

    BLOCK_BASE ("block_base");

    String filename;
    Image image;

    Block(String filename) {
        this.filename = filename;
        this.image = new Image("file:assets/blocks/" + filename + ".png");
    }

    public String getFilename() {
        return filename;
    }

    public Image getImage() {
        return image;
    }
}
