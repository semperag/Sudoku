package gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

public class ChangeableBlock extends JTextField
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private boolean changeable;
  private boolean containsWrongAnswer;

  public ChangeableBlock(String number, boolean changeable)
  {
    if (!number.equals("0"))
    {
      setText(number);
      setEditable(false);
    }
    
    this.changeable = changeable;
    containsWrongAnswer = false;
    setPreferredSize(new Dimension(70,70));
    setFont(new Font(Font.DIALOG, Font.BOLD, 40));
    setHorizontalAlignment(JTextField.CENTER);
    setOpaque(false);
    //setEditable(false);
  }
  
  public boolean canChange()
  {
    return changeable;
  }
  
  public boolean containsWrongAnswer()
  {
    return containsWrongAnswer;
  }
  
  public void wrongAnswer()
  {
    containsWrongAnswer = true;
  }
  
  public void correctAnswer()
  {
    containsWrongAnswer = false;
  }
  
  public void reset()
  {
    if (changeable)
    {
      setText("");
      setEditable(true);
      setOpaque(false);
      containsWrongAnswer = false;
    }
  }
}
