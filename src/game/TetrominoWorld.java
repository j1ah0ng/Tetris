package game;

import engine.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TetrominoWorld extends World {

    /* Begin static types */

    // Blank block image
    public static final Image BLANK_SQUARE = new Image("file:assets/blocks/ux0.png");

    // Blank grid
    public static ImageView[][] BLANK_GRID;

    // Array of all possible Block types
    public static final Block[] BLOCKS = {Block.BLOCK_BASE};

    // Rotation matrix
    public static final int[][] R_MAT =
            {{0, -1},
             {1,  0}};

    // Grid width
    public static final int WIDTH = 12;
    // Grid height
    public static final int HEIGHT = 18;

    /* End static types */

    // Keycode bindings
    public KeyCode ROTATE = KeyCode.R;
    public KeyCode DOWN = KeyCode.DOWN;
    public KeyCode LEFT = KeyCode.LEFT;
    public KeyCode RIGHT = KeyCode.RIGHT;
    public KeyCode DONE = KeyCode.SPACE;

    // Attributes
    private long delay;             // Delay between display updates
    private long delayAccel;        // d(delay)/dt in seconds per second
    private long lastRun;           // Last run of act()
    private boolean spawnNew;       // Whether it should spawn a new block this tick
    private boolean hasTouchedBottom;
    // Flag variable for having touched the bottom stack

    ArrayList<ImageView> fallingBlocks; // Current set of falling blocks
    ArrayList<ImageView> nextBlocks; // Next set of falling blocks, for preview purposes
    int nextTetromino;              // int representing ID of the next falling tetromino

    // Constructors
    public TetrominoWorld(long delay) {
        super();
        lastRun = 0;
        spawnNew = true;
        hasTouchedBottom = false;
        this.delay = delay;
        this.delayAccel = 0;

        initialise();
    }

    public TetrominoWorld(long delay, long delayAccel) {
        super();
        lastRun = 0;
        spawnNew = true;
        hasTouchedBottom = false;
        this.delay = delay;
        this.delayAccel = delayAccel;

        initialise();
    }

    // Methods
    @Override
    public void act(long now) {

        // Check whether we've reached a new tick
        if (now - lastRun > delay) {
            System.out.println("Time: " + System.currentTimeMillis());

            delay += (delayAccel) * (now - lastRun);

            // Block movements and check collisions
            if (!spawnNew) {
                // Iterate over falling blocks

                if (!hasTouchedBottom) {
                    for (ImageView view : fallingBlocks) {
                        // Move blocks down
                        setRowIndex(view, getRowIndex(view) + 1);
                    }
                    hasTouchedBottom = checkCollisions();
                }

                spawnNew = hasTouchedBottom;
            } else {


                if (fallingBlocks.size() == 0) {        // Deal with first run scenario
                    // Randomise block appearance
                    int blockType = (int) (Math.random() * BLOCKS.length);
                    fallingBlocks = new ArrayList<ImageView>();
                    for (int i = 0; i < 4; ++i) {
                        fallingBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
                    }

                    // Arrange the blocks in the ArrayList accordingly
                    buildTetromino(fallingBlocks, (int) (6 * Math.random()));

                    spawnNew = hasTouchedBottom = false;

                    // Create new tetromino for nextBlocks
                    blockType = (int) (Math.random() * BLOCKS.length);
                    nextBlocks = new ArrayList<ImageView>();
                    for (int i = 0; i < 4; ++i) {
                        nextBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
                    }

                    // Deal with next case
                    nextTetromino = (int) (Math.random() * 6);
                }

                else {
                    // Make the currently falling block the next one
                    fallingBlocks = nextBlocks;

                    // Arrange the blocks in the ArrayList accordingly
                    buildTetromino(fallingBlocks, nextTetromino);

                    // Create new tetromino for nextBlocks
                    int blockType = (int) (Math.random() * BLOCKS.length);
                    nextBlocks = new ArrayList<ImageView>();
                    for (int i = 0; i < 4; ++i) {
                        nextBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
                    }

                    // Deal with next case
                    nextTetromino = (int) (Math.random() * 6);

                    spawnNew = hasTouchedBottom = false;
                }
            }

            // Update runtime
            lastRun = now;
        }

        // Move falling blocks
        if (!hasTouchedBottom) {

            // Rotate fallingBlocks
            if (hasKey(ROTATE)) {

                // Find origin as the average coordinates of all falling blocks
                int xZero = 0;
                int yZero = 0;
                for (ImageView i : fallingBlocks) {
                    xZero += GridPane.getColumnIndex(i);
                    yZero += GridPane.getRowIndex(i);
                }
                xZero /= 4;
                yZero /= 4;

                // Translate blocks to points about origin
                int[][] points = new int[4][2];
                int xOffset = 0;
                int yOffset = 0;
                for (int i = 0; i < 4; ++i ) {
                    ImageView v = fallingBlocks.get(i);
                    points[i][0] = getColumnIndex(v) - xZero;
                    points[i][1] = getRowIndex(v) - yZero;

                    // Do coordinate transform, adding back origin offsets
                    int[] temp = rotatePoint(points[i]);
                    points[i][0] = temp[0] + xZero;
                    points[i][1] = temp[1] + yZero;

                    // Check if the entire block needs shifting
                    while (points[i][0] + xOffset < 0) {
                        ++xOffset;
                    }
                    while (points[i][0] + xOffset >= WIDTH) {
                        --xOffset;
                    }

                    // Check for vertical shifting (issue only with the lonc block)
                    while (points[i][1] + yOffset < 0) {
                        ++yOffset;
                    }
                }

                // Move blocks back
                for (int i = 0; i < 4; ++i) {
                    ImageView v = fallingBlocks.get(i);
                    setColumnIndex(v, points[i][0] + xOffset);
                    setRowIndex(v, points[i][1] + yOffset);
                }

                removeKey(ROTATE);
            }

            boolean flag = false;
            if (hasKey(this.DOWN)) {
                // Check bounds
                if (!hasTouchedBottom) {
                    for (ImageView i : fallingBlocks) {
                        setRowIndex(i, getRowIndex(i) + 1);
                    }
                    hasTouchedBottom = checkCollisions();
                }
                removeKey(this.DOWN);
            } else if (hasKey(this.LEFT)) {
                // Check bounds
                for (ImageView i : fallingBlocks) {
                    if (getColumnIndex(i) - 1 < 0) flag = true;
                }
                if (!flag) {
                    for (ImageView i : fallingBlocks) {
                        setColumnIndex(i, getColumnIndex(i) - 1);
                    }
                }
                removeKey(this.LEFT);
            } else if (hasKey(this.RIGHT)) {
                // Check bounds
                for (ImageView i : fallingBlocks) {
                    if (getColumnIndex(i) + 1 >= WIDTH) flag = true;
                }
                if (!flag) {
                    for (ImageView i : fallingBlocks) {
                        setColumnIndex(i, getColumnIndex(i) + 1);
                    }
                }
                removeKey(this.RIGHT);
            } else if (hasKey(this.DONE)) {
                while (!hasTouchedBottom) {
                    for (ImageView i : fallingBlocks) {
                        setRowIndex(i, getRowIndex(i) + 1);
                    }
                    hasTouchedBottom = checkCollisions();
                }
                removeKey(this.DONE);
            }
        } else {

            // Check if any rows need to be eliminated
            stop();
            int[] rows = new int[HEIGHT];
            for (Node i : getChildren()) {
                if (((ImageView)i).getImage().equals(BLANK_SQUARE)) continue;

                    // A property of the grid is that at any given time, each x-y on the GridPane may be
                    // occupied by only one non-blank image. We exploit this property.
                else rows[getRowIndex(i)]++;
            }

            // Collapse rows from down to up.
            for (int i = HEIGHT-1; i >= 0; --i) {
                // Check each row.
                if (rows[i] >= WIDTH) {
                    // Move everything under it down and everything in it away.
                    Iterator<Node> iter = getChildren().iterator();
                    while (iter.hasNext()) {
                        Node n = iter.next();
                        if (((ImageView)n).getImage().equals(BLANK_SQUARE)) continue;
                        else {
                            int k = getRowIndex(n);
                            if (k == i) iter.remove();
                            else if (k < i) setRowIndex(n, getRowIndex(n) + 1);
                        }
                    }
                }
            }
            start();

        }
    }

    /** Rotates a point clockwise by 90 degrees represented as int[] {x, y}.
     *
     * @param point A point to be rotated represented as int[] {x, y}
     * @return A rotated point represented as int[] {x, y}
     */
    public int[] rotatePoint(int[] point) {
        return new int[] {(R_MAT[0][0] * point[0]) + (R_MAT[0][1] * point[1]),
                (R_MAT[1][0] * point[0]) + (R_MAT[1][1] * point[1])};
    }

    /** Builds a Tetromino from a list of ImageViews of Blocks
     *
     * @param list ArrayList<ImageView> of length 4
     */
    public void buildTetromino(ArrayList<ImageView> list, int r) {

        if (list.size() != 4) return;

        /*  Deprecated due to issues
        // Randomise.
        int r = (int)(6 * Math.random());
         */

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
            case 5:
                // Inverse Z-shape.
                add(list.get(0), 5, 1);
                add(list.get(1), 6, 1);
                add(list.get(2), 6, 0);
                add(list.get(3), 7, 0);
                break;
        }
    }

    public void setLeft(KeyCode k) {
        this.LEFT = k;
    }
    public void setRight(KeyCode k) {
        this.RIGHT = k;
    }
    public void setDown(KeyCode k) {
        this.DOWN = k;
    }
    public void setRotate(KeyCode k) {
        this.ROTATE = k;
    }
    public void setDone(KeyCode k) {
        this.DONE = k;
    }
    public KeyCode getLeft() { return this.LEFT; }
    public KeyCode getRight() { return this.RIGHT; }
    public KeyCode getDown() { return this.DOWN; }
    @Override
    public KeyCode getRotate() { return this.ROTATE; }
    public KeyCode getDone() { return this.DONE; }

    /** Fills the board with blank Blocks. */
    public void initialise() {
        BLANK_GRID = new ImageView[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                BLANK_GRID[row][col] = new ImageView(BLANK_SQUARE);
                add(BLANK_GRID[row][col], col, row);
            }
        }

        this.fallingBlocks = new ArrayList<ImageView>();
    }

    public boolean checkCollisions() {
        boolean flag = false;
        for (ImageView view : fallingBlocks) {
            // Check if next move will intersect with bottom stack
            for (Node n : getChildren()) {
                if (!hasTouchedBottom &&
                        !fallingBlocks.contains(n) &&
                        !((ImageView)n).getImage().equals(BLANK_SQUARE) &&
                        getRowIndex(n) - 1 == getRowIndex(view) &&
                        getColumnIndex(n) == getColumnIndex(view)) {

                    // If so, set flag
                    flag = true;
                } else if (getRowIndex(view) + 1 >= HEIGHT) flag = true;
            }
        }
        return flag;
    }

    // Mode-specific commands
    public GridPane getNextTetromino() {
        GridPane upcoming = new GridPane();

        switch (nextTetromino) {
            case 0:
                // L-shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 6, 1);
                upcoming.add(nextBlocks.get(2), 6, 2);
                upcoming.add(nextBlocks.get(3), 7, 2);
                upcoming.break;
            case 1:
                // Square shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 7, 0);
                upcoming.add(nextBlocks.get(2), 6, 1);
                upcoming.add(nextBlocks.get(3), 7, 1);
                upcoming.break;
            case 2:
                // T-shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 5, 0);
                upcoming.add(nextBlocks.get(2), 7, 0);
                upcoming.add(nextBlocks.get(3), 6, 1);
                upcoming.break;
            case 3:
                // Straight shape.
                upcoming.add(nextBlocks.get(0), 5, 0);
                upcoming.add(nextBlocks.get(1), 6, 0);
                upcoming.add(nextBlocks.get(2), 7, 0);
                upcoming.add(nextBlocks.get(3), 8, 0);
                upcoming.break;
            case 4:
                // Z-shape
                upcoming.add(nextBlocks.get(0), 5, 0);
                upcoming.add(nextBlocks.get(1), 6, 0);
                upcoming.add(nextBlocks.get(2), 6, 1);
                upcoming.add(nextBlocks.get(3), 7, 1);
                upcoming.break;
            case 5:
                // Inverse Z-shape.
                upcoming.add(nextBlocks.get(0), 5, 1);
                upcoming.add(nextBlocks.get(1), 6, 1);
                upcoming.add(nextBlocks.get(2), 6, 0);
                upcoming.add(nextBlocks.get(3), 7, 0);
                break;
        }

        return upcoming;
    }
}
