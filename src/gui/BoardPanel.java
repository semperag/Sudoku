package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BoardPanel extends JPanel
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private List<ChangeableBlock> blocks;
  private boolean playerHasLost;
  
  public BoardPanel(String text)
  {
    blocks = new ArrayList<>();
    playerHasLost = false;
    setLayout(new GridLayout(9,9));
    
    addComponents(text);
    setBorders();
  }

  private void addComponents(String text)
  {
    for(int row = 0; row < 9; row++)
    {
      for(int col = 0; col < 9; col++)
      {
        String number = String.valueOf(text.charAt((row * 9) + col));
        ChangeableBlock block;
        
        if (number.equals("0"))
          block = new ChangeableBlock(number, true);
        else
          block = new ChangeableBlock(number, false);
        
        blocks.add(block);
        add(block);
      }
    }
    setBorders();
  }

  public void setBorders()
  {
    for(int row = 0; row < 9; row++)
    {
      for(int col = 0; col < 9; col++)
      {
        ChangeableBlock block = getBlock(row, col);
        
        if(col % 3 == 2 && row % 3 == 2 && col < 8 && row < 8)
        {
          block.setBorder(
              BorderFactory.createMatteBorder(1, 1, 4, 4, Color.GRAY));
        }
        else if(row % 3 == 2 && row < 8)
        {
          block.setBorder(
              BorderFactory.createMatteBorder(1, 1, 4, 1, Color.GRAY));
        }
        else if(col % 3 == 2 && col < 8)
        {
          block.setBorder(
              BorderFactory.createMatteBorder(1, 1, 1, 4, Color.GRAY));
        }
        else
        {
          block.setBorder(
              BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        }
      }
    }
  }
  
  public ChangeableBlock getBlock(int row, int col)
  {
    return blocks.get((row * 9) + col);
  }
  
  public boolean didPlayerLose()
  {
    return playerHasLost;  
  }
  
  public void reset()
  {
    Iterator<ChangeableBlock> iter = blocks.iterator();
    
    while(iter.hasNext())
      iter.next().reset();
    
    setBorders();
    playerHasLost = false;
  }
  
  public void lostGame()
  {
    Iterator<ChangeableBlock> iter = blocks.iterator();
    
    while(iter.hasNext())
      iter.next().setEditable(false);
    
    playerHasLost = true;
  }
}
