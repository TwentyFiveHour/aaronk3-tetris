package gui;

import board.TetrisBoard.TetrisData;

import gui.KeyBindings.CardinalActions;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


/**
 * Displays all information relating to the current game of
 *  tetris that is NOT the current board state.
 * @author Aaron Kaufman
 *@version 3 3 2012
 */
@SuppressWarnings("serial")
public class DataDisplayPanel extends JPanel implements Observer
{
  private static final int DEFAULT_WIDTH = 150;
  private static final int DEFAULT_HEIGHT = 500;
  private static final int DEFAULT_FONT_SIZE = 24;
  private static final int SPACER = 5;
  private int my_total_height;
  private final Font my_font;
  private TetrisData my_data;
  /**
   * The panel responsible for painting the next piece on the board.
   */
  private final NextPiecePainter my_piece_painter;
  /**
   * The panel responsible for drawing "Next Piece: ".
   */
  private final JPanel my_string_panel;
  /**
   * The object housing all key bindings for this class.
   */
  private final KeyBindings my_keys;
  /**
   * The panel which paints the key bindings to the data display panel.
   */
  private final KeysPainter my_keys_painter;
  /**
   * The panel which draws the score on the board.
   */
  private final ScoreDifficultyPanel my_score_panel;
  
  public DataDisplayPanel(final TetrisData the_data, final KeyBindings the_keys)
  {
    super();
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    
    my_total_height = 0;
    my_data = the_data;
    my_keys = the_keys;
    my_font = new Font("Helvetica", Font.PLAIN,  DEFAULT_FONT_SIZE);
    
   
    my_score_panel = new ScoreDifficultyPanel(); 
    my_piece_painter = new NextPiecePainter();
    my_string_panel = new StringPainter();
    my_keys_painter = new KeysPainter();
     
    initializePanels();
  }
  public int getDefaultWidth()
  {
    return DEFAULT_WIDTH;
  }
  
  /**
   * Adds each child panel to the parent panel, and 
   * sets their fonts and backgrounds.
   */
  private void initializePanels()
  {
    

    setOpaque(false);   
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    
    final List<JPanel> panels = new ArrayList<JPanel>();
    
    panels.add(my_string_panel);
    panels.add(my_piece_painter);
    panels.add(my_keys_painter);
    panels.add(my_score_panel);
    
    for (JPanel p : panels)
    { 
      add(p);
      p.setFont(my_font);
      p.setOpaque(false);
      p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    
  }
  @Override
  public void update(final Observable the_board, final Object the_data)
  {
    if (the_data instanceof TetrisData)
    {
      my_data = (TetrisData) the_data;
      my_piece_painter.my_next = my_data.getNextBlock().getBlockArray();
      my_score_panel.my_score = my_data.getScore();
      my_score_panel.my_difficulty = my_data.getDifficulty();
      repaint();
    }
    
  }
  /**
   * Returns the total default height of the Data Display Panel.
   * @return  the total default height of the Data Display Panel.
   */
  public int getTotalHeight()
  {
    return my_total_height;
  }
  /**
   * Paints the current keybindings to the keyboard.
   * @author Aaron Kaufman
   *
   */
  public class KeysPainter extends JPanel
  {
    private static final int NUM_COMMANDS = 5;
    public KeysPainter()
    {
      super();
      
      final int height = DEFAULT_FONT_SIZE *  NUM_COMMANDS + SPACER;
      setPreferredSize(new Dimension(DEFAULT_WIDTH, height));
      my_total_height += height;
    }
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics the_graphics)
    {
      
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(Color.WHITE);
      

      int y_axis_val = 0;
      
      for (final CardinalActions action : my_keys.getKeySet())
      {
        final StringBuilder s = new StringBuilder();
        s.append(my_keys.getString(action));
        s.append(":  ");
        s.append(my_keys.getChar(action));
        y_axis_val += DEFAULT_FONT_SIZE;
        g2d.drawString(s.toString(), 0, y_axis_val);
      }
    }
  }
  /**
   * Paints "Next Piece:" to the parent panel.
   * @author Aaron Kaufman
   *
   */
  public class StringPainter extends JPanel
  {
    /**
     * Constructor for this class.
     */
    public StringPainter()
    {
      super();
      setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_FONT_SIZE));
      my_total_height += DEFAULT_FONT_SIZE;
    }
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics the_graphics)
    {
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(Color.WHITE);
      g2d.drawString("Next Piece:  ", 0, 2 * DEFAULT_FONT_SIZE);
    }
    
  }
  /**
   * Draws the next piece on the parent panel.
   * @author Aaron Kaufman
   *
   */
  public class NextPiecePainter extends JPanel
  {
    private static final int DEFAULT_BLOCK_SIZE = 20;
    private static final int MAX_BLOCK_DIMENSION = 4;
    /**
     * The next block given as a 2D boolean array.
     */
    private boolean[][] my_next;
    /**
     * The size of the next block's maximum dimension.
     */
    private int my_block_size;
    /**
     * Constructor for this class.
     */
    public NextPiecePainter()
    {
      super();
      my_next = my_data.getNextBlock().getBlockArray();
      setPreferredSize(new Dimension(DEFAULT_WIDTH, 
                                     DEFAULT_BLOCK_SIZE * MAX_BLOCK_DIMENSION + SPACER));
      my_total_height += DEFAULT_BLOCK_SIZE * MAX_BLOCK_DIMENSION + SPACER;
    }
    
    /**
     * {@inheritDoc}
     */
    public void paintComponent(final Graphics the_graphics)
    {
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
      my_block_size = my_data.getNextBlock().calculateBlockSize();
      for (int y = 0; y < my_block_size; y++)
      {
        for (int x = 0; x < my_block_size; x++)
        {
          if (my_next[y][x])
          {

            g2d.setColor(Color.RED);
            drawRectFromPoint(x, y, g2d);
          }
        }
      }
    }
    /**
     * Draws a square of the Next Piece given a certain point.
     * @param the_x The x axis value of the point.
     * @param the_y The y axis value of the point.
     * @param the_g2d The graphics2D object used in the drawing.
     */
    private void drawRectFromPoint(final int the_x, final int the_y, final Graphics2D the_g2d)
    {
      the_g2d.setColor(Color.RED);
      
      final int block_size = DEFAULT_BLOCK_SIZE * MAX_BLOCK_DIMENSION / my_block_size;
      int starting_x = block_size * the_x;
      int starting_y = block_size * the_y;
      starting_y += SPACER / 2;
      starting_x += SPACER / 2;
      the_g2d.fillRect(starting_x,
                       starting_y, 
                       block_size, 
                       block_size);
      the_g2d.setColor(Color.BLACK);
      the_g2d.drawRect(starting_x, starting_y, block_size, block_size);
    }
    
    
  }
  /**
   * The panel responsible for drawing the score to the board.
   * @author Aaron Kaufman
   *
   */
  public class ScoreDifficultyPanel extends JPanel
  {
    /**
     * The current score.
     */
    private int my_score;
    /**
     * the current difficulty.
     */
    private int my_difficulty;
   /**
    * Constructor for this class.
    */
    public ScoreDifficultyPanel()
    {
      super();
      my_total_height += (DEFAULT_FONT_SIZE + SPACER) * 6;
      my_score = my_data.getScore();
      my_difficulty = my_data.getDifficulty();
      setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_FONT_SIZE * 6));
    }
   
    @Override
    public void paintComponent(final Graphics the_graphics)
    {
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
      int current_space = DEFAULT_FONT_SIZE;
      g2d.setColor(Color.WHITE);
      g2d.drawString("Score: " + my_score,
                     0,
                     current_space);
      
      current_space += DEFAULT_FONT_SIZE;
      g2d.drawString("Difficulty: " + my_difficulty,
                     0,
                     current_space);
    
      current_space += DEFAULT_FONT_SIZE;
      g2d.drawString("Your rating:", 0, current_space);
      current_space += DEFAULT_FONT_SIZE;
      g2d.drawString(getRating(), 0, current_space);
    }
    /**
     * Gives a "rating" to the player based on difficulty level.
     * @return The rating.
     */
    private String getRating()
    {
      
      String result;
      if (my_difficulty < 2)
      {
        result = "Victim";
      }
      else if (my_difficulty < 3)
      {
        result = "Cultist";
      }
      else if (my_difficulty < 5)
      {
        result = "Unknowable";
      }
      else
      {
        result = "Abomination";
      }
      return result;
    }
    
  }
  

}
