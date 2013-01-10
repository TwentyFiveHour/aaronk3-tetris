package board;
/**
 * This class tests whether the TetrisBoard class works correctly in all cases.
 * @author Aaron Kaufman
 * @version 2 15 2012
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import board.TetrisBoard.gameMode;

import org.junit.Before;
import org.junit.Test;

import piece.Block;
import piece.IBlock;
import piece.JBlock;
import piece.LBlock;
import piece.OBlock;
import piece.SBlock;
import piece.TBlock;
import piece.ZBlock;

/**
 * This class tests whether the TetrisBoard class works correctly in all cases.
 * @author Aaron Kaufman
 * @version 2 15 2012
 */
public class BoardTest
{
  /**
   * A tetris board with no inactive pieces placed on it.  Places
   * O blocks only.
   */
  private final TetrisBoard my_blank = new TetrisBoard(6, 6, gameMode.TEST);
  /**
   * A tetris board designed to test clearing one line at once.
   */
  
  private final TetrisBoard my_one_line_populated = new TetrisBoard(6, 6, gameMode.TEST);
  
  /**
   * A tetris board which uses a defined piece creation pattern.
   * Populated with one block for testing rotation.
   */
  private TetrisBoard my_small_pattern;
  /**
   * A tetris board designed to test clearing two lines at once.
   */
  private TetrisBoard my_two_line_populated;
  /**
   * A tetris board designed to test clearing three lines at once.
   */
  private TetrisBoard my_three_line_populated;
  /**
   * A tetris board designed to test clearing four lines at once.
   */
  private TetrisBoard my_four_line_populated;
/**
 * The array of blocks that the big TetrisBoard object starts out
 * using.
 */
  private Block[] my_block_array;
  private final static int HEAD_SPACE = 4;
  
  
  /**
   * Sets up the Populated and Pattern boards.
   */
  
  @Before
  public void setUpTest()
  {
    for (int x = 0; x < 4; x++)
    {
      for (int y = 4 + HEAD_SPACE; y > 2 + HEAD_SPACE; y--)
      {
        my_one_line_populated.fillSpace(x, y, true);
      }
    }
    my_one_line_populated.fillSpace(0, 2 + HEAD_SPACE, true);
    my_block_array = new Block[3];
    my_block_array[0] = new OBlock(0, 0);
    my_block_array[1] = new LBlock(0, 0);
    my_block_array[2] = new JBlock(0, 0);
    
    final Block [] i_block_array = new Block[1];
    i_block_array[0] = new IBlock(0, 0);
    
    
    my_small_pattern = new TetrisBoard(6, 6, gameMode.TEST, my_block_array);
    my_small_pattern.fillSpace(1, 2 + HEAD_SPACE, true);
    
    my_two_line_populated = new TetrisBoard(6,
      10, gameMode.TEST);
    my_three_line_populated = new TetrisBoard(6, 10,
      gameMode.TEST, i_block_array);
    my_four_line_populated = new TetrisBoard(6, 10, gameMode.TEST,
      i_block_array);
    
    for (int y = HEAD_SPACE + 9; y > HEAD_SPACE + 5; y--)
    {
      for (int x = 0; x < 4; x++)
      {
        my_two_line_populated.fillSpace(x, y, true); 
      }
    }
    for (int y = HEAD_SPACE + 9; y > HEAD_SPACE + 6; y--)
    {
      for (int x = 0; x < 5; x++)
      {
        my_three_line_populated.fillSpace(x, y, true);
      }
    }
    for (int y = HEAD_SPACE + 9; y > HEAD_SPACE + 5; y--)
    {
      for (int x = 0; x < 5; x++)
      {
        my_four_line_populated.fillSpace(x, y, true);

      }
    }
    
  }

  /**
   * Tests whether the boards initialized properly.
   */
  @Test
  
  public void testInitialize()
  {
    assertEquals("Blank does not initialize properly" , "...AA.\n" +
        "...AA.\n" +
        "......\n" + 
        "......\n" +
        "......\n" +
        "......\n", my_blank.toString());
    assertEquals("Pattern does not initialize properly" , "...AA.\n" +
        "...AA.\n" +
        ".B....\n" + 
        "......\n" +
        "......\n" +
        "......\n", my_small_pattern.toString());

    assertEquals("One-line populated does not initialize properly" , "...AA.\n" +
        "...AA.\n" +
        "B.....\n" + 
        "BBBB..\n" +
        "BBBB..\n" +
        "......\n", my_one_line_populated.toString());
    
    assertEquals("two-line populated does not initialize properly" , "...AA.\n" + 
        "...AA.\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "BBBB..\n" +
        "BBBB..\n" +
        "BBBB..\n" +
        "BBBB..\n", my_two_line_populated.toString());
    assertEquals("four-line populated does not initialize properly" , "....A.\n" + 
        "....A.\n" +
        "....A.\n" +
        "....A.\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "BBBBB.\n" +
        "BBBBB.\n" +
        "BBBBB.\n", my_three_line_populated.toString());
    assertEquals("four-line populated does not initialize properly" , "....A.\n" + 
        "....A.\n" +
        "....A.\n" +
        "....A.\n" +
        "......\n" +
        "......\n" +
        "BBBBB.\n" +
        "BBBBB.\n" +
        "BBBBB.\n" +
        "BBBBB.\n", my_four_line_populated.toString());
  }
 
  /**
   * Tests whether the piece generation pattern works as expected.
   */
  @Test
  public void testPattern()
  {
    for (int i = 0; i < 9; i++)
    {
      my_small_pattern.step();
      my_small_pattern.attemptMoveX(false);
    }
    assertEquals("Pattern does not function" , "..AA..\n" +
                 "...A..\n" +
                 ".B.A..\n" + 
                 "..BB..\n" +
                 "BBB...\n" +
                 "BBB...\n", my_small_pattern.toString());
  }
  /**
   * Tests whether the attemptMoveX function works properly.
   * That is, if it moves when it is free to and does not
   * move when it would cause the active piece to overlap with the
   * boundary or other pieces.
   */
  @Test
  public void testMoveX()
  {
    my_small_pattern.step();
    for (int i = 0; i < 5; i++)
    {
      my_small_pattern.attemptMoveX(false);
    }
    
    assertEquals("Active block does not stop at static block", 
        "......\n" +
        "..AA..\n" +
        ".BAA..\n" + 
        "......\n" +
        "......\n" +
        "......\n", my_small_pattern.toString());
    
    for (int i = 0; i < 5; i++)
    {
      my_small_pattern.attemptMoveX(true);
    }
    
    assertEquals("Active block does not stop at boundary", 
        "......\n" +
        "....AA\n" +
        ".B..AA\n" + 
        "......\n" +
        "......\n" +
        "......\n", my_small_pattern.toString());
  }
  /**
   * Tests whether involuntary dropping and freezing happen correctly.
   */
  @Test
  public void testDrop()
  {
    for (int i = 0; i < 5; i++)
    {
      my_blank.step();
    }
    assertEquals("Active block does not become static on landing on boundary", 
                 "...AA.\n" +
                 "...AA.\n" +
                 "......\n" + 
                 "......\n" +
                 "...BB.\n" +
                 "...BB.\n", my_blank.toString());
    for (int i = 0; i < 3; i++)
    {
      my_blank.step();
    }
    assertEquals("Active block does not become static on landing on block", 
                 "...AA.\n" +
                 "...AA.\n" +
                 "...BB.\n" + 
                 "...BB.\n" +
                 "...BB.\n" +
                 "...BB.\n", my_blank.toString());
  }
  /**
   * Tests whether rotation works properly.
   * That is, whether it rotates when the piece is free
   */
  @Test
  public void testRotate()
  {
    for (int i = 0; i < 7; i++)
    {
      my_small_pattern.attemptMoveX(false);
      my_small_pattern.step();
    }
    my_small_pattern.attemptRotate();
    assertEquals("Active block rotation goes into other blocks", 
                 "......\n" +
                 "......\n" +
                 ".BAA..\n" + 
                 "..A...\n" +
                 "BBA...\n" +
                 "BB....\n", my_small_pattern.toString());
    my_small_pattern.attemptMoveX(true);
    my_small_pattern.attemptRotate();
    assertEquals("Active block does not rotate when it should", 
                 "......\n" +
                 "......\n" +
                 ".BA...\n" + 
                 "..AAA.\n" +
                 "BB....\n" +
                 "BB....\n", my_small_pattern.toString());
  }
  /**
   * Tests to see whether the correct board positions result from
   * filling up a row.
   */
  @Test
  public void testClear()
  {
    for (int i = 0; i < 5; i++)
    {
      my_one_line_populated.attemptMoveX(true);
      my_one_line_populated.step();
    }
    for (int i = 0; i < 9; i++)
    {
      my_two_line_populated.attemptMoveX(true);
      my_two_line_populated.step();
    }
    for (int i = 0; i < 7; i++)
    {
      my_three_line_populated.attemptMoveX(true);
      my_three_line_populated.step();
      my_four_line_populated.attemptMoveX(true);
      my_four_line_populated.step();
    }
    

    assertEquals("two-line populated does not clear properly" , 
        "...AA.\n" + 
        "...AA.\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "......\n" +
        "BBBB..\n" +
        "BBBB..\n", my_two_line_populated.toString());
    assertEquals("three-line populated does not clear properly" , 
                 "...AA.\n" + 
                 "...AA.\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 ".....B\n", my_three_line_populated.toString());
    assertEquals("four-line populated does not clear properly" , 
                 "...AA.\n" + 
                 "...AA.\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n" +
                 "......\n", my_four_line_populated.toString());
    
    assertEquals("Row-clearing does not occur properly for one line", 
                     "...AA.\n" + 
                     "...AA.\n" + 
                     "......\n" +
                                                         
                     "B.....\n" + 
                     "BBBB..\n" +
                                                         
                     "....BB\n", my_one_line_populated.toString());
  }
  /**
   * Tests whether or not large patterns (30 pieces) work.
   * This also tests whether the program fails when we finish with
   * the blocks, or if it just switches back to its normal mode (which
   * it should.)  Should produce alternating L and Z blocks before switching
   * back to normal mode (O blocks.)
   */
  @Test
  public void testLargePattern()
  {
    final Block[] b = new Block[30];
    for (int i = 0; i < b.length; i++)
    {
      if (i % 2 == 1)
      {
        b[i] = new LBlock(0, 0);
      }
      else
      {
        b[i] = new ZBlock(0, 0);
      }
    }
    final TetrisBoard big = new TetrisBoard(10, 50, gameMode.TEST, b);
    while (big.getNumDropped() < 32)
    {
      if (big.getNumDropped() < 15)
      {
        big.attemptMoveX(false);
        big.attemptMoveX(false);
        big.attemptMoveX(false);
      }
      big.step();
    }
    assertEquals("Set pattern doesn't function!",
      ".....AA...\n" +
      ".....AA...\n" +
      "..........\n" +
      "..........\n" +
      "..........\n" +
      "..........\n" +
      "..........\n" +
      "......BB..\n" +
      "......B...\n" +
      ".....BB...\n" +
      ".....BB...\n" +
      "B.....B...\n" +
      "BB....BB..\n" +
      ".B....B...\n" +
      "BB...BB...\n" +
      "B....BB...\n" +
      "B.....B...\n" +
      "B.....BB..\n" +
      "BB....B...\n" +
      ".B...BB...\n" +
      "BB...BB...\n" +
      "B.....B...\n" +
      "B.....BB..\n" +
      "B.....B...\n" +
      "BB...BB...\n" +
      ".B...BB...\n" +
      "BB....B...\n" +
      "B.....BB..\n" +
      "B.....B...\n" +
      "B....BB...\n" +
      "BB...BB...\n" +
      ".B....B...\n" +
      "BB....BB..\n" +
      "B.....B...\n" +
      "B....BB...\n" +
      "B....BB...\n" +
      "BB....B...\n" +
      ".B....BB..\n" +
      "BB....B...\n" +
      "B....BB...\n" +
      "B....BB...\n" +
      "B.....B...\n" +
      "BB....BB..\n" +
      ".B....B...\n" +
      "BB...BB...\n" +
      "B....BB...\n" +
      "B.....B...\n" +
      "B.....BB..\n" +
      "BB....B...\n" +
      ".B....B...\n", big.toString());
  }
  /**
   * Tests the clearing of non-adjacent lines.
   */
  @Test
  public void nonadjacentLineClear()
  {
    final Block [] b = new Block[1];
    b[0] = new IBlock(0, 0);
    final TetrisBoard long_block = new TetrisBoard(5, 5, gameMode.TEST, b);
    for (int i = 0; i < 4; i++)
    {
      for (int j = 0; j < 4; j++)
      {
        long_block.fillSpace(i, j + HEAD_SPACE, true);
      }
      
    }
    long_block.fillSpace(2, 2 + HEAD_SPACE, false);
    for (int i = 0; i < 3; i++)
    {
      long_block.attemptMoveX(true);
    }

    for (int i = 0; i < 5; i++)
    {
      long_block.step();
    }
   
    assertEquals("Nonadjacent lines cannot be cleared.",
      "..AA.\n" + 
      "..AA.\n" +
      "BBBB.\n" +
      "BB.BB\n" +
      "....B\n", long_block.toString());
  }
  /**
   * Tests that the random mode generates at least one of each block
   * on successive runthroughs.
   */
  @Test
  public void testRandomMode()
  {
    int num_o = 0;
    int num_i = 0;
    int num_j = 0;
    int num_l = 0;
    int num_t = 0;
    int num_s = 0;
    int num_z = 0;

    for (int i = 0; i < 200; i++)
    {
      final TetrisBoard t = new TetrisBoard(6, 6, gameMode.RANDOM);
      if (t.getCurrentBlock().equals(new OBlock(3, 0)))
      {
        num_o++;
      }
      else if (t.getCurrentBlock().equals(new SBlock(3, 0)))
      {
        num_s++;
      }
      else if (t.getCurrentBlock().equals(new ZBlock(3, 0)))
      {
        num_z++;
      }
      else if (t.getCurrentBlock().equals(new TBlock(3, 0)))
      {
        num_t++;
      }
      else if (t.getCurrentBlock().equals(new LBlock(3, 0)))
      {
        num_l++;
      }
      else if (t.getCurrentBlock().equals(new JBlock(3, 0)))
      {
        num_j++;
      }
      else if (t.getCurrentBlock().equals(new IBlock(3, 0)))
      {
        num_i++;
      }
      
    }
    
 
    assertNotSame(num_i, 0);
    assertNotSame(num_j, 0);
    assertNotSame(num_l, 0);
    assertNotSame(num_z, 0);
    assertNotSame(num_s, 0);
    assertNotSame(num_t, 0);
    assertNotSame(num_o, 0);
    
  }
  /**
   * Tests the voluntary movement in the y axis (which
   * should not freeze the block in place).
   */
  @Test
  public void testVoluntaryMoveY()
  {
    my_blank.attemptLowerBlock();
    assertEquals("AttemptLowerBlock does not function properly" , "......\n" +
        "...AA.\n" +
        "...AA.\n" + 
        "......\n" +
        "......\n" +
        "......\n", my_blank.toString());
    for (int i = 0; i < 10; i++)
    {
      my_blank.attemptLowerBlock();
    }
    assertEquals("AttemptLowerBlock freezes when it shouldn't" , "......\n" +
        "......\n" +
        "......\n" + 
        "......\n" +
        "...AA.\n" +
        "...AA.\n", my_blank.toString());
  }
  
  /**
   * Tests that headspace works properly.
   * That is, tests that blocks in normal modes (not test mode)
   * fall gradually onto the board.
   */
  @Test
  public void testRegularMode()
  {
    final TetrisBoard regular = new TetrisBoard(5, 5, gameMode.OBLOCK);
    regular.step();
    regular.step();
    assertEquals("Regular mode does not use headspace properly", 
      ".....\n" +
      ".....\n" +
      ".....\n" +
      ".....\n" +
      ".....\n", regular.toString());
    System.out.println(regular);
    regular.step();
    assertEquals("Regular mode does not use headspace properly",
      "..AA.\n" +
      ".....\n" +
      ".....\n" +
      ".....\n" +
      ".....\n" , regular.toString());
    System.out.println(regular);
    regular.step();
    assertEquals("Regular mode does not use headspace properly",
      "..AA.\n" +
      "..AA.\n" +
      ".....\n" +
      ".....\n" +
      ".....\n", regular.toString());
    System.out.println(regular);
    
  }
  
}
  




