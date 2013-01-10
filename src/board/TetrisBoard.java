
package board;


import great_old_one.Cthulhu;
import great_old_one.DefaultCharacter;
import great_old_one.AbstractGreatOldOne;
import great_old_one.IGreatOldOne;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import piece.Block;
import piece.BlockElement;
import piece.IBlock;
import piece.JBlock;
import piece.LBlock;
import piece.OBlock;
import piece.SBlock;
import piece.TBlock;
import piece.ZBlock;


/**
 * This class keeps track of the Tetris board and implements
 * the rules of the game.  This includes collision detection,
 * freezing fallen blocks, creating new blocks, and clearing filled
 * rows.
 * @author Aaron Kaufman
 * @version 2 15 2012
 *
 */
public class TetrisBoard extends Observable
{
  
  /**
   * Enumerated class defining the different game modes.
   * @author Aaron Kaufman
   *
   */
  public enum gameMode
  {
    /**
     * The normal game of Tetris, where each block is randomly established from a 
     * list of block types.
     */
    RANDOM, 
    /**
     * Only O blocks will be generated in this game.
     */
    OBLOCK,
    /**
     * Only O blocks will be generated in this game, AND
     * blocks will start below the headspace for easier testing.
     * 
     */
    TEST
  }
    
  
  /**
   * Enumerated class defining all blocks in the game of Tetris.
   * Assigns each one a number for purpose of random generation.
   * @author Aaron Kaufman
   *
   */
  public enum StartingBlocks 
  {
    O(1, new OBlock(DEFAULT_MAX_X / 2, 0)),
    L(2, new LBlock(DEFAULT_MAX_X / 2, 0)), 
    J(3, new JBlock(DEFAULT_MAX_X / 2, 0)),
    S(4, new SBlock(DEFAULT_MAX_X / 2, 0)), 
    Z(5, new ZBlock(DEFAULT_MAX_X / 2, 0)), 
    T(6, new TBlock(DEFAULT_MAX_X / 2, 0)),
    I(7, new IBlock(DEFAULT_MAX_X / 2, 0));
    /**
     * The assigned number for a given blockType.
     */
    private final int my_number;
    /**
     * The block object for this blockType.
     */
    private final Block my_block;
    /**
     * blockType constructor.
     * @param the_num The number for the blockType.
     * @param the_block The Block object for t
     */
    
    private StartingBlocks(final int the_num, final Block the_block)
    {
      my_number = the_num;
      my_block = the_block;
    }
    /**
     * Returns the class representing the BlockType.
     * @return The class representing the BlockType.
     */
    private Block getBlock()
    {
      return my_block;
    }
    /**
     * Returns the number of the BlockType.
     * @return The number of the BlockType.
     */
    private int getNum()
    
    {
      return my_number;
    }
  }
  /**
   * The default width of the board.
   */
  private static final int DEFAULT_MAX_X = 10;
  /**
   * Sets the default score which causes the level to go up by one.
   */
  private static final int DEFAULT_SCORE_PER_LEVEL = 100;
  
  /**
   * The default height of the board.
   */
  private static final int DEFAULT_MAX_Y = 25;
  /**
   * The default amount of sapce not shown on the
   *  board (where pieces are placed before falling).
   *  The toString does not show this portion of the board.
   */
  private static final int HEAD_SPACE = 4;
  /**
   * The char representing active (dropping) blocks.
   */
  private static final char ACTIVE_CHAR = 'A';
  /**
   * The char representing inactive (dropped) blocks.
   */
  private static final char INACTIVE_CHAR = 'B';
  /**
   * The char representing a clear space on the board.
   */
  private static final char NOTHING_CHAR = '.';
  /**
   * The default time step for the board.
   */
  private static final int DEFAULT_TIME_STEP = 500;
  /**
   * The score given for each piece dropped.
   */
  private static final int SCORE_PER_DROP = 10;
  
  private static final double TIME_MULTIPLIER_PER_DIFFICULTY = .75;
  /**
   * The array of blocks passed by the user, to be used by the game
   * in order.
   */
  private Block[] my_block_array; 
  /**
   * Whether the game is ended.
   */
  private boolean my_game_ended;
  /**
   * The player's current score.
   */
  private int my_score;
  /**
   * The timer used for involuntary block dropping.
   */
  private Timer my_timer;
  
  /**
   * The mode being used for this game of Tetris.
   */
  private int my_difficulty;
  /**
   * The current game mode.
   */
  private gameMode my_game_mode;
  /**
   * The existing number of block types.
   */
  private int my_num_block_types;
  /**
   * The number of blocks created thus far on this board.
   */
  private int my_num_blocks_created;
  /**
   *  A Map used in generating blocks from random numbers.
   */
  private final Map<Integer, Block> my_num_to_block;
 
  /**
   * A boolean array representing all spaces and inactive pieces on the board. 
   */
  private boolean[][] my_placed_pieces;
  private int my_max_x;
  private int my_max_y;
  /**
   * The current Great Old One used by this class.
   */
  private IGreatOldOne my_great_old_one;
  /**
   * The great old one to be used in the next game.
   */
  private AbstractGreatOldOne my_great_old_one_next;
  /**
   * The current block being dropped.
   */
  private Block my_current_block;
  /**
   * The block set to be dropped after this one.
   */
  private Block my_next_block;
/**
 * Constructor for the tetris board.
 * @param the_x The width of the board.
 * @param the_y The height of the board.
 * @param the_mode the game mode the game initializes with.
 * @param the_block_array The array of blocks this board
 * will start out using.
 */
  public TetrisBoard(final int the_x, final int the_y, final gameMode the_mode,
                     final Block[] the_block_array)
  {
    
    super();
    my_great_old_one = new DefaultCharacter(this);
    my_great_old_one_next = new DefaultCharacter(this);
    
    my_score = 0;
    my_timer = new Timer(DEFAULT_TIME_STEP, new TimerListener(this));
    my_max_x = the_x;
    my_max_y = the_y;
    my_game_mode = the_mode;
    my_game_ended = false;
    my_difficulty = 0;
    
    my_num_to_block = new HashMap<Integer, Block>();
    setNumToBlock();
    my_placed_pieces = new boolean[my_max_x][my_max_y + HEAD_SPACE];
    
    setBlockArray(the_block_array);
    my_num_blocks_created = 0;
    
    decideDifficulty();
    
    createNewBlock(my_game_mode);
    createNewBlock(my_game_mode);
    //Pre-loads This and Next block before game starts.
    
  }
  /**
   * The constructor for the TetrisBoard class. Initializes to default
   * width and height values.
   */
  public TetrisBoard()
  {
    this(DEFAULT_MAX_X, DEFAULT_MAX_Y, gameMode.RANDOM, new Block[0]);
  }
  
  
  /**
   * Constructor for the TetrisBoard class.
   * @param the_x The width of the board.
   * @param the_y The height of the board.
   * @param the_mode The game mode the board starts in.
   */
  public TetrisBoard(final int the_x, final int the_y, final gameMode the_mode)
  {
    this(the_x, the_y, the_mode, new Block[0]);
  }  
/**
 * Sets the value of my_block_array to the_block_array.
 * @param the_block_array The block array.
 */
  private void setBlockArray(final Block [] the_block_array)
  {
    my_block_array = new Block[the_block_array.length];
    System.arraycopy(the_block_array, 0, my_block_array,
                     0, the_block_array.length);

  }
  /**
   * Sets the game mode for Tetris.
   * @param the_game The game mode.
   */
  public void setGameMode(final gameMode the_game)
  {
    my_game_mode = the_game;
  }
  /**
   * Returns the maximum x coordinate of the board.
   * @return the maximum x coordinate of the board.
   */
  public int getMaxX()
  {
    return my_max_x;
  }
  /**
   * Creates the map assigning each number to each block type.
   */
  private void setNumToBlock()
  {
    my_num_block_types = 0;
    for (StartingBlocks b : StartingBlocks.values())
    {
      my_num_to_block.put(b.getNum(), b.getBlock());
      my_num_block_types++;
    }
  }
  /**
   * Given an int, returns a certain block.
   * @param the_num The number of the desired block.
   * @return The Block object.
   */

  private Block getBlockFromInt(final int the_num)
  {
    return my_num_to_block.get(the_num);
    
  }
  /**
   * Sets the next block to the given block.
   * @param the_block The new next block.
   */
  public void setNextBlock(final Block the_block)
  {
    my_next_block = the_block;
  }
  
  
  /**
   * attempts a rotation of the current active block.
   */
  public void attemptRotate()
  {
    final Block next = attemptPlacement(my_current_block.rotate());
    if (!next.equals(my_current_block))
    {
      setChanged();
      notifyObservers(new TetrisData());
    }
    my_current_block = next;
    
  }
  /**
   * Sets the current block equal to my_next_block, and then
   * makes my_next_block instantiate a new Block according to the game mode.
   * 
   * @param the_game The game mode.
   */
  private void createNewBlock(final gameMode the_game)
  {
    my_current_block = my_next_block;
    
    if (my_num_blocks_created < my_block_array.length)
    {
      my_next_block = my_block_array[my_num_blocks_created];
    }
    
    else if (the_game.equals(gameMode.RANDOM))
    {
      my_next_block = getRandomBlock();
    }
    else if (the_game.equals(gameMode.OBLOCK) ||
        the_game.equals(gameMode.TEST))
    {
      my_next_block = new OBlock(my_max_x / 2, 0);
    }
    my_num_blocks_created++;
    if (the_game.equals(gameMode.TEST))
    {
      my_next_block = my_next_block.moveBlockTo(my_max_x / 2, HEAD_SPACE);
    }
    else 
    {
      my_next_block = my_next_block.moveBlockTo(my_max_x / 2, 0);
    }
   
  }
  
/**
 * Attempts to move along the X axis.  
 * If this is impossible, it does not move the current block.
 * @param the_right_dir The boolean value signaling the direction "right".
 */
  public void attemptMoveX(final boolean the_right_dir)
  {
    int translation = -1;

    if (the_right_dir)
    {
      translation = 1;
    }
    final Block new_block = my_current_block.translateX(translation);

    if (!attemptPlacement(new_block).equals(my_current_block))
    {
      my_current_block = new_block;
      setChanged();
      notifyObservers(new TetrisData());
    }
  }
  /**
   * Method which checks to see if a block moved to a certain location is valid.
   * Checks both boundaries of the board and the position of inactive blocks.
   * Returns the position of the block resulting from this transformation.
   * (If the position is invalid, the block returned will be the original block.)
   * @param the_new_block The block that we are checking the validity of.
   * @return The new Block object.
   */
  private Block attemptPlacement(final Block the_new_block)
  {
    
    final Set<BlockElement> elements = the_new_block.getElements();
    boolean invalid = false;
    for (BlockElement b : elements)
    {
      final int absolute_x = b.x() + the_new_block.getX();
      final int absolute_y = b.y() + the_new_block.getY();
      // Boundary checking!
      if (absolute_x >= my_max_x || absolute_x < 0)
      {
        invalid = true;
      }
      else if (absolute_y >= my_max_y + HEAD_SPACE || absolute_y < 0)
      {
        invalid = true;
      }

      // Overlapping-another-piece checking!

      else if (my_placed_pieces[absolute_x][absolute_y])
      {
        invalid = true;
      }
    }

    Block returned_block;

    if (invalid)
    {
      returned_block = my_current_block;
    }
    else
    {
      returned_block = the_new_block;
    }

    return returned_block;

  }

  /**
   * Attempts to lower the block.  If the block cannot be lowered, the method freezes it in
   * place and starts a new block in the default position.
   */
  private void forceLowerBlock()
  {
    final Block new_block = attemptPlacement(my_current_block.translateY(1));
    if (new_block.equals(my_current_block))
    // Signifies the new location was invalid for some reason
    {
      for (BlockElement b : my_current_block.getElements())
      {
        final int absolute_x = b.x() + my_current_block.getX();
        final int absolute_y = b.y() + my_current_block.getY();
        my_placed_pieces[absolute_x][absolute_y] = true;
        if (absolute_y < HEAD_SPACE)
        {
          endGame();
        }
      }
      createNewBlock(my_game_mode);
      my_score += SCORE_PER_DROP;
      checkFilled();
    }
    else
    {
      my_current_block = new_block;
    }
    setChanged();
    notifyObservers(new TetrisData());
  }
  /**
   * Voluntary action to lower block by user; will not freeze.
   */
  
  public void attemptLowerBlock()
  {
    my_current_block = attemptPlacement(my_current_block.translateY(1));
    setChanged();
    notifyObservers(new TetrisData());
  }

  /**
   *
   * @return A Block object of a random type.
   */
  private Block getRandomBlock()
  {
    
    
    final Random rand = new Random();

    final int block_num = rand.nextInt(my_num_block_types) + 1;
    final Block new_block = getBlockFromInt(block_num);
    
    return new_block;

  }
  /**
   * Sets the difficulty to the desired quantity.
   * @param the_difficulty The new game difficulty.
   */
  public void changeDifficulty(final int the_difficulty)
  {
    my_difficulty = the_difficulty;
    my_timer.setDelay((int) (DEFAULT_TIME_STEP * Math.pow(TIME_MULTIPLIER_PER_DIFFICULTY, 
                               the_difficulty + my_great_old_one.getDifficultyModifier())));
  }
  /**
   * Called whenever new blocks are placed; if score is above a certain milestone,
   * sets difficulty according to that milestone.
   */
  private void decideDifficulty()
  {
    changeDifficulty((int) (my_score / DEFAULT_SCORE_PER_LEVEL));
  }
  
  /**
   * Method which checks to see if a row needs to be cleared. 
   */
  private void checkFilled()
  {
    boolean is_filled;
    for (int y = 0; y < my_max_y + HEAD_SPACE; y++)
    {
      is_filled = true;
      for (int x = 0; x < my_max_x; x++)
      {
        if (!my_placed_pieces[x][y])
        {
          is_filled = false;
          break;
        }
      }
      if (is_filled)
      {
        clearRow(y);
        y--;
      }

    }
    decideDifficulty();
  }
  /**
   * Method which clears a row, moving all rows above it down one.
   * @param the_y  The row to be cleared.
   */
  public void clearRow(final int the_y)
  {
    for (int y = the_y; y > 0; y--)
    {
      for (int x = 0; x < my_max_x; x++)
      {
        my_placed_pieces[x][y] = my_placed_pieces[x][y - 1];
      }

    }
    //Getting the top row cleared.
    for (int x = 0; x < my_max_x; x++)
    {
      my_placed_pieces[x][0] = false;
    }
  }
  /**
   * Moves the game along by one tick.
   */
  public void step()
  {
    forceLowerBlock();
    my_great_old_one.decrementPowerTimer();
    setChanged();
    notifyObservers(new GreatOldOneData());
  }
  
  public void updateOldOneGUI()
  {
    setChanged();
    notifyObservers(new GreatOldOneData());
  }
  
  /**
   * Returns the current block in this game.
   * @return the current block in this game.
   */
  
  public Block getCurrentBlock()
  {
    return my_current_block;
  }
  /**
   * Creates a char[][] representing the current state of the board.
   * @return The char[][] representing the current state of the board.
   */
  public char[][] makeCharBoard()
  {
    
    final char[][] board_char = new char[my_max_x][my_max_y + HEAD_SPACE];
 
    for (int x = 0; x < my_max_x; x++)
    {
      for (int y = 0; y < my_max_y + HEAD_SPACE; y++)
      {

        if (my_placed_pieces[x][y])
        {
          board_char[x][y] = INACTIVE_CHAR;
        }
        else
        {
          board_char[x][y] = NOTHING_CHAR;
        }

      }
    }
    for (BlockElement b : my_current_block.getElements())
    {
      final int absolute_x = b.x() + my_current_block.getX();
      final int absolute_y = b.y() + my_current_block.getY();
      board_char[absolute_x][absolute_y] = ACTIVE_CHAR;
    }
    return board_char;
  }
/**
 * {@inheritDoc}
 * 
 */
  @Override
  public String toString()
  {

    final StringBuilder s = new StringBuilder();

    final char[][] board_char = makeCharBoard();

    for (int y = HEAD_SPACE; y < my_max_y + HEAD_SPACE; y++)
    {
      for (int x = 0; x < my_max_x; x++)
      {
        s.append(board_char[x][y]);
      }
      s.append("\n");
    }

    return s.toString();
  }
  
  /**
   * Method which sets a spot on the board to be active or inactive.
   * @param the_x  The x coordinate of the spot.
   * @param the_y  The y coordinate of the spot.
   * @param the_val Whether the spot is filled or not.  True indicates filled.
   * False indicates cleared.
   */
  public void fillSpace(final int the_x, final int the_y, final boolean the_val)
  {
    my_placed_pieces[the_x][the_y] = the_val;
  }
  /**
   * Returns the number of blocks created thus far in the game.
   * @return The number of blocks created so far in the game.
   */
  public int getNumDropped()
  {
    return my_num_blocks_created;
  }

  /**
   * Starts the timer on the TetrisBoard.
   */
  public void startTimer()
  {
    if (!my_game_ended)
    {
      my_timer.start();
    }
  }
  /**
   * Gets the current Timer object for the board.
   * @return the Timer object for the board.
   */
  public Timer getTimerObject()
  {
    return my_timer;
  }
  /**
   * Drops a piece to the bottom of the screen,
   *  stopping where it hits another piece or the floor.
   */
  public void instantDrop()
  {
    while (!my_current_block.equals(attemptPlacement(my_current_block.translateY(1))))
    {
      step();
    }
  }  
  /**
   * Pauses the game.
   */
  public void pause()
  {
    my_timer.stop();
  }
  /**
   * Returns whether the game is paused.
   * @return Returns true if game is paused.
   */
  public boolean isPaused()
  {
    return !my_timer.isRunning();
  }
  /**
   * Starts a new game.
   */
  public void startNewGame()
  {
    my_score = 0;
    createNewBlock(my_game_mode);
    createNewBlock(my_game_mode);
    
    my_great_old_one = my_great_old_one_next;
    
    
    my_placed_pieces = new boolean[my_max_x][my_max_y + HEAD_SPACE];
    my_game_ended = false;
    
    my_timer.start();
    decideDifficulty();
    my_great_old_one.reset();
    
    setChanged();
    notifyObservers(new TetrisData());
    
    
  }
  
  /**
   * Checks whether the game is ended or not.
   * @return If the game is ended, returns true.
   */
  public boolean isGameEnded()
  {
    return my_game_ended;
  }
  /**
   * Ends the game.
   */
  public void endGame()
  {
    my_timer.stop();
    my_game_ended = true;
    setChanged();
    notifyObservers(new TetrisData());
  }
  
  /**
   * Returns the current length of the board (including headspace).
   * @return the current length of the board.
   */
  public int getMaxY()
  {
    return my_max_y + HEAD_SPACE;
  }
  /**
   * Returns a new TetrisData object corresponding to the board state.
   * @return a new TetrisData object corresponding to the board state.
   */
  public TetrisData getData()
  {
    return new TetrisData();
  }
  /**
   * Changes the Great Old One to a different one selected by player.
   * @param the_character The new character chosen by the player.
   */
  public void changeCharacter(final AbstractGreatOldOne the_character)
  {
    my_great_old_one_next = the_character;
  }
  /**
   * Attempts to use the current great old one's power.
   */
  public void doPower()
  {
    my_great_old_one.doAbility();
  }
  
  public GreatOldOneData getOldOneData()
  {
    return new GreatOldOneData();
  }
  
  /**
   * An ActionListener used to advance the board every time step.
   * @author Aaron Kaufman
   *
   */
  public class TimerListener implements ActionListener
  {
    /**
     * The board the TimerListener is attached to.
     */
    private final TetrisBoard my_board;
    /**
     * The constructor for the TimerListener class.
     * @param the_board The board the TimerListener is attached to.
     */
    public TimerListener(final TetrisBoard the_board)
    {
      my_board = the_board;
    }
    @Override
    public void actionPerformed(final ActionEvent the_arg)
    {
      my_board.step();
    }
    
    
  }
  /**
   * An object that acts as a "container" for data representing the current board-state.
   * @author Aaron Kaufman
   *
   */
  public class TetrisData extends Observable
  {
    /***
     * The current state of the board in characters.
     */
    private final char[][] my_board_data;
    /**
     * The current score.
     */
    private final int my_score_data;
    /**
     * The next block to be dropped by the board.
     */
    private final Block my_next_block_data;
    /**
     * The width of the board in blocks.
     */
    private final int my_max_x_data;
    /**
     * The height of the board in blocks.
     */
    private final int my_max_y_data;
    /**
     * Whether the game is ended or not.
     */
    private final boolean my_game_ended_data;
    /**
     * The constructor for this class.
     */
    public TetrisData()
    {
      super();
      my_board_data = makeCharBoard();
      my_max_x_data = my_max_x;
      my_max_y_data = my_max_y;
      my_score_data = my_score;
      my_next_block_data = my_next_block;
      my_game_ended_data = my_game_ended;
      
    }
    /**
     * Returns a representation of the board in characters.
     * @return a representation of the board in characters.
     */
    public char[][] getBoard()
    {
      //makeCharBoard creates a new representation of the board, 
      //and returns that, which is what this method returns.
      return my_board_data;
    }
    /**
     * Returns the score.
     * @return the score.
     */
    public int getScore()
    {
      return my_score_data;
    }
    /**
     * Returns the height of the board in blocks.
     * @return the height of the board in blocks.
     */
    public int getY()
    {
      return my_max_y_data;
    }
    /**
     * Returns the width of the board in blocks.
     * @return the width of the board in blocks.
     */
    public int getX()
    {
      return my_max_x_data;
    }
    /**
     * returns the next block.
     * @return The next block.
     */
    public Block getNextBlock()
    {
      return my_next_block_data;
    }
    /**
     * Returns whether the game is ended or not.
     * @return Whether the game is ended or not.
     */
    public boolean isGameEnded()
    {
      return my_game_ended_data;
    }
    /**
     * Returns the current game difficulty.
     * @return the current game difficulty.
     */
    public int getDifficulty()
    {
      return my_difficulty;
    }
    
  }
  
  public class GreatOldOneData
  {
    private final String my_name_data;
    private final String my_power_name_data;
    private final Color my_color_data;
    private final int my_difficulty_mod_data;
    private final ImageIcon my_image_icon_data;
    private final int my_power_timer_data;

    public GreatOldOneData()
    {
      my_name_data = my_great_old_one.getName();
      my_power_name_data = my_great_old_one.getPowerName();
      my_color_data = my_great_old_one.getColor();
      my_difficulty_mod_data = my_great_old_one.getDifficultyModifier();
      my_image_icon_data = my_great_old_one.getIcon();
      my_power_timer_data = my_great_old_one.getPowerTimer();
    }

    public String getName()
    {
      return my_name_data;

    }

    public String getPowerName()
    {
      return my_power_name_data;

    }

    public Color getColor()
    {
      return my_color_data;
    }

    public ImageIcon getIconData()
    {
      return my_image_icon_data;
    }

    public int getTimer()
    {
      return my_power_timer_data;
    }

    public int getDifficultyModifier()
    {
      return my_difficulty_mod_data;
    }

  }
}
  

