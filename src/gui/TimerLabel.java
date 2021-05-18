package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TimerLabel extends JLabel
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int elapsedTime;
  private int elapsedMinutes;
  private int elapsedSeconds;

  public TimerLabel(int time)
  { 
    elapsedTime = time;
    elapsedMinutes = (int) elapsedTime / 60;
    elapsedSeconds = elapsedTime % 60;
    
    setPreferredSize(new Dimension(100,100));
    setFont(new Font(Font.DIALOG, Font.BOLD, 20));
    setHorizontalAlignment(JTextField.CENTER);
    
  }
  
  public void run()
  {
    while(elapsedTime > 0)
    {
      elapsedTime--;
      elapsedMinutes = (int) elapsedTime / 60;
      elapsedSeconds = elapsedTime % 60;
      
      setText("Time: " + elapsedMinutes + ":" + elapsedSeconds);
      try
      {
        TimeUnit.SECONDS.sleep(0);
      }
      catch (InterruptedException e)
      {
        System.out.println("Timer is F-ed up");
      }
    }
  }
  
  public void stop()
  {
    elapsedTime = 0;
  }
  
  public void reset()
  {
    elapsedTime = 600;
    run();
  }
}
