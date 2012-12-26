package great_old_one;

import board.TetrisBoard;

import java.awt.Color;

/**
 * This class represents Cthulhu of the Great Old Ones.
 * Credit for the artwork goes to reau (DeviantArt.com).
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
public class Cthulhu extends AbstractGreatOldOne
{
  /**
   * The difficulty mod for this character.
   */
  private static final int DIFFICULTY_MOD = 3;
  /**
   * The current board in use.
   */
  private final TetrisBoard my_board;
  
  /**
   * This is the constructor for the Cthulhu class.
   * @param the_board The board currently in use.
   */
  public Cthulhu(final TetrisBoard the_board)
  {
    super("Cthulhu", 
          "Drowned Earth: \nEliminates last two rows from the board.",
          "cthulhu.jpg",
          DIFFICULTY_MOD, 
          the_board, 
          Color.GREEN);
    my_board = the_board;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doPower()
  {
    my_board.clearRow(my_board.getMaxY() - 1);
    my_board.clearRow(my_board.getMaxY() - 1);
  }
  
}
