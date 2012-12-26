package gui;

import board.TetrisBoard.TetrisData;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the panel responsible for drawing the Tetris board to the GUI.
 * @author Aaron Kaufman
 *@version 3 3 2012
 */
@SuppressWarnings("serial")
public class TetrisPanel extends JPanel implements Observer
{
  /**
   * The amount of space between the top of the panel and the board.
   */
  private static final int STARTING_Y = 20;
  /**
   * The stroke width for drawing the board.
   */
  private static final int STROKE_WIDTH = 5;
  /**
   * The default block size in pixels.
   */
  private static final int DEFAULT_BLOCK_SIZE = 20;
  /**
   * The head space required in a game of Tetris (that is, 
   * the space of the board which should not be shown to the user).
   */
  private static final int HEAD_SPACE = 4;
  /**
   * The data sent over by the TetrisBoard used to construct the visuals.
   */
  
  private char[][] my_board_chars;
  /**
   * The width of the board in blocks.
   */
  private final int my_width_in_blocks;
  /**
   * The height of the board in blocks.
   */
  private final int my_height_in_blocks;
  /**
   * The height to width ratio of the board.
   */

  private final double my_height_to_width_ratio;
  /**
   * The current block size in pixels.
   */
  private int my_current_block_size;
  
  /**
   * The height of the board in pixels.
   */
  private int my_height_in_pixels;
  /**
   * The width of the board in pixels.
   */
  private int my_width_in_pixels;
  
  /**
   * Whether the game over screen has been shown.
   */
  private boolean my_game_over_panel_performed;
  
  /**
   * 
   * Constructor for the TetrisPanel.
   * @param the_data The board data sent by the current TetrisBoard.
   */
  public TetrisPanel(final TetrisData the_data)
  {
    super();
    my_game_over_panel_performed = false;
    my_current_block_size = DEFAULT_BLOCK_SIZE;
    my_board_chars = the_data.getBoard();
    my_width_in_blocks = the_data.getX();
    my_height_in_blocks = the_data.getY();
    my_height_to_width_ratio = (double) my_width_in_blocks / my_height_in_blocks;
    my_height_in_pixels = DEFAULT_BLOCK_SIZE * my_height_in_blocks + 2 * DEFAULT_BLOCK_SIZE;
    my_width_in_pixels = DEFAULT_BLOCK_SIZE * my_width_in_blocks + 2 * DEFAULT_BLOCK_SIZE;
    
    initialize();
    
  }
  /**
   * Sets the background color, the preferred size, and the opacity.
   */
  private void initialize()
  {
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(my_width_in_pixels, my_height_in_pixels));
    setOpaque(false);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void paintComponent(final Graphics the_graphics)
  {
    super.paintComponent(the_graphics);
    final Graphics2D g2d = (Graphics2D) the_graphics;
 
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); 
    
    final char active_block = 'A';
    final char inactive_block = 'B';
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                         RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.setColor(Color.BLACK);
    for (int y = HEAD_SPACE; y < my_height_in_blocks + HEAD_SPACE; y++)
    {
      for (int x = 0; x < my_width_in_blocks; x++)
      {
        final char current = my_board_chars[x][y];
        if (current == active_block)
        {

          g2d.setColor(Color.RED);
          drawRectFromPoint(x, y, g2d);
        }
        else if (current == inactive_block)
        {
          g2d.setColor(Color.GREEN);
          drawRectFromPoint(x, y, g2d);
        }
        /*else if (current == space)
        {
          g2d.setColor(Color.BLACK);
          drawRectFromPoint(x, y, g2d);
        }
        */
      }
    }
    
    paintBorder(g2d);
  }
  /**
   * Paints a border around the TetrisPanel.
   * @param the_g2d The Graphics2D object used by the object.
   */
  private void paintBorder(final Graphics2D the_g2d)
  {
    
    the_g2d.setColor(Color.BLACK);
    the_g2d.setStroke(new BasicStroke(STROKE_WIDTH));
    the_g2d.drawRect(STROKE_WIDTH, STARTING_Y,
                     my_current_block_size * my_width_in_blocks,
                     my_current_block_size * my_height_in_blocks);
  }
 
  /**
   * Draws a square of the current block size from a single point on the JPanel.
   * The point represents the upper left hand corner of the block.
   * @param the_x The x coordinate of the point.
   * @param the_y The y coordinate of the point.
   * @param the_g2d The graphics2D object being used by this object.
   */
  private void drawRectFromPoint(final int the_x, final int the_y, final Graphics2D the_g2d)
  {
    final int starting_x = my_current_block_size * the_x + STROKE_WIDTH;
    final int starting_y = my_current_block_size * (the_y - HEAD_SPACE) + STARTING_Y;
    
    the_g2d.fillRect(starting_x,
                     starting_y, 
                     my_current_block_size, 
                     my_current_block_size);
    the_g2d.setColor(Color.BLACK);
    the_g2d.drawRect(starting_x,
                     starting_y, 
                     my_current_block_size, 
                     my_current_block_size);
  }

/**
 * {@inheritDoc}
 */
  @Override
  public void update(final Observable the_board, final Object the_data)
  {
    if (the_data instanceof TetrisData)
    {
      final TetrisData data = (TetrisData) the_data;
      my_board_chars = data.getBoard();
      repaint();
      if (data.isGameEnded())
      {
        stopGame();
      }
      else
      {
        my_game_over_panel_performed = false;
      }
    }
  }
  /**
   * Class which automatically resizes the TetrisPanel given a set of frame
   * dimensions.
   * @param the_panel_width The width that the TetrisPanel is allowed to use.
   * @param the_panel_height The height that the TetrisPanel is allowed to use.
   */
  public void resizeComponents(final int the_panel_width, final int the_panel_height)
  {
    //If this is true, then our height is our determining factor and is used to get the width.
    if ((double) ((double) the_panel_width / (double) the_panel_height) >
        my_height_to_width_ratio)
    {
      my_height_in_pixels = the_panel_height;
      my_width_in_pixels = (int) (the_panel_height * my_height_to_width_ratio);
      my_current_block_size = the_panel_height / (my_height_in_blocks + 1);
    }
    //Otherwise, our width is our determining factor and is used to get the height.
    else 
    {
/*      System.out.println("Width is determining factor.");
      System.out.println("Width:  " + the_panel_width);
      System.out.println("Height:  " + the_panel_height);
      System.out.println("Default ratio:  " + my_height_to_width_ratio);
      System.out.println("Current ratio:  " + 
          (double) ((double) the_panel_width / (double) the_panel_height));
*/
      my_width_in_pixels = the_panel_width;
      my_height_in_pixels = (int) (the_panel_width / my_height_to_width_ratio); 
      my_current_block_size = the_panel_width / (my_width_in_blocks + 1);
    }
  }
  /**
   * Creates a dialogue box announcing the end of the game.
   */
  public void stopGame()
  {
    if (!my_game_over_panel_performed)
    {
      JOptionPane.showMessageDialog(this,
        "Alas, the stars remain beyond your grasp!");
      my_game_over_panel_performed = true;
    }
  }
  /*
  public class BackgroundPanel extends JPanel
  {
    
    public BackgroundPanel()
    {
      super();
      setBackground(Color.YELLOW);
      setOpaque(false);
      setPreferredSize(new Dimension(my_width_in_pixels, my_height_in_pixels));
    }
  }

  public Component createBackgroundPanel()
  {
    return new BackgroundPanel();
  }*/
}


