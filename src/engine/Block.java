package engine;

import javafx.scene.image.Image;

// Represents the different types of blocks a tetronmino
// can take on.
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
