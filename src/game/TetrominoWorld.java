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

/**
 * <PRE>
 * The TetrominoWorld.java class extends World and creates the raw Tetris game through a GridPane. TetrominoWorld handles all the spawns,
 * collisions, functionalities, rotations, binds, and etc. for the Tetris game. The class also builds the Tetromino shapes
 * itself. 
 * 
 * @author Ryan
 *</PRE>
 */
public class TetrominoWorld extends World {

    /* Begin static types */

    // Gamemode enum
	
	/**
	 * <PRE>
	 * The enums of the three different game modes to depict which TetrominoWorld is going to run.
	 * 
	 * @author Ryan
	 * </PRE>
	 */
    public static enum GameMode {
        GM_MULTIPLAYER, GM_BLITZ, GM_NORMAL
    }

    // Blank block image
    
    /**  A static final Image attribute that represents the individual blank Tetromino block. */
    public static final Image BLANK_SQUARE = new Image("file:assets/blocks/32ux0.png");

    // Blank grid
    /** A static attribute that represents the grid through a 2-D array of ImageView elements. */
    public static ImageView[][] BLANK_GRID;

    // Array of all possible Block types
    /** A static final attribute that holds an array of all the possible tetromino block colors. */
    public static final Block[] BLOCKS = {Block.DARK_RED, Block.GREEN, Block.ORANGE, Block.PURPLE,
        Block.RED, Block.TURQUOISE, Block.YELLOW};

    /** A static final attribute that is a 2-D array of integers that represents a rotation matrix. */
    public static final int[][] R_MAT =
            {{0, -1},
             {1,  0}};

    /** A static final attribute that represents the grid's width.  */
    public static final int WIDTH = 12;
    /** A static final attribute that represents the grid's height.  */
    public static final int HEIGHT = 18;

    /* End static types */

    /** A KeyCode attribute representing a call for a rotation. */
    private KeyCode rotate = KeyCode.R;
    /** A KeyCode attribute representing a call for down. */
    private KeyCode down = KeyCode.DOWN;
    /** A KeyCode attribute representing a call for left. */
    private KeyCode left = KeyCode.LEFT;
    /** A KeyCode attribute representing a call for right. */
    private KeyCode right = KeyCode.RIGHT;
    /** A KeyCode attribute representing a call for a space. */
    private KeyCode done = KeyCode.SPACE;

    // Gamemode flag
    /** A final boolean attribute that represents whether the current mode is multiplayer. */
    private final boolean MULTIPLAYER;
    /** A final boolean attribute that represents whether the current mode is blitz. */
    private final boolean BLITZ;

    // Data relevant only to blitz mode
    /** A static final long attribute that keeps track of how long blitz mode is.   */
    private static final long BLITZ_LENGTH_NS = (long)120e9;
    /** A static final long attribute that keeps track of how long the frenzy period is.   */
    private static final long FRENZY_LENGTH_NS = (long)3e9;
    /** A static final int attribute that keeps track of how fast the blocks are falling in a frenzy attack.   */
    private static final int FRENZY_SPEED_SCALAR = 3;
    /** A long attribute that represents the start time for a frenzy attack.  */
    private long frenzyStartTimeNs;
    /** An int attribute that represents how many rows are currently eliminated.   */
    private int rowsEliminated;
    /** A TetrominoWorld attribute that represents the opponents Tetris grid. */
    private TetrominoWorld opponent;
    /** A keycode attribute that represents a call for the F key (initiates frenzy attack).  */
    private KeyCode mpFrenzy = KeyCode.F;     
    /** A keycode attribute that represents a call for the D key (initiates drop attack). */
    private KeyCode mpDrop = KeyCode.D;     

    // Attributes
    /** A long attribute that represents delay between display updates. */
    private long delay;           
    /** A long attribute that represents delay in seconds. */
    private long delayAccel;     
    /** A long attribute representing the last run of the act() method. */
    private long lastRun;      
    /** A boolean attribute that represents whether a new Tetromino block should be spawned. */
    private boolean spawnNew;       
    /** A boolean attribute representing whether the game is over. */
    private boolean gameOver;
    /** A Game attribute representing the Game.java class. */
    private Game game;

    /** An ArrayList of ImageView objects that represent the set of falling blocks. */
    private ArrayList<ImageView> fallingBlocks; 
    /** An ArrayList of ImageView objects representing the next falling blocks. */
    private ArrayList<ImageView> nextBlocks; 
    /** An integer attribute representing the ID of the next falling tetromino. */
    private int nextTetromino;             

    // Constructors
    
    /**
     * <PRE>
     *A custom constructor that creates a new TetrominoWorld which can be implemented into a Game.java class. 
     * This constructor initalizes many attributes in accordance to how the game functions at the beginning of it's 
     * implementation. 
     * 
     * @param game Represents the Game.java class (inputed as "this" when used in the Game.java Class)
     * @param delay represents the grid delay in display updates.
     * </PRE>
     */
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
    /**
     * <PRE>
     * A custom constructor that creates a new TetrominoWorld which can be implemented into a Game.java class. 
     * This constructor initalizes many attributes in accordance to how the game functions at the beginning of it's 
     * implementation. 
     * 
     * @param game Represents the Game.java class (inputed as "this" when used in the Game.java Class).
     * @param delay Represents the grid delay in display updates.
     * @param delayAccel Represents the change in the acceleration of the delay in seconds.
     * </PRE>
     */
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

    /**
     * <PRE>
     * A custom constructor that creates a new TetrominoWorld which can be implemented into a Game.java class. 
     * This constructor initalizes many attributes in accordance to how the game functions at the beginning of it's 
     * implementation. 
     * 
     * @param game Represents the Game.java class (inputed as "this" when used in the Game.java Class).
     * @param delay Represents the grid delay in display updates.
     * @param delayAccel Represents the change in the acceleration of the delay in seconds.
     * @param mode Represents the specific GameMode the TetrominoWorld is.
     * </PRE>
     */
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
    /** 
     * <PRE>
     * Sets the opponent TetrominoWorld.
     * @param opponent Represents the opponent's TetrominoWorld.
     * </PRE>
     */
    public void setOpponent(TetrominoWorld opponent) { this.opponent = opponent; }
    /** 
     * <PRE>
     * Sets the left keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setLeft(KeyCode k) { this.left = k; }
    /** 
     * <PRE>
     * Sets the right keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setRight(KeyCode k) { this.right = k; }
    /** 
     * <PRE>
     * Sets the down keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setDown(KeyCode k) { this.down = k; }
    /** 
     * <PRE>
     * Sets the rotation keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setRot(KeyCode k) { this.rotate = k; }
    /** 
     * <PRE>
     * Sets the done keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setDone(KeyCode k) { this.done = k; }
    /** 
     * <PRE>
     * Sets the frenzy keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setMPFrenzy(KeyCode k) { this.mpFrenzy = k; }
    /** 
     * <PRE>
     * Sets the drop keycode.
     * @param k Represents the keycode.
     * </PRE>
     */
    public void setMPDrop(KeyCode k) { this.mpDrop = k; }
    /** 
     * <PRE>
     * Gets the left keycode.
     * </PRE>
     */
    public KeyCode getLeft() { return this.left; }
    /** 
     * <PRE>
     * Gets the right keycode.
     * </PRE>
     */
    public KeyCode getRight() { return this.right; }
    /** 
     * <PRE>
     * Gets the down keycode.
     * </PRE>
     */
    public KeyCode getDown() { return this.down; }
    /** 
     * <PRE>
     * Gets the rotation keycode.
     * </PRE>
     */
    public KeyCode getRot() { return this.rotate; }
    /** 
     * <PRE>
     * Gets the done keycode.
     * </PRE>
     */
    public KeyCode getDone() { return this.done; }
    /** 
     * <PRE>
     * Gets the frenzy keycode.
     * </PRE>
     */
    public KeyCode getMPFrenzy() { return this.mpFrenzy; }
    /** 
     * <PRE>
     * Gets the drop keycode.
     * </PRE>
     */
    public KeyCode getMPDrop() { return this.mpDrop; }

    // Main loop function
    
    /**
     * <PRE>
     * The act method is constantly called and has the responsibility of telling the Blocks what to do. The act method
     * tells the blocks when the game is over, is currently in a collision, is rotating, etc. 
     * 
     * @param now represents the current time.
     * </PRE>
     */
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
                            if (k == i) iter.remove();
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

    /**
     * <PRE>
     *  Builds a Tetromino from a list of ImageViews of Blocks
     *
     * @param list ArrayList<ImageView> of length 4
     * </PRE>
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

    /**
     * <PRE>
     * Checks whether there is a collision at the coordinates x,y.
     * 
     * @param x represents the coordinate x in pixels
     * @param y represents the coordinate y in pixels
     * @return returns the flag
     * </PRE>
     */
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

    /**
     * Moves the falling tetromino blocks down one block.
     */
    private void moveDown() {

        if (!checkCollisions(0, 1)) {
            for (ImageView view : fallingBlocks) {
                // Move blocks down
                setRowIndex(view, getRowIndex(view) + 1);
            }
        }
        spawnNew = checkCollisions(0, 1);
    }

    /**
     * Ends the game.
     */
    private void endGame() {
        if (game != null) game.endGame();
        stop();
        spawnNew = false;
    }
    

    /**
     * Handles the spawning of tetromino blocks by building the blocks and spawning them onto the tetris grid.
     */
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
    
    /**
     * <PRE>
     * Finds the next Tetromino block that is being spawned.
     * @return Returns the next tetromino block being spawned.
     * </PRE>
     */
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
    /**
     * <PRE>
     * Gets the number of rows eliminated.
     * @return returns number of rows eliminated.
     * </PRE>
     */
    public int getRowsEliminated() { return rowsEliminated; }
    
    /**
     * Drops the current tetromino blocks on the enemy's screen if there is a row eliminated.
     */
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

    /**
     * Drops the current tetromino blocks on the screen to the bottom.
     */
    public void drop() {
        while (!checkCollisions(0, 1)) {
            moveDown();
        }
    }

    /**
     * Initiates the frenzy attack.
     */
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

    /**
     * <PRE>
     * Handles the frenzy's delay and timer.
     * @param l represents the time in long
     * @param timer represents the timer.
     * </PRE>
     */
    private void handleFrenzy(long l, AnimationTimer timer) {
        if (frenzyStartTimeNs + FRENZY_LENGTH_NS <= l) {
            delay *= FRENZY_SPEED_SCALAR;
            timer.stop();
        }
        act(l);
    }
}
