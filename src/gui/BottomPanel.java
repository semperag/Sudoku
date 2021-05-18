package gui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BottomPanel extends JPanel
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private JTextField[] buttons;
  private JLabel timerLabel;
  //private Timer timer1;
  private int wrongNumberOfTimes;
  
  public BottomPanel()
  {
    createComponents();
    setLayout(new GridLayout(0,5));
    
    addComponents();
    //runTimer();
  }

  private void addComponents()
  {
    for(int i = 0; i < 3; i++)
    {
      JTextField btn = new JTextField();
      btn.setPreferredSize(new Dimension(30,70));
      btn.setEditable(false);
      btn.setHorizontalAlignment(JTextField.CENTER);
      
      buttons[i] = btn;
      add(btn);
    }
    
    timerLabel.setPreferredSize(new Dimension(100,100));
    timerLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
    timerLabel.setHorizontalAlignment(JTextField.CENTER);
    
    add(new JLabel("                       "));
    add(timerLabel);
  }

  private void createComponents()
  {
    buttons = new JTextField[3];
    timerLabel = new JLabel("9:59");
    wrongNumberOfTimes = 0;
  }
  
  public boolean wrongAnswer()
  {
    buttons[wrongNumberOfTimes].setBackground(Color.RED);
    wrongNumberOfTimes++;
    
    return wrongNumberOfTimes > 2;
  }
  
  public void reset() 
  {
    for(int i = 0; i < 3; i++)
    {
      buttons[i].setBackground(null);
    }
    wrongNumberOfTimes = 0;
  }
  
  public String setTimer(String time) {
    
    int minutes = Integer.parseInt(time.substring(0, 1));
    int seconds = Integer.parseInt(time.substring(2));
    
    if (seconds == 0) {
      minutes -= 1;
      seconds = 59;
    } else {
      seconds -= 1;
    }
    
    time = minutes + ":" + String.format("%02d" , seconds);
    timerLabel.setText(time);
    
    return time;
  }

}