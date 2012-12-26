package piece;


import java.util.HashSet;
import java.util.Set;



/**
 * A generic Block class representing all blocks in the game of
 * Tetris.
 * @author Aaron Kaufman
 * @version Winter 2012, 2/1/2012
 */
public class Block
{
  /**
   * This block's position on the x axis.
   */

  
  private final int my_position_x;
  /**
   * This block's position on the y axis.
   */
  
  private final int my_position_y;

  /**
   * The size of this Block.
   */
  private final int my_size;
  /**
   * The set of BlockElements corresponding to this block.
   */
  private final Set<BlockElement> my_block_set;
  /**
   * The string expected after the printed boolean array for the toString method at
   * location (0,0).
   */
  
  //Invariants: the Integer[] components of the_initial_formation are exactly 2 elements long.
  //These corrospond to x and y axis.
  /**
   * Constructor for any block in Tetris, given an arrangement of pieces and x and y axis
   * coordinate values.
   * @param the_position_x  This object's position on the x axis.
   * @param the_position_y  This object's position on the y axis.
   * @param the_block_set  The set of BlockElement objects corresponding to this block.
   */
  
  protected Block(final int the_position_x, final int the_position_y, 
               final Set<BlockElement> the_block_set)
  {
    
    my_block_set = the_block_set;
    my_position_x = the_position_x;
    my_position_y = the_position_y;
    my_size = calculateBlockSize();
    
  }
  
  /**
   * Calculates the size of the boolean array needed to hold the current
   * blockset.
   * 
   * @return The necessary array size.
   */

  public final int calculateBlockSize()
  {
    int max_dimension = 0;
    for (BlockElement s: my_block_set)
    {
      if (s.x() > max_dimension)
      {
        max_dimension = s.x();
      }
      if (s.y() > max_dimension)
      {
        max_dimension = s.y();
      }
    }
    return max_dimension + 1;
  }
  /**
   * {@inheritDoc}
   */
  public String toString()
  {
    final boolean [][] block_array = getBlockArray();
    final StringBuffer s = new StringBuffer(100);
    for (int i = 0; i < my_size; i++)
    {
      for (int j = 0; j < my_size; j++)
      {
        if (block_array[i][j])
        {
          s.append('*');
        }
        else
        {
          s.append('x');
        }
      }
      s.append('\n');
    }
    s.append("\nX axis position:  ");
    s.append(my_position_x);
    s.append("\nY axis position:  ");
    s.append(my_position_y);
    return s.toString();
  }
  /**
   * 
   * @return a boolean array representing the current set of blocks.
   */
  public final boolean[][] getBlockArray()
  {
    boolean[][] initial_formation;
    initial_formation = new boolean[my_size][my_size];
    //initializes to false
    for (BlockElement elements: my_block_set)
    {
      initial_formation[elements.x()][elements.y()] = true;
    }
    return initial_formation;
  }
  /**
   * Rotates a block counterclockwise 90 degrees, returning a new block representing the
   * counterclockwise rotation.
   * @return The new block corresponding to 90 degrees counterclockwise rotation.
   */
  public final Block rotate()
  {
    final Set<BlockElement> rotated_block_set = new HashSet<BlockElement>();
    for (BlockElement s : my_block_set)
    {
      final BlockElement translated_element = new BlockElement(s.y(), my_size - s.x() - 1);
      rotated_block_set.add(translated_element);
    } 
    return new Block(my_position_x, my_position_y, rotated_block_set);
    
  }
  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public final int hashCode()
  {
    return this.toString().hashCode();
  }
  
  /**
   * {@inheritDoc}
   * 
   */
  @Override
  public final boolean equals(final Object the_other_object)
  {
    boolean is_equal = true;

    if (!(this instanceof Block)  ||
        !(the_other_object instanceof Block))
    {
      return false;
    }
    final Block the_other = (Block) the_other_object;

    if (!this.toString().equals(the_other.toString()))
    {
      is_equal = false;
    }
    return is_equal;
    
  }
  
  /**
   * Translates the block along the X axis some integral number of spaces
   * to the right.
   * @param the_spaces The number of spaces to translate the block.
   * @return The new Block object corresponding to however many spaces.
   */
  public final Block translateX(final int the_spaces)
  {
    return new Block(my_position_x + the_spaces, my_position_y, my_block_set);
  }


/**
 * Translates the block along the Y axis some integral number of spaces to 
 * the right.
 * @param the_spaces The number of spaces to translate the block.
 * @return The new Block object corresponding to however many spaces.
 */
  public final Block translateY(final int the_spaces)
  {
    return new Block(my_position_x, my_position_y + the_spaces, my_block_set);
  }
  /**
   * Returns the block's position on the y axis.
   * @return the block's position on the y axis.
   */
  public final int getY()
  {
    return my_position_y;
  }
  /**
   * returns the block's position on the x axis.
   * @return the block's position on the x axis.
   */
  public final int getX()
  {
    return my_position_x;
  }
  /**
   * The blockElements that make up this block.
   * @return The Set of BlockElements that make up this Block object.
   */
  public final Set<BlockElement> getElements()
  {
    final Set<BlockElement> copy = new HashSet<BlockElement>();
    for (BlockElement b : my_block_set)
    {
      copy.add(b); 
      //Can do this because BlockElements are immutable.
    }
    return copy;
  }
  /**
   * Moves the active block somewhere specific on the board.
   * @param the_x The x coordinate.
   * @param the_y The y coordinate.
   * @return The Block object resulting from this transformation.
   */
  public final Block moveBlockTo(final int the_x, final int the_y)
  {
    return new Block(the_x, the_y, my_block_set);
  }


}
