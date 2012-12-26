package piece;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * A test case for the Block class and subclasses.
 * 
 * @author Aaron Kaufman
 *@version Winter 2012, 2/1/2012
 */
public class BlockTest
{
  /**
   * A String containing the positional info in the toString method.
   */
  private String my_init_coordinates;
  /**
   * An S block object at 0,0.
   */
  private Block my_s;
  /**
   * An I block object at 0,0.
   */
  private Block my_i;
  /**
   * An O block object at 0,0.
   */
  private Block my_o;
  /**
   * A J block object at 0,0.
   * 
   */
  private Block my_j;
  /**
   * An L block object at 0,0.
   */
  private Block my_l;
  /**
   * A Z block object at 0,0.
   */
  private Block my_z;
  /**
   * A T block object at 0,0.
   */
  private Block my_t;

  /**
   * Sets up the current battery of tests.
   */

  @Before
  public void setUp()
  {

    my_init_coordinates = "\n\nX axis position:  0\nY axis position:  0";
    my_s = new SBlock(0, 0);
    my_i = new IBlock(0, 0);
    my_o = new OBlock(0, 0);
    my_j = new JBlock(0, 0);
    my_l = new LBlock(0, 0);
    my_z = new ZBlock(0, 0);
    my_t = new TBlock(0, 0);

  }

  /**
   * Tests each rotation of T, up to four rotations. Passing this test is
   * assumed to indicate success of the transformation governing the rotate()
   * method for a size 3 block.
   */
  @Test
  public void testRotationTFull()
  {
    Block t = new TBlock(0, 0);
    assertEquals("T not initialized properly", "xxx\n***\nx*x" + my_init_coordinates,
                 t.toString());
    t = t.rotate();
    assertEquals("T rotation failed (first rotation)", "x*x\n**x\nx*x" + my_init_coordinates,
                 t.toString());
    t = t.rotate();
    assertEquals("T rotation failed (second rotation)", "x*x\n***\nxxx" + my_init_coordinates,
                 t.toString());
    t = t.rotate();
    assertEquals("T rotation failed (third rotation)", "x*x\nx**\nx*x" + my_init_coordinates,
                 t.toString());
    t = t.rotate();
    assertEquals("T rotated four times is not the same as the" + "original", my_t.toString(),
                 t.toString());
  }

  /**
   * Tests the initial L formation.
   */
  @Test
  public void testInitialL()
  {

    assertEquals("L not initialized properly", "xxx\n***\n*xx" + my_init_coordinates,
                 my_l.toString());
  }

  /**
   * tests the initial Z block formation.
   */
  @Test
  public void testInitialZ()
  {

    assertEquals("Z not initialized properly", "**x\nx**\nxxx" + my_init_coordinates,
                 my_z.toString());
  }

  /**
   * 
   * Tests each rotation of I, up to four rotations. Passing this test is
   * assumed to indicate success of the transformation governing the rotate()
   * method for a size 4 block.
   */
  @Test
  public void testRotationIFull()
  {

    Block i = new IBlock(0, 0);
    assertEquals("I not initialized properly", "xxxx\n****\nxxxx\nxxxx" + my_init_coordinates,
                 i.toString());
    i = i.rotate();
    assertEquals("T rotation failed (first rotation)", "xx*x\nxx*x\nxx*x\nxx*x" +
                                                       my_init_coordinates, i.toString());
    i = i.rotate();
    assertEquals("T rotation failed (second rotation)", "xxxx\nxxxx\n****\nxxxx" +
                                                        my_init_coordinates, i.toString());
    i = i.rotate();
    assertEquals("T rotation failed (third rotation)", "x*xx\nx*xx\nx*xx\nx*xx" +
                                                       my_init_coordinates, i.toString());
    i = i.rotate();
    assertEquals("T rotated four times is not the same as the" + "original", my_i.toString(),
                 i.toString());

  }

  /**
   * tests the initial S block formation.
   */
  @Test
  public void testInitialS()
  {
    assertEquals("S not initialized correctly", my_s.toString(), "*xx\n**x\nx*x" +
                                                                 my_init_coordinates);
  }

  /**
   * tests the initial J block formation.
   */
  @Test
  public void testInitialJ()
  {
    assertEquals("J not initialized correctly", my_j.toString(), "*xx\n***\nxxx" +
                                                                 my_init_coordinates);
  }

  /**
   * tests the initial O block rotation and the unique rotate() method that the
   * o block possesses.
   */
  @Test
  public void testRotationO()
  {
    assertEquals("O block not initialized properly!", "**\n**" + my_init_coordinates,
                 my_o.toString());
    assertEquals("O block rotated is not the same!", my_o, my_o.rotate());
  }

  /**
   * @Test public void testJBlockFullRotation() { final Block j_block_rotated =
   *       my_j.rotate().rotate().rotate().rotate();
   *       assertEquals("J block rotated four times changes from original!",
   *       j_block_rotated, my_j);
   *       assertFalse("J block rotated one time does not change from original."
   *       , my_j.rotate().equals(my_j)); }
   */

  /**
   * Tests the translateX method.
   */
  @Test
  public void testTranslationX()
  {
    my_o = my_o.translateX(1);
    assertEquals("Translate X doesn't work properly", my_o.getX(), 1);
  }

  /**
   * Tests the translateY method.
   */
  @Test
  public void testTranslationY()
  {
    my_o = my_o.translateY(1);
    assertEquals("O block not translated correctly!", my_o.getY(), 1);
  }

  /**
   * Tests the equals method for equalities and inequalities.
   */
  @Test
  public void testIsEquals()
  {
    final Set<BlockElement> o_block_set = new HashSet<BlockElement>();
    o_block_set.add(new BlockElement(0, 0));
    o_block_set.add(new BlockElement(0, 1));
    o_block_set.add(new BlockElement(1, 0));
    o_block_set.add(new BlockElement(1, 1));
    final Block new_o = new Block(0, 0, o_block_set);
    final Block copy_o = new OBlock(0, 0);

    assertEquals("Two twin copies of the O block are not equal", my_o, new OBlock(0, 0));
    assertEquals("O Block constructed with Block constructor not equal to O Block.", new_o,
                 copy_o);
    assertFalse("different types are equal!", copy_o.equals(o_block_set));
    assertFalse("different blocks are equal!", my_l.equals(my_o));
    assertFalse("a block is equal to null!", copy_o.equals(null));
  }

}
