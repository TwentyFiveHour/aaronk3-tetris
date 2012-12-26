package great_old_one;

import board.TetrisBoard;

import java.awt.Color;

/**
 * This is the default GreatOldOne for the game.
 * Credit for the artwork goes to LianneC (DeviantArt.com)
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
public class DefaultCharacter extends AbstractGreatOldOne
{
  /**
   * Description for Herbert West's power.
   */
  private static final String POWER_DESC = "Herbert West is a puny human. He has no powers.";
  
  /**
   * Constructor for the DefaultCharacter class.
   * @param the_board The current tetris board.
   */
  public DefaultCharacter(final TetrisBoard the_board)
  {
    super("Herbert West", POWER_DESC, "herbert_west.jpg",
          0, the_board, Color.BLUE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doPower()
  {
  }
  
}
