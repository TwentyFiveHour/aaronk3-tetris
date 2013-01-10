package great_old_one;

import java.awt.Color;

import piece.IBlock;

import board.TetrisBoard;

/**
 * The King in Yellow class (playable character).
 * Credit for the artwork goes to "gutterball" (DeviantArt.com).
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
public class KingInYellow extends AbstractGreatOldOne
{
  private final TetrisBoard my_board;
  public KingInYellow(final TetrisBoard the_board)
  {
    super("The King in Yellow", "Dark Designs:\nReplace the next block with an I block.",
          "king_in_yellow.jpg", 1, the_board, Color.YELLOW);
    my_board = the_board;
  }

  @Override
  public void doPower()
  {
    my_board.setNextBlock(new IBlock(my_board.getMaxX() / 2,  0));
    
  }
   
}
