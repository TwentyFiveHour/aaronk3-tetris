package gui;


import board.TetrisBoard.GreatOldOneData;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The panel displaying the information for the Great Old One currently
 * being played.
 * @author Aaron Kaufman
 *@version 3 9 2012
 */
@SuppressWarnings("serial")
public class GreatOldOnePanel extends JPanel implements Observer
{

  private static final int FONT_SIZE = 20;
  private static final int ICON_SIZE = 100;
  private static final int PREFERRED_WIDTH = 200;
  private static final int PREFERRED_HEIGHT = 400;
  private static final int LEFT_JUSTIFY = 10;
  private final JPanel my_portrait_panel;
  /**
   * The panel describing the Great Old One's power and difficulty modifier.
   */
  private final DescriptionPanel my_description_panel;
  /**
   * The list of sub-panels making up this panel.
   */
  private final List<JPanel> my_panel_list;
  private final Font my_font;
  private GreatOldOneData my_great_old_one_data;
  /**
   * Panel giving all Great Old One data for this game.
   * @param the_data The data given to the class.
   */
  public GreatOldOnePanel(final GreatOldOneData the_data)
  {
    super();
    my_font = new Font("Helvetica", Font.BOLD, FONT_SIZE);
    
    my_great_old_one_data = the_data;
    my_portrait_panel = new PortraitPanel();
    my_description_panel = new DescriptionPanel();
    my_panel_list = new ArrayList<JPanel>();
    
    initializePanel();
    
  }
  /**
   * Adds the different panels, sets backgrounds and opacities, and sets the preferred
   * size for the GreatOldOne panel overall.
   */
  private void initializePanel()
  {
    setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    
    
    my_panel_list.add(my_portrait_panel);
    
    for (JPanel p : my_panel_list)
    {
      add(p);
      p.setBackground(Color.BLACK);
    }
    add(my_description_panel);
    setOpaque(false);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final Observable the_board, final Object the_data)
  {
    if (the_data instanceof GreatOldOneData)
    {
      my_great_old_one_data = (GreatOldOneData) the_data;
      my_portrait_panel.repaint();
      my_description_panel.initialize();
    }
  }
  /**
   * The class showing the portrait of the Great Old One.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  private class PortraitPanel extends JPanel
  {
    private static final int HEIGHT = 200;
    
    public PortraitPanel()
    {
      super();
      setPreferredSize(new Dimension(PREFERRED_WIDTH, HEIGHT));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(final Graphics the_graphics)
    {
      super.paintComponent(the_graphics);
      final Graphics2D g2d = (Graphics2D) the_graphics;
      int current_line = FONT_SIZE;
      g2d.setColor(my_great_old_one_data.getColor());
      g2d.setFont(my_font);
      g2d.drawString(my_great_old_one_data.getName(), 
                     LEFT_JUSTIFY,
                     current_line);
      current_line += 2 * FONT_SIZE;
      
      final Image img = my_great_old_one_data.getIconData().getImage();
      g2d.drawImage(img, 
                    PREFERRED_WIDTH / 2 - ICON_SIZE / 2, 
                    current_line, 
                    ICON_SIZE,
                   ICON_SIZE, 
                   this); 
      current_line += ICON_SIZE + FONT_SIZE;
      g2d.setFont(new Font("Helvetica", Font.PLAIN, (int)(FONT_SIZE * .75)));
      g2d.drawString("Ticks until next power usage:", 0, current_line);
      current_line += FONT_SIZE;
      g2d.drawString(my_great_old_one_data.getTimer() + " ticks",
                     LEFT_JUSTIFY,
                     current_line);
      
      
    }
  }
  /**
   * Panel responsible for describing the GOO's power.
   * @author Aaron Kaufman
   *@version 3 9 2012
   */
  private class DescriptionPanel extends JTextArea
  {
    private static final int HEIGHT = 300;
    public DescriptionPanel()
    {
      super();
      initialize();
    }
    

    private void initialize()
    {
      setText("Power:\n");
      append(my_great_old_one_data.getPowerName());
      append("\n\nDifficulty Modifier:  +" + my_great_old_one_data.getDifficultyModifier());

      setFont(my_font);
      setBackground(Color.BLACK);
      setEditable(false);
      setOpaque(true);
      setWrapStyleWord(true);
      setLineWrap(true);
      setEnabled(false);

      this.setForeground(my_great_old_one_data.getColor());
      setPreferredSize(new Dimension(PREFERRED_WIDTH, HEIGHT));
    }
    
    
  }

  

}
