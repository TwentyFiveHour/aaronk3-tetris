package piece;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the Z block in Tetris.
 * 
 * @author Aaron Kaufman
 *@version Winter 2012, 2/1/2012
 */
public final class ZBlock extends Block
{

  /**
   * Constructor for the TBlock class.
   * 
   * @param the_position_x This block's position on the X axis.
   * @param the_position_y This block's posiiton on the Y axis.
   */
  public ZBlock(final int the_position_x, final int the_position_y)
  {
    super(the_position_x, the_position_y, getBlockSet());
  }

  /**
   * Helper method containing the set of BlockElements corresponding to this
   * object.
   * 
   * @return The set of BlockElements corresponding to this object.
   */
  private static Set<BlockElement> getBlockSet()
  {
    final Set<BlockElement> temp_set = new HashSet<BlockElement>();
    final BlockElement a = new BlockElement(0, 0);
    final BlockElement b = new BlockElement(0, 1);
    final BlockElement c = new BlockElement(1, 1);
    final BlockElement d = new BlockElement(1, 2);

    temp_set.add(a);
    temp_set.add(b);
    temp_set.add(c);
    temp_set.add(d);

    return temp_set;
  }


}
