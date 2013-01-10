package gui;

import board.TetrisBoard;
import gui.KeyBindings.CardinalActions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * This class is responsible for displaying the Tetris game.
 * @author Aaron Kaufman
 *@version 3 3 2012
 */
public class TetrisGUI
{
  private static final int MENU_BAR_HEIGHT = 90;
  private static final int MIN_X = 600;
  private static final int MIN_Y = 600;
  private static final int MAX_X = 1000;
  private static final int MAX_Y = 650;
  
  private static final int SPACER = 50;
  private final JFrame my_frame;
  private final TetrisBoard my_board;
  private final BackgroundImagePanel my_background_panel;
  private final TetrisPanel my_panel;
  private final DataDisplayPanel my_data_display_panel;
  private final KeyBindings my_keys;
  private final GreatOldOnePanel my_great_old_one_panel;


  public TetrisGUI()
  {
    my_background_panel = new BackgroundImagePanel();
    my_keys = new KeyBindings();
    my_board = new TetrisBoard();
    my_frame = new JFrame("CSS305 Tetris, made by Aaron Kaufman");
    my_data_display_panel = new DataDisplayPanel(my_board.getData(), my_keys);
    my_panel = new TetrisPanel(my_board.getData());
    my_great_old_one_panel = new GreatOldOnePanel(my_board.getOldOneData());
  }
  
  /**
   * Initializes all panels and starts a new game of Tetris.
   */
  public void start()
  {
    
    
    /*final JPanel dummy_panel = new JPanel();
    dummy_panel.setPreferredSize(new Dimension(1, MENU_BAR_HEIGHT));
    dummy_panel.setOpaque(false);*/
    my_board.addObserver(my_panel);
    my_board.addObserver(my_data_display_panel);
    my_board.addObserver(my_great_old_one_panel);
    
    
    my_frame.addComponentListener(new GUIListener());
    my_frame.addKeyListener(new TetrisKeyAdapter());
    my_frame.addWindowListener(new WindowCloseListener());
    
    
    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    
    final JComponent content_pane = new JPanel();
    content_pane.setLayout(new BorderLayout());
    //glass_pane.add(dummy_panel, BorderLayout.NORTH);
    content_pane.add(new TetrisMenuBar(my_board), BorderLayout.NORTH);
    content_pane.add(my_panel, BorderLayout.CENTER);  
    content_pane.add(my_data_display_panel, BorderLayout.EAST);
    content_pane.add(my_great_old_one_panel, BorderLayout.WEST);
    
    my_frame.setContentPane(content_pane);
    
    content_pane.setOpaque(false);
    content_pane.setVisible(true);
    
    
    

    my_frame.getRootPane().add(my_background_panel, BorderLayout.CENTER);
    

    //my_frame.pack();
    
    my_panel.repaint();
    content_pane.repaint();
    
    my_frame.setMinimumSize(new Dimension
    (MIN_X,
      MIN_Y));
    
    my_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    my_frame.setLocationRelativeTo(null);
    my_frame.setVisible(true);
    my_board.startTimer();
    
  }
  /**
   * Starts the GUI.
   * @param the_args The arguments given in the command line.
   */
  public static void main(final String [] the_args)
  {
    final TetrisGUI gui = new TetrisGUI();
    gui.start();
  }
  
/**
 * Starts a new game of Tetris.
 */
  /*
  public void startNewGame()
  {
    my_board.pause();
    
    my_board = new TetrisBoard();
    
    my_frame.removeAll();
    start();
    
  }
  */
  /**
   * Handles the controls for the Tetris game.
   * @author Aaron Kaufman
   *
   */
  public class TetrisKeyAdapter extends KeyAdapter
  {
    public void keyTyped(final KeyEvent the_event) 
    {
      if (!my_board.isPaused())
      {
        if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.LEFT)) 
        {
          my_board.attemptMoveX(false);
        }
        else if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.RIGHT)) 
        {
          my_board.attemptMoveX(true);
        }
        else if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.ROTATE)) 
        {
          my_board.attemptRotate();
        }
        else if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.DOWN)) 
        {
          my_board.attemptLowerBlock();
        }
        else if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.DROP))
        {
          my_board.instantDrop();
        }
        else if (the_event.getKeyChar() == my_keys.getChar(CardinalActions.POWER))
        {
          my_board.doPower();
        }
      }
    }
  }
  /**
   * Listener which handles resizing of GUI elements when the size of the window changes.
   * @author Aaron
   *
   */
  public class GUIListener extends ComponentAdapter
  {
    /**
     * {@inheritDoc}
     */
    public void componentResized(final ComponentEvent the_e)
    {
      my_panel.resizeComponents(my_frame.getWidth() - my_data_display_panel.getDefaultWidth() -
                                SPACER, my_frame.getHeight() - MENU_BAR_HEIGHT - SPACER);
      if (my_panel.getSize().getWidth() > my_background_panel.getSize().getWidth())
      {
        my_panel.setSize(new Dimension(my_background_panel.getWidth(), 
                                       my_panel.getHeight()));
        System.out.println("Changing width");
      }
      if (my_panel.getSize().getHeight() > my_background_panel.getSize().getHeight())
      {
        my_panel.setSize(new Dimension(my_panel.getWidth(), 
                                       my_background_panel.getHeight()));
        System.out.println("Changing height");
      }
      
      //if the changed height is greater than the max height, set height to max.
      if (my_frame.getWidth() > MAX_X)
      {
        my_frame.setSize(new Dimension(MAX_X, my_frame.getHeight()));
      }
      if (my_frame.getHeight() > MAX_Y)
      {
        my_frame.setSize(new Dimension(my_frame.getWidth(), MAX_Y));
      }
    }
    
    
  }
  
  /**
   * Class which handles stopping the timer when the window is closed.
   * @author Aaron Kaufman
   *
   */
  public class WindowCloseListener extends WindowAdapter
  {


    @Override
    public void windowClosed(final WindowEvent the_arg)
    {
      my_board.getTimerObject().stop();
      
    }
  }
  /**
   * The panel used to make the background image for the Tetris program.
   * This image is from NASA, and is a picture of the Trifid Nebula.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  @SuppressWarnings("serial")
  public class BackgroundImagePanel extends JPanel
  {
    
    private static final String FILE_PATH = "trifid_nebula_nasa1.jpg";
    private BufferedImage my_image;
    private final int my_width;
    private final int my_height;
    public BackgroundImagePanel()
    {
      super();
      URL url = this.getClass().getResource(FILE_PATH);
      
      try
      {
        my_image = ImageIO.read(url);
      }
      catch (final IOException e)
      {
        e.printStackTrace();
      }
      my_width = my_image.getWidth();
      my_height = my_image.getHeight();
      setSize(new Dimension(my_width * 2 , my_height * 2));
    }
    @Override
    public void paintComponent(final Graphics the_graphics)
    {
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      
      g2d.drawImage(my_image, 0, 0, null);
     /*
      g2d.drawImage(my_image, my_width, 0, null);
      g2d.drawImage(my_image, 0, my_height, null);
      g2d.drawImage(my_image, my_width, my_height, null);
      */
     
    }
    
    public int getWidth()
    {
      return my_width;
    }
    
    public int getHeight()
    {
      return my_height;
    }
  }
  
}
