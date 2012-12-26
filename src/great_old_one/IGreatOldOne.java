package great_old_one;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import board.TetrisBoard;
/**
 * Interface describing the Great Old One class of objects, which defines
 * a Lovecraftian entity and an ability that the entity has to influence
 * the current tetris board.
 * @author Aaron Kaufman
 *
 */
public interface IGreatOldOne
{
  /**
   * Returns the difficulty modifier for this GOO; that is,
   * the amount by which the tetris blocks will speed up.
   * @return
   */
  public int getDifficultyModifier();
  /**
   * Returns this GOO's name.
   * @return this GOO's name.
   */
  public String getName();
  /**
   * Returns the name of this GOO's power.
   * @return the name of this GOO's power.
   */
  public String getPowerName();
  /**
   * Returns the icon representing this GOO.
   * @return the icon representing this GOO.
   */
  public ImageIcon getIcon();
  /**
   * Returns the color representing this GOO.
   * @return the color representing this GOO.
   */
  public Color getColor();
  /**
   * Changes the board in some way defined by the subclass.
   */
  public abstract void doPower();
  
  /**
   * Called by the board to check to see if the GOO's power can be used;
   * and if it can be, increments the max power timer by 100, and uses the power.
   */
  public void doAbility();
  /**
   * Returns the current time until power is ready (in ticks).
   * @return The current time until power is ready in ticks.
   */
  public int getPowerTimer();
  /**
   * Decreases the power timer by 1 if it is above 0.
   */
  public void decrementPowerTimer();
  /**
   * Returns the tetris board currently used by this character.
   * @return The tetris board currently used.
   */
  public TetrisBoard getBoard();
  /**
   * Resets the character to its default timer and max timer values.
   */
  public void reset();
  
}
