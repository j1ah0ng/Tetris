package game;

import engine.*;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;

public class TetrominoWorld extends World {

    // Array of all possible Block types
    public static final Block[] BLOCKS = {Block.BLOCK_BASE};

    // Rotate keycode
    public static final KeyCode ROTATE = KeyCode.R;

    // Rotation matrix
    public static final int[][] R_MAT =
            {{0, -1},
             {1,  0}};

    // Grid width
    public static final int WIDTH = 12;
    // Grid height
    public static final int HEIGHT = 18;

    // Attributes
    private long delay;             // Delay between display updates
    private long lastRun;           // Last run of act()
    private boolean spawnNew;
    ArrayList<ImageView> fallingBlocks; // Current set of falling blocks

    // Constructors
    public TetrominoWorld() {
        super();
    }

    // Methods
    @Override
    public void act(long now) {

        // Spawn new blocks
        if (spawnNew) {

            // Create four new Block objects and add them to the falling
            // ArrayList

            // Randomise block appearance
            int blockType = (int)(Math.random() * BLOCKS.length);
            fallingBlocks = new ArrayList<ImageView>();
            for (int i = 0; i < 4; ++i) {
                fallingBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
            }

            // Arrange the blocks in the ArrayList accordingly
            buildTetromino(fallingBlocks);

            spawnNew = false;
        }

        // Block movements and check collisions
        else if (lastRun - now > delay) {

            // Flag variable for having touched the bottom stack
            boolean hasTouchedBottom = false;

            // Iterate over falling blocks
            for (ImageView view : fallingBlocks) {
                // Move blocks down
                setRowIndex(view, getRowIndex(view) + 1);
                // Check if next move will intersect with bottom stack
                for (Node n : getChildren()) {
                    if (!hasTouchedBottom &&
                            !fallingBlocks.contains(n) &&
                            getRowIndex(n) - 1 == getRowIndex(view) &&
                            getColumnIndex(n) == getColumnIndex(view)) {

                        // If so, set flag
                        hasTouchedBottom = true;

                    }
                }
            }

            spawnNew = hasTouchedBottom;

        }

        // Rotate fallingBlocks
        if (hasKey(ROTATE)) {

            // Find origin

            // Translate blocks to points about origin

            // Do coordinate transform and move blocks back
            removeKey(ROTATE);
        }
    }

    /** Rotates a point clockwise by 90 degrees represented as int[] {x, y}.
     *
     * @param point A point to be rotated represented as int[] {x, y}
     * @return A rotated point represented as int[] {x, y}
     */
    public int[] rotatePoint(int[] point) {
        return {(R_MAT[0,0] * point[0]) + (R_MAT[0,1] * point[1]),
                (R_MAT[1,0] * point[0]) + (R_MAT[1,1] * point[1])};
    }

    /** Builds a Tetromino from a list of ImageViews of Blocks
     *
     * @param list ArrayList<ImageView> of length 4
     */
    public void buildTetromino(ArrayList<ImageView> list) {

        if (list.size() != 4) return;

        // Randomise.
        int r = (int)(5 * Math.random());
        switch (r) {
            case 0:
                // L-shape.
                add(list.get(0), 6, 0);
                add(list.get(1), 6, 1);
                add(list.get(2), 6, 2);
                add(list.get(3), 7, 2);
                break;
            case 1:
                // Square shape.
                add(list.get(0), 6, 0);
                add(list.get(1), 7, 0);
                add(list.get(2), 6, 1);
                add(list.get(3), 7, 1);
                break;
            case 2:
                // T-shape.
                add(list.get(0), 6, 0);
                add(list.get(1), 5, 0);
                add(list.get(2), 7, 0);
                add(list.get(3), 6, 1);
                break;
            case 3:
                // Straight shape.
                add(list.get(0), 5, 0);
                add(list.get(1), 6, 0);
                add(list.get(2), 7, 0);
                add(list.get(3), 8, 0);
                break;
            case 4:
                // Z-shape.
                add(list.get(0), 5, 0);
                add(list.get(1), 6, 0);
                add(list.get(2), 6, 1);
                add(list.get(3), 7, 1);
                break;
        }
    }
}