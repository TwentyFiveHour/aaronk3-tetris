package piece;
/**
 * This class represents sub-blocks 
 * which make up each unique Tetris block.
 * @author Aaron Kaufman
 *@version Winter 2012, 2/1/2012
 */
public final class BlockElement
{
  /**
   * The local x-axis coordinate of this BlockElement relative to the origin of the Block.
   */
  private final int my_x;
  /**
   * The local y-axis coordinate of this BlockElement relative to the origin of the Block.
   */
  private final int my_y;
  /**
   * The constructor for the BlockElement object.
   * @param the_x The x axis coordinate of this 
   * BlockElement relative to the origin of the Block.
   * @param the_y The y axis coordinate of this 
   * BlockElement relative to the origin of the Block.
   */
  public BlockElement(final int the_x, final int the_y)
  {
    my_x = the_x;
    my_y = the_y;
  }
  /**
   * Returns the local x axis value of this BlockElement.
   * @return The local x axis value of this BlockElement.
   */
  public int x()
  {
    return my_x;
  }
  /**
   * Returns the local y axis value of this BlockElement.
   * @return The local y axis value of this BlockElement.
   */
  public int y()
  {
    return my_y;
  }
  /**
   * Returns the String representation of this object.
   * @return The String representation of this object.
   */
  public int hashCode()
  {
    return this.toString().hashCode();
  }
  /**
   * {@inheritDoc}
   */
  public boolean equals(final Object the_other_object)
  {
    boolean is_equal = true;
    if (the_other_object == null || this.getClass() != the_other_object.getClass())
    {
      return false;
    }
    final BlockElement the_other = (BlockElement) the_other_object;
    if (my_x != the_other.x())
    {
      is_equal = false;
    }
    if (my_y != the_other.y())
    {
      is_equal = false;
    }
    return is_equal;
  }
  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    final StringBuffer temp = new StringBuffer(30);
    temp.append(". BlockElement X:  ");
    temp.append(x());
    temp.append("  Y:  ");
    temp.append(y());
  
    return temp.toString();
  }
}
