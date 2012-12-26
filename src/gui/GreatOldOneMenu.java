package gui;

import board.TetrisBoard;

import great_old_one.AbstractGreatOldOne;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * This class handles the menu allowing the user to
 * switch between Great Old Ones assigned to the current TetrisBoard.
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
@SuppressWarnings("serial")
public class GreatOldOneMenu extends JMenu
{
  /**
   * The tetrisboard being used.
   */
  private final TetrisBoard my_board;
  
  /**
   * Constructor for this class.
   * @param the_board the current board in use.
   */
  public GreatOldOneMenu(final TetrisBoard the_board)
  {
    super("Great Old Ones");
    my_board = the_board;
  }
  
  /**'
   * Creates a Great Old One button.
   * @param the_great_old_one The great old one to be assigned.
   */
  public void createOldOneButton(final AbstractGreatOldOne the_great_old_one)
  {
    final JMenuItem item = new JMenuItem(the_great_old_one.getName());
    add(item);
    item.addActionListener(new GreatOldOneListener(the_great_old_one));
  }
  /**
   * This is an action listener for the menu items in this class.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  public class GreatOldOneListener implements ActionListener
  {
    /**
     * The great old one to be added as a button.
     */
    private final AbstractGreatOldOne my_great_old_one;
    /**
     * Constructor for this class.
     * @param the_great_old_one The great old one to be added as a button.
     */
    public GreatOldOneListener(final AbstractGreatOldOne the_great_old_one)
    {
      my_great_old_one = the_great_old_one;
    }
    @Override
    public void actionPerformed(final ActionEvent the_arg0)
    {
      
      my_board.changeCharacter(my_great_old_one);
    }
    
  }
}
