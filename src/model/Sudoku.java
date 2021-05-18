package model;

import java.io.File;
import java.io.FileNotFoundException;

import gui.GameWindow;

public class Sudoku
{

  public static void main(String[] args) throws FileNotFoundException, InterruptedException
  { 
    new GameWindow(new File("sudoku_game_3.txt"));
    //SudokuBoard board = new SudokuBoard(new FileIO(new File("sudoku_game_1.txt")).toString());
    //board.reset();
    //System.out.print(board.toString());
    //char letter = '1';
    //int num = 1;
    //System.out.print(letter == Character.forDigit(num, 10) && letter != '0');
  }
}
