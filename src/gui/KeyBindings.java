package gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Class which handles the assignment of keys to commands.
 * @author Aaron Kaufman
 * @version 3 3 2012
 */
public class KeyBindings
{
  /**
   * Map which correlates commands to strings.
   */
  private final Map<CardinalActions, String> my_strings;
  /**
   * Map which correlates commands to keyboard characters.
   */
  private final Map<CardinalActions, Character> my_chars;
  
  /**
   * Enum which defines all possible actions for the game of Tetris.
   * @author Aaron Kaufman
   *
   */
  public enum CardinalActions
  {
    DOWN,
    RIGHT,
    LEFT,
    ROTATE,
    DROP,
    POWER;
  }
    
    
  
  public KeyBindings()
  {
    super();
    my_chars = new HashMap<CardinalActions, Character>();
    my_strings = new HashMap<CardinalActions, String>();
    setDefaultKeys();
  }
  
  public final void setDefaultKeys()
  {
    my_chars.put(CardinalActions.DOWN, 's');
    my_chars.put(CardinalActions.RIGHT, 'd');
    my_chars.put(CardinalActions.LEFT, 'a');
    my_chars.put(CardinalActions.ROTATE, 'w');
    my_chars.put(CardinalActions.DROP, 'g');
    my_chars.put(CardinalActions.POWER, 'f');
    
    my_strings.put(CardinalActions.DOWN, "Down");
    my_strings.put(CardinalActions.LEFT, "Left");
    my_strings.put(CardinalActions.ROTATE, "Rotate");
    my_strings.put(CardinalActions.DROP, "Drop");
    my_strings.put(CardinalActions.RIGHT, "Right");
    my_strings.put(CardinalActions.POWER, "Power");
  }
  /**
   * rebinds a command to a certain character.
   * @param the_action The action to be bound.
   * @param the_char The char to bind it to.
   */
  public void rebind(final CardinalActions the_action, final char the_char)
  {
    my_chars.put(the_action, the_char);
  }
  /**
   * Gets the string corresponding to a certain action.
   * @param the_action The action.
   * @return The string corresponding to that action.
   */
  public String getString(final CardinalActions the_action)
  {
    return my_strings.get(the_action);
  }
  /**
   * Gets the character corresponding to a certain action.
   * @param the_action The action.
   * @return The character corresponding to that action.
   */
  public char getChar(final CardinalActions the_action)
  {
    return my_chars.get(the_action);
  }
  /**
   * Gets a set of all CardinalActions.
   * @return The set of all CardinalActions.
   */
  public Set<CardinalActions> getKeySet()
  {
    return my_chars.keySet();
  }
  
}
