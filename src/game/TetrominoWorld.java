package game;

import engine.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class TetrominoWorld extends World {

    /* Begin static types */

    // Gamemode enum
    public static enum GameMode {
        GM_MULTIPLAYER, GM_BLITZ, GM_NORMAL
    }

    // Blank block image
    public static final Image BLANK_SQUARE = new Image("file:assets/blocks/32ux0.png");

    // Blank grid
    public static ImageView[][] BLANK_GRID;

    // Array of all possible Block types
    public static final Block[] BLOCKS = {Block.DARK_RED, Block.GREEN, Block.ORANGE, Block.PURPLE,
        Block.RED, Block.TURQUOISE, Block.YELLOW};

    // Rotation matrix
    public static final int[][] R_MAT =
            {{0, -1},
             {1,  0}};

    // Grid width
    public static final int WIDTH = 12;
    // Grid height
    public static final int HEIGHT = 20;

    /* End static types */

    // Keycode bindings
    private KeyCode rotate = KeyCode.R;
    private KeyCode down = KeyCode.DOWN;
    private KeyCode left = KeyCode.LEFT;
    private KeyCode right = KeyCode.RIGHT;
    private KeyCode done = KeyCode.SPACE;

    // Gamemode flag
    private final boolean MULTIPLAYER;
    private final boolean BLITZ;

    // Data relevant only to blitz mode
    private static final long BLITZ_LENGTH_NS = (long)120e9;
    // Data relevant only to multiplayer mode
    private static final long FRENZY_LENGTH_NS = (long)3e9;
    private static final int FRENZY_SPEED_SCALAR = 3;
    private long frenzyStartTimeNs;
    private int rowsEliminated;
    private TetrominoWorld opponent;
    private KeyCode mpFrenzy = KeyCode.F;      // Placeholder keybinds
    private KeyCode mpDrop = KeyCode.D;        // until we implement better

    // Attributes
    private long delay;             // Delay between display updates
    private long delayAccel;        // d(delay)/dt in seconds per second
    private long lastRun;           // Last run of act()
    private boolean spawnNew;       // Whether it should spawn a new block this tick
    private boolean gameOver;
    private Game game;

    private ArrayList<ImageView> fallingBlocks; // Current set of falling blocks
    private ArrayList<ImageView> nextBlocks; // Next set of falling blocks, for preview purposes
    private int nextTetromino;              // int representing ID of the next falling tetromino

    // Constructors
    public TetrominoWorld(Game game, long delay) {
        super();
        this.game = game;
        lastRun = 0;
        spawnNew = true;
        this.delay = delay;
        this.delayAccel = 0;
        MULTIPLAYER = false;
        BLITZ = false;
        rowsEliminated = 0;
        gameOver = false;

        initialize();
    }

    public TetrominoWorld(Game game, long delay, long delayAccel) {
        super();
        this.game = game;
        lastRun = 0;
        spawnNew = true;
        this.delay = delay;
        this.delayAccel = delayAccel;
        MULTIPLAYER = false;
        BLITZ = false;
        rowsEliminated = 0;
        gameOver = false;

        initialize();
    }

    public TetrominoWorld(Game game, long delay, long delayAccel, GameMode mode) {
        super();
        this.game = game;
        lastRun = 0;
        spawnNew = true;
        this.delay = delay;
        this.delayAccel = delayAccel;
        rowsEliminated = 0;
        gameOver = false;

        switch (mode) {
            case GM_BLITZ:
                BLITZ = true;
                MULTIPLAYER = false;
                break;
            case GM_MULTIPLAYER:
                BLITZ = false;
                MULTIPLAYER = true;
                break;
            default:
                BLITZ = false;
                MULTIPLAYER = false;
                break;
        }

        initialize();
    }

    // Public getter and setter functions
    public void setOpponent(TetrominoWorld opponent) { this.opponent = opponent; }
    public void setLeft(KeyCode k) { this.left = k; }
    public void setRight(KeyCode k) { this.right = k; }
    public void setDown(KeyCode k) { this.down = k; }
    public void setRot(KeyCode k) { this.rotate = k; }
    public void setDone(KeyCode k) { this.done = k; }
    public void setMPFrenzy(KeyCode k) { this.mpFrenzy = k; }
    public void setMPDrop(KeyCode k) { this.mpDrop = k; }
    public KeyCode getLeft() { return this.left; }
    public KeyCode getRight() { return this.right; }
    public KeyCode getDown() { return this.down; }
    public KeyCode getRot() { return this.rotate; }
    public KeyCode getDone() { return this.done; }
    public KeyCode getMPFrenzy() { return this.mpFrenzy; }
    public KeyCode getMPDrop() { return this.mpDrop; }

    // Main loop function
    @Override
    protected void act(long now) {

        // Stop acting if game is over
        if (gameOver) return;

        // Check whether we've reached a new tick
        if (now - lastRun > delay) {

            // Debug statement
            // System.out.println("Time: " + System.currentTimeMillis());

            delay += (delayAccel) * (now - lastRun);

            // Block movements and check collisions
            if (!spawnNew) {
                // Iterate over falling blocks
                moveDown();
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

                    spawnNew = false;

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
                    handleSpawn();
                }
            }

            // Update runtime
            lastRun = now;
        }

        // Move falling blocks
        if (!checkCollisions(0, 1)) {

            // Rotate fallingBlocks
            // todo: rotations may break collision logic
            if (hasKey(rotate)) {

                // Find origin as the average coordinates of all falling blocks
                int xZero = 0;
                int yZero = 0;
                for (ImageView i : fallingBlocks) {
                    xZero += 1 + GridPane.getColumnIndex(i);    // add 1 to fix
                    yZero += 1 + GridPane.getRowIndex(i);       // left shifting
                }
                xZero /= 4; // where 4 is the number of blocks
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

                removeKey(rotate);
            }

            boolean flag = false;
            if (hasKey(this.down)) {
                moveDown();
                removeKey(this.down);
            } else if (hasKey(this.left)) {
                // Check bounds
                if(!checkCollisions(-1, 0)) {
                    for (ImageView i : fallingBlocks) {
                        if (getColumnIndex(i) - 1 < 0) flag = true;
                    }
                    if (!flag) {
                        for (ImageView i : fallingBlocks) {
                            setColumnIndex(i, getColumnIndex(i) - 1);
                        }
                    }
                }
                removeKey(this.left);
            } else if (hasKey(this.right)) {
                // Check bounds
                if(!checkCollisions(+1, 0)) {
                    for (ImageView i : fallingBlocks) {
                        if (getColumnIndex(i) + 1 >= WIDTH) flag = true;
                    }
                    if (!flag) {
                        for (ImageView i : fallingBlocks) {
                            setColumnIndex(i, getColumnIndex(i) + 1);
                        }
                    }
                }
                removeKey(this.right);
            } else if (hasKey(this.done)) {
                drop();
                removeKey(this.done);
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
                    ++rowsEliminated;
                    // Move everything under it down and everything in it away.
                    Iterator<Node> iter = getChildren().iterator();
                    while (iter.hasNext()) {
                        Node n = iter.next();
                        if (((ImageView)n).getImage().equals(BLANK_SQUARE)) continue;
                        else {
                            int k = getRowIndex(n);
                            if (k == i) { 
                            	game.score1.addScore(10);
                            	iter.remove();
                            }
                            else if (k < i) setRowIndex(n, getRowIndex(n) + 1);
                        }
                    }
                }
            }
            start();

        }

        // Deal with multiplayer
        if (MULTIPLAYER && opponent != null) mpAct();

        // Deal with blitz
        if (msElapsed() > BLITZ_LENGTH_NS) endGame();
    }

    // Private helper functions
    /** Rotates a point clockwise by 90 degrees represented as int[] {x, y}.
     *
     * @param point A point to be rotated represented as int[] {x, y}
     * @return A rotated point represented as int[] {x, y}
     */
    private int[] rotatePoint(int[] point) {
        return new int[] {(R_MAT[0][0] * point[0]) + (R_MAT[0][1] * point[1]),
                (R_MAT[1][0] * point[0]) + (R_MAT[1][1] * point[1])};
    }

    /** Builds a Tetromino from a list of ImageViews of Blocks
     *
     * @param list ArrayList<ImageView> of length 4
     */
    private void buildTetromino(ArrayList<ImageView> list, int r) {

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

    /** Fills the board with blank Blocks. */
    private void initialize() {
        BLANK_GRID = new ImageView[HEIGHT][WIDTH];

        for (int row = 0; row < HEIGHT; ++row) {
            for (int col = 0; col < WIDTH; ++col) {
                BLANK_GRID[row][col] = new ImageView(BLANK_SQUARE);
                add(BLANK_GRID[row][col], col, row);
            }
        }

        this.fallingBlocks = new ArrayList<ImageView>();
    }

    private boolean checkCollisions(int x, int y) {
        boolean flag = false;
        for (ImageView view : fallingBlocks) {
            // Check if next move will intersect with bottom stack
            if (getRowIndex(view) + 1 >= HEIGHT) {
                flag = true;
                break;
            }

            for (Node n : getChildren()) {
                if (!fallingBlocks.contains(n) &&
                        !((ImageView)n).getImage().equals(BLANK_SQUARE) &&
                        getRowIndex(n) == getRowIndex(view) + y &&
                        getColumnIndex(n)  == getColumnIndex(view) + x) {

                    // If so, set flag
                    flag = true;
                }
            }
        }
        return flag;
    }

    private void moveDown() {
    	if (game.mPane.getChildren().contains(game.r)) {
    		gameOver=true;
        	for (int i = fallingBlocks.size()-1;i>=1;i--) {
        		fallingBlocks.remove(fallingBlocks.get(i));
        	}
        	stopGame();
            spawnNew = false;
            return;
        }
        if (!checkCollisions(0, 1)) {
            for (ImageView view : fallingBlocks) {
                // Move blocks down
                setRowIndex(view, getRowIndex(view) + 1);
            }
        }
        spawnNew = checkCollisions(0, 1);
    }

    private void endGame() {
    	stopGame();
        if (game != null) {
        	game.endGame();
        }
        spawnNew = false;
    }
    
    private void handleSpawn() {
        // Make the currently falling block the next one
        fallingBlocks = nextBlocks;

        // Arrange the blocks in the ArrayList accordingly
        buildTetromino(fallingBlocks, nextTetromino);

        // Check if the game has ended
        for (ImageView i : fallingBlocks) {
            Iterator it = getChildren().iterator();
            int c = getColumnIndex(i);
            int r = getRowIndex(i);
            while (it.hasNext()) {
                Node n = (Node) it.next();
                if (!fallingBlocks.contains(n) &&
                        !((ImageView) n).getImage().equals(BLANK_SQUARE) &&
                        getColumnIndex(n) == c &&
                        getRowIndex(n) == r) {
                    gameOver = true;
                }
            }
        }

        if (gameOver) {
            for (ImageView i : fallingBlocks) {
                getChildren().remove(i);
            }
            endGame();
            return;
        }

        
        
        // Create new tetromino for nextBlocks
        int blockType = (int) (Math.random() * BLOCKS.length);
        nextBlocks = new ArrayList<ImageView>();
        for (int i = 0; i < 4; ++i) {
            nextBlocks.add(new ImageView(BLOCKS[blockType].getImage()));
        }

        // Deal with next case
        nextTetromino = (int) (Math.random() * 6);

        spawnNew = false;
    }

    // Enables next block
    private GridPane getNextTetromino() {
        GridPane upcoming = new GridPane();

        switch (nextTetromino) {
            case 0:
                // L-shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 6, 1);
                upcoming.add(nextBlocks.get(2), 6, 2);
                upcoming.add(nextBlocks.get(3), 7, 2);
                break;
            case 1:
                // Square shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 7, 0);
                upcoming.add(nextBlocks.get(2), 6, 1);
                upcoming.add(nextBlocks.get(3), 7, 1);
                break;
            case 2:
                // T-shape.
                upcoming.add(nextBlocks.get(0), 6, 0);
                upcoming.add(nextBlocks.get(1), 5, 0);
                upcoming.add(nextBlocks.get(2), 7, 0);
                upcoming.add(nextBlocks.get(3), 6, 1);
                break;
            case 3:
                // Straight shape.
                upcoming.add(nextBlocks.get(0), 5, 0);
                upcoming.add(nextBlocks.get(1), 6, 0);
                upcoming.add(nextBlocks.get(2), 7, 0);
                upcoming.add(nextBlocks.get(3), 8, 0);
                break;
            case 4:
                // Z-shape
                upcoming.add(nextBlocks.get(0), 5, 0);
                upcoming.add(nextBlocks.get(1), 6, 0);
                upcoming.add(nextBlocks.get(2), 6, 1);
                upcoming.add(nextBlocks.get(3), 7, 1);
                break;
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

    // Handle multiplayer
    public int getRowsEliminated() { return rowsEliminated; }
    private void mpAct() {
        if (rowsEliminated > 0) {
            if (hasKey(this.mpDrop)) {
                opponent.drop();
                --rowsEliminated;
            }
            else if (hasKey(this.mpFrenzy)) {
                opponent.beginFrenzy();
                --rowsEliminated;
            }
        }
    }

    public void drop() {
        while (!checkCollisions(0, 1)) {
            moveDown();
        }
    }

    public void beginFrenzy() {
        // Stop the world timer
        stop();

        // Handle the frenzy for five seconds
        AnimationTimer frenzyTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                handleFrenzy(l, this);
            }
        };

        delay /= FRENZY_SPEED_SCALAR;
        frenzyStartTimeNs = (long) (System.currentTimeMillis() * 1e6);
        frenzyTimer.start();
    }

    private void handleFrenzy(long l, AnimationTimer timer) {
        if (frenzyStartTimeNs + FRENZY_LENGTH_NS <= l) {
            delay *= FRENZY_SPEED_SCALAR;
            timer.stop();
        }
        act(l);
    }
}
