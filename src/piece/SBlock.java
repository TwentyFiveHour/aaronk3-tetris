package piece;
import java.util.HashSet;
import java.util.Set;
/**
 * Represents the S block in Tetris.
 * @author Aaron Kaufman
 *@version Winter 2012, 2/1/2012
 */
public final class SBlock extends Block
{
/**
 * Constructor for the S block in tetris.
 * @param the_x This object's position on the x-axis.
 * @param the_y This object's position on the y-axis.
 */
  public SBlock(final int the_x, final int the_y)
  {
    super(the_x, the_y, getBlockSet());
  }
/**
 * Helper method containing the set of BlockElements corresponding to this object.
 * @return The set of BlockElements corresponding to this object.
 */
  private static Set<BlockElement> getBlockSet()
  {
    final Set<BlockElement> temp_set = new HashSet<BlockElement>();
    final BlockElement a = new BlockElement(0, 0);
    final BlockElement b = new BlockElement(1, 1);
    final BlockElement c = new BlockElement(1, 0);
    final BlockElement d = new BlockElement(2, 1);

    temp_set.add(a);
    temp_set.add(b);
    temp_set.add(c);
    temp_set.add(d);

    return temp_set;
  }

}
