package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIO
{
  private BufferedReader file;
  
  public FileIO(File file) throws FileNotFoundException
  {
    this.file = new BufferedReader(new FileReader(file));
  }
  
  @Override
  public String toString()
  {
    String text = "";
    String line;

    try
    {
      line = file.readLine();
    }
    catch (IOException e)
    {
      line = null;
    }
    
    while(line != null)
    {
      text += line;
      try
      {
        line = file.readLine();
      }
      catch (IOException e)
      {
        line = null;
      }
    }
    
    return text;
  }
}
