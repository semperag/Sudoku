package model;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard
{
  private int[][] entireBoard;
  private boolean[][] changeableElements;
  private List<int[][]> miniBoards;
  private String fileText;
  
  public SudokuBoard(String text)
  {
    entireBoard = new int[9][9];
    changeableElements = new boolean[9][9];
    miniBoards = new ArrayList<>();
    fileText = text;
    
    addToBoards(fileText);
    boardSolver();
  }
  
  public void addToBoards(String text)
  {
    for(int row = 0; row < 9; row++)
    {
      miniBoards.add(new int[3][3]);
      for(int col = 0; col < 9; col++)
      {
        int number = Integer.parseInt((text.charAt((row * 9) + col)) + "");
        entireBoard[row][col] = number;
        
        if ( number != 0 )
          changeableElements[row][col] = false;
        else
          changeableElements[row][col] = true;
      }
    }
    
    for(int index = 0; index < 9; index++)
    {
      for(int row = 0; row < 3; row++)
      {
        for(int col = 0; col < 3; col++)
        {
          int position;
          
          if (row == 0)
            position = row + col;
          else if (row == 1)
            position = row + col + 8;
          else
            position = row + col + 16;
          
          if (index == 1)
            position += 3;
          else if (index == 2)
            position += 6;
          else if (index == 3)
            position += 27;
          else if (index == 4)
            position += 30;
          else if (index == 5)
            position += 33;
          else if (index == 6)
            position += 54;
          else if (index == 7)
            position += 57;
          else if (index == 8)
            position += 60;
          
          
          
          miniBoards.get(index)[row][col] = Integer.parseInt(text.charAt(position) + "");
        }
      }
    }
  }
  
  public void addAtPosition(int number, int rowPos, int colPos)
  {
    
    if(number != 0 && numberCanBePlaced(number, rowPos, colPos))
    {
      entireBoard[rowPos][colPos] = number;
      
      int index = findIndex(rowPos, colPos);
      miniBoards.get(index)[findNumber(rowPos)][findNumber(colPos)] = number;
    }
  }

  public int[][] getEntireBoard()
  {
    return entireBoard;
  }
  
  public List<int[][]> getMiniBoards()
  {
    return miniBoards;
  }
  
  public boolean numberCanBePlaced(int number, int rowPos, int colPos)
  {
    boolean result = true;
    int index = 0;
    int count = 0;
    
    for(int i = 0; i < 9; i++)
    {
      if(entireBoard[rowPos][i] == number)
        count++;
      
      if(entireBoard[i][colPos] == number)
        count++;
    }
    
    index = findIndex(rowPos, colPos);
    
    for(int row = 0; row < 3; row++)
      for(int col = 0; col < 3; col++)
      {
        if(miniBoards.get(index)[row][col] == number)
          result = false;
      }
    
    return count < 1 && result;
  }
  
  public void resetAtPosition(int rowPos, int colPos)
  {
      entireBoard[rowPos][colPos] = 0;
      
      int index = findIndex(rowPos, colPos);
      miniBoards.get(index)[findNumber(rowPos)][findNumber(colPos)] = 0;

  }
  
  public int getNumber(int row, int col)
  {
    return entireBoard[row][col];
  }
  
  public int getNumberBefore(int row, int col)
  {
    int number;
    
    if( row-1 != -1 && col == 0 )
      number = entireBoard[row-1][8];
    else
      number = entireBoard[row][col-1];
      
    return number;
  }
  
  public void boardSolver()
  {
    String direction = "forward";
    int number = 1;
    
    boardSolver(0, 0, number, direction);
    
    //System.out.println(board.toString());
  }
  
  public boolean boardSolver(int row, int col, int number, String direction)
  {
    boolean result = false;
    int newCol = col;
    int newRow = row;
    //System.out.println(toString());
    
    if (col == 9)
    {
      newRow++;
      newCol = 0;
    }
    else if (col == -1)
    {
      newRow--;
      newCol = 8;
    }
    
    if (newRow == 9)
    {
      result = true;
    }
    else if(newRow == -1)
    {
      result = false;
    }
    else if( changeableElements[newRow][newCol] )
    {
      if ( number == 10 )
      {
        resetAtPosition(newRow, newCol);
        
        if ( newCol-1 == -1 && newRow-1 == -1)
          return false;
        
        int newNumber = getNumberBefore(newRow, newCol);
        result = boardSolver(newRow, newCol-1, newNumber+1, "back");
      }
      else if ( numberCanBePlaced(number, newRow, newCol) )
      {
        //System.out.println(toString());
        addAtPosition(number, newRow, newCol);
        result = boardSolver(newRow, newCol+1, 1, "forward");
      }
      else
      {
        result = boardSolver(newRow, newCol, number+1, "forward");
      }
      
    }
    else if( direction.equals("forward") )
    {
      result = boardSolver(newRow, newCol+1, 1, "forward");
    }
    else
    {
      if ( newCol-1 == -1 && newRow-1 == -1)
        return false;
      int newNumber = getNumberBefore(newRow, newCol);
      result = boardSolver(newRow, newCol-1, newNumber+1, "back");
    }
    
    return result;
  }
  
  private int findIndex(int rowPos, int colPos)
  {
    int index = 0;
    
    if(rowPos > 5)
      index += 6;
    else if(rowPos > 2)
      index += 3;
    
    if(colPos > 5)
      index += 2;
    else if(colPos > 2)
      index += 1;
    
    return index;
  }
  
  private int findNumber(int number)
  {
    int num = 0;
    
    if(number % 3 == 1)
      num = 1;
    else if(number % 3 == 2)
      num = 2;
    
    return num;
  }
  
  public void reset()
  {
    addToBoards(fileText);
    //boardSolver();
  }
  
  public String toString()
  {
    String text = "";
    for(int row = 0; row < 9; row++)
    {
      for(int col = 0; col < 9; col++)
      {
        text += entireBoard[row][col];
      }
    }
    
    return text;
  }
}
