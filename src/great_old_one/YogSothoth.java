package great_old_one;

import board.TetrisBoard;
import java.awt.Color;

/**
 * The Yog Sothoth playable character.
 * Credit for the artwork goes to Patrick McEvoy.
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
public class YogSothoth extends AbstractGreatOldOne
{
  private final TetrisBoard my_board;
  public YogSothoth(final TetrisBoard the_board)
  {
    super("Yog'Sothoth", "Terrible Knowledge:\nfills half of both left and right" +
        " sides of the board with blocks.",
          "yog_sothoth.jpg",
          1, the_board, Color.RED);
    my_board = the_board;
  }

  @Override
  public void doPower()
  {
    for (int i = my_board.getMaxY() - 1; i > (int) (my_board.getMaxY() / 2); i--)
    {
      my_board.fillSpace(0, i, true);
      my_board.fillSpace(my_board.getMaxX() - 1, i, true);
    }
  }
  
}
