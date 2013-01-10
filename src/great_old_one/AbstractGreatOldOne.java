package great_old_one;

import board.TetrisBoard;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This is the abstract class governing the Great Old Ones.
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
public abstract class AbstractGreatOldOne implements IGreatOldOne
{
  /**
   * The amount more each usage of the Power costs.
   */
  private static final int MAX_POWER_INCREMENTER = 100;
  /**
   * The difficulty modifier from playing this Great Old One.
   */
  private final int my_difficulty_modifier;
  /**
   * The board used by this Great Old One.
   */
  private final TetrisBoard my_board;
  /**
   * The name of this Great Old One.
   */
  private final String my_name;
  /**
   * The name of this GOO's power.
   */
  private final String my_power_name;
  /**
   * The path used to get at this GOO's image.
   */
  private final String my_image_path;
  /**
   * The color representing this GOO.
   */
  private final Color my_color;
  /**
   * The time until the GOO's power can be used again.
   */
  private int my_power_timer;
  /**
   * The maximum time between when the GOO uses its power and when it can be used again.
   */
  private int my_max_power_timer;
  /**
   * The constructor for this class.
   * @param the_name The name of the great old one.
   * @param the_power_name The power's name.
   * @param the_image_name The image path.
   * @param the_difficulty_modifier The difficulty modifier for this GOO.
   * (Larger numbers represent greater difficulty.)
   * @param the_board The TetrisBoard used.
   * @param the_color The color representing this GOO.
   */
  public AbstractGreatOldOne(final String the_name, 
                     final String the_power_name,
                     final String the_image_name,
                     final int the_difficulty_modifier, 
                     final TetrisBoard the_board,
                     final Color the_color)
  {
    super();
    my_name = the_name;
    my_difficulty_modifier = the_difficulty_modifier;
    my_board = the_board;
    my_power_name = the_power_name;
    my_image_path = the_image_name;
    my_color = the_color;
    my_power_timer = 0;
    my_max_power_timer = 0;
  }
  /**
   * Returns the difficulty modifier for this GOO.
   * @return the difficulty modifier. 
   */
  public int getDifficultyModifier()
  {
    return my_difficulty_modifier;
  }
  /**
   * Returns this GOO's name.
   * @return this GOO's name.
   */
  public String getName()
  {
    return my_name;
  }
  /**
   * Returns the name of this GOO's power.
   * @return the name of this GOO's power.
   */
  public String getPowerName()
  {
    return my_power_name;
  }
  /**
   * Returns the icon representing this GOO.
   * @return the icon representing this GOO.
   */
  public ImageIcon getIcon()
  {
    ImageIcon img_icon = new ImageIcon();
    URL url = this.getClass().getResource(my_image_path);
    
    try
    {
      img_icon =  new ImageIcon(ImageIO.read(url));
    }
    catch (final IOException e)
    {
      e.printStackTrace();
    }
    return img_icon;
  }
  
  public Color getColor()
  {
    return my_color;
  }
  public abstract void doPower();
  
  /**
   * Called by the board to check to see if the GOO's power can be used;
   * and if it can be, increments the max power timer by 100, and uses the power.
   */
  public void doAbility()
  {
    if (my_power_timer == 0)
    {
      my_max_power_timer += MAX_POWER_INCREMENTER;
      my_power_timer = my_max_power_timer;
      doPower();
      my_board.updateOldOneGUI();
    }
  }
  /**
   * Returns the current time until power is ready (in ticks).
   * @return The current time until power is ready in ticks.
   */
  public int getPowerTimer()
  {
    return my_power_timer;
  }
  /**
   * Decreases the power timer by 1 if it is above 0.
   */
  public void decrementPowerTimer()
  {
    if (my_power_timer > 0)
    {
      my_power_timer--;
    }
  }
  public TetrisBoard getBoard()
  {
    return my_board;
  }
  /**
   * Resets the character to its default timer and max timer values.
   */
  public void reset()
  {
    my_power_timer = 0;
    my_max_power_timer = 0;
    
  }
  
  

  
  
}
