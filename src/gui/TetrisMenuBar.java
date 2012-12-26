package gui;

import board.TetrisBoard;

import great_old_one.Cthulhu;
import great_old_one.DefaultCharacter;
import great_old_one.KingInYellow;
import great_old_one.YogSothoth;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


/**
 * The menu bar for the Tetris program.
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
@SuppressWarnings("serial")
public class TetrisMenuBar extends JMenuBar
{
  /**
   * The dimension of the help dialog box.
   */
  private static final int  HELP_BOX_DIMENSION = 500;
  /**
   * The help menu item.
   */
  private final JMenuItem my_help;
  /**
   * The pause menu item.
   */
  private final JMenuItem my_pause;
  /**
   * The file menu.
   */
  private final  JMenu my_file;
  /**
   * The new game menu item.
   */
  private final JMenuItem my_new_game;
  /**
   * The current board in use.
   */
  private final TetrisBoard my_board;
  /**
   * The "end game" menu item.
   */
  private final JMenuItem my_end_game;
  /**
   * The menu governing Great Old One selection.
   */
  private final GreatOldOneMenu my_old_ones;
  /**
   * Constructor for this class.
   * @param the_board The Tetris Board in use.
   */
  public TetrisMenuBar(final TetrisBoard the_board)
  {
    super();
    my_help = new JMenuItem("Help");
    my_board = the_board;
    my_file = new JMenu("File");
    my_pause = new JMenuItem("Pause/Unpause");
    my_new_game = new JMenuItem("New game");
    my_end_game = new JMenuItem("End game");
    my_old_ones = new GreatOldOneMenu(the_board);
    
    setMenuOptions();
  }
  /**
   * Sets the options for the various menus.
   */
  private void setMenuOptions()
  {
    my_old_ones.createOldOneButton(new DefaultCharacter(my_board));
    my_old_ones.createOldOneButton(new Cthulhu(my_board));
    my_old_ones.createOldOneButton(new KingInYellow(my_board));
    my_old_ones.createOldOneButton(new YogSothoth(my_board));
    
    add(my_old_ones);
    
    my_file.add(my_help);
    my_file.add(my_pause);
    my_file.add(my_new_game);
    my_file.add(my_end_game);
    
    my_pause.addActionListener(new PauseListener());
    my_new_game.addActionListener(new NewGameListener());
    my_end_game.addActionListener(new EndGameListener());
    my_help.addActionListener(new HelpListener());
    add(my_file);
  }
  /**
   * ActionListener for the pause button.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  public class PauseListener implements ActionListener
  {
    
    @Override
    public void actionPerformed(final ActionEvent the_arg)
    {
      if (my_board.isPaused())
      {
        my_board.startTimer(); 
      }
      else
      {
        my_board.pause();
      }
    }
    
  }
  /**
   * Listener for the "New Game" button.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  public class NewGameListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent the_arg)
    {
      my_board.startNewGame();
    }
  }
  /**
   * Listener for the "end game" button.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  public class EndGameListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent the_arg0)
    {
      my_board.endGame();
      
    }
    
  }
  /**
   * ActionListener for the help menu button.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  public class HelpListener implements ActionListener
  {

    @Override
    public void actionPerformed(final ActionEvent the_arg0)
    {
      final JTextArea area = new JTextArea();
      area.setLineWrap(true);
      area.setWrapStyleWord(true);
      area.setSize(new Dimension(HELP_BOX_DIMENSION, HELP_BOX_DIMENSION));
      area.append("This is a classy Tetris program " +
                                    "themed on the Lovecraft mythos, created by" +
                                    " Aaron Kaufman.\n\n " +
                                    "HOW TO PLAY:\nThis game plays just " +
                                    "like ordinary Tetris." +
                                     "  Except!  You can choose to play as " +
                                     "a Great Old One, who" +
                                     " gets a specific power that it can execute " +
                                     "every so often." +
                                     "  The amount of time it takes to " +
                                     "regenerate this power increases " +
                                     "based on how often you use it, " +
                                     "however, so be careful!  " +
                                     "Note also the difficulty modifier to the lower left.  " +
                                     "On the more powerful characters, " +
                                     "this makes the blocks fall " +
                                     "significantly faster from the get-go.\n\n" +
                                     "SWITCHING CHARACTERS:\n Use the Great Old Ones " +
                                     "menu to select a new character, then start a new" +
                                     " game!  (this will not take effect until you start a " +
                                     "new game.)\n\nCONTROLS:  See right hand of the screen." +
                                     "\n\nCREDITS:  Background image by NASA. " +
                                     " King in Yellow art done by " +
                                     "Gutterball (Deviantart.com).  " +
                                     "Cthulhu art done by reau (Deviantart.com).  " +
                                     "Yog'Sothoth art done by Patrick McEvoy.  " +
                                     "Herbert West art done by LianneC. (Deviantart.com)");
      JOptionPane.showMessageDialog(null, area);
                                    
      
    }
    
  }
  
  
}
