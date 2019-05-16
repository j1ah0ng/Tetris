package game;

import engine.*;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class GridWorld extends World {

    // Array of all possible Block types
    public static final Block[] BLOCKS = {Block.BLOCK_BASE};

    // Rotate keycode
    public static final KeyCode ROTATE = KeyCode.R;

    // Rotation matrix
    public static final double[][] R_MAT =
            {{0, -1},
             {1,  0}};

    // Attributes
    private long delay;             // Delay between display updates
    private long lastRun;           // Last run of act()
    ArrayList<ImageView> fallingBlocks; // Current set of falling blocks

    // Constructors
    public GridWorld() {
        super();
    }

    // Methods
    @Override
    public void act(long now) {

        // Block movements
        if (lastRun - now > delay) {
        }

        // Spawn new blocks
        if (timeToSpawnNewBlock()) {

            // Create four new Block objects and add them to the falling
            // ArrayList
            int blockType = (int)(Math.random() * BLOCKS.length);
            fallingBlocks = new ArrayList<ImageView>
            for (int i = 0; i < 4; ++i) {
                fallingBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
            }

            // Arrange the blocks in the ArrayList accordingly

        }

        // Rotate fallingBlocks
        if (hasKey(ROTATE)) {

        }
    }

    public boolean timeToSpawnNewBlock() {
        return false;
    }
}
