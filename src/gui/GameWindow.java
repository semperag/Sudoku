package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import file.FileIO;
import model.SudokuBoard;

public class GameWindow extends JFrame implements KeyListener, ActionListener, FocusListener
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private SudokuBoard board;
  private FileIO file;
  private String textBoard;
  private BoardPanel boardPanel;
  private BottomPanel bottomPanel;
  private JPanel mainPanel;
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenu editMenu;
  private JMenuItem exitItem;
  private JMenuItem solveItem;
  private JMenuItem resetItem;
  private Timer timer;
  private String time;
  private int correctAnswers = 0;
  private boolean hasNotWon = true;
  
  //private PrintWriter out;
  
  public GameWindow(File file) throws FileNotFoundException
  {
    this.file = new FileIO(file);
    time = "9:59";
    timer = new Timer(1000, this);
    
    createComponents();
    setLayouts();
    addComponents();
    setListeners();
    
    setJMenuBar(menuBar);
    setTitle("Soduko by Semperag");
    setContentPane(mainPanel);
    
    pack();
    setLocationRelativeTo(null);

    timer.start();
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //bottomPanel.runTimer();
  }

  private void setListeners()
  {
    for(int row = 0; row < 9; row++)
    {
      for(int col = 0; col < 9; col++)
      {
        ChangeableBlock block = boardPanel.getBlock(row, col);
        
        if ( block.canChange() )
        {
          boardPanel.getBlock(row, col).addKeyListener(this);
          boardPanel.getBlock(row, col).addFocusListener(this);
        }
      }
    }
    
    exitItem.addActionListener(this);
    solveItem.addActionListener(this);
    resetItem.addActionListener(this);
  }

  private void addComponents()
  {
    fileMenu.add(exitItem);
    editMenu.add(solveItem);
    editMenu.add(resetItem);
    
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    
    mainPanel.add(menuBar);
    mainPanel.add(boardPanel);
    mainPanel.add(bottomPanel);
  }

  private void setLayouts()
  {
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
  }

  private void createComponents()
  {
    mainPanel = new JPanel();
    
    textBoard = file.toString();
    
    bottomPanel = new BottomPanel();
    bottomPanel.setTimer(time);
    
    boardPanel = new BoardPanel(textBoard);
    board = new SudokuBoard(textBoard);
    
    
    menuBar = new JMenuBar();
    menuBar.setPreferredSize(new Dimension(100,50));
    
    fileMenu = new JMenu("File");
    editMenu = new JMenu("Edit");
    
    exitItem = new JMenuItem("Exit");
    solveItem = new JMenuItem("Solve");
    resetItem = new JMenuItem("Reset");
  }
/*  
  private void boardSolver()
  {
    String direction = "forward";
    int number = 1;
    
    board.reset();
    boardSolver(0, 0, number, direction);
    
    displaySolution();
  }
  
  private boolean boardSolver(int row, int col, int number, String direction)
  {
    boolean result = false;
    int newCol = col;
    int newRow = row;
    
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
    
    boolean notOutOfBounds = newCol-1 > -1 || newRow-1 > -1;
    
    if (newRow == 9)
    {
      result = true;
    }
    else if(newRow == -1)
    {
      result = false;
    }
    else if( boardPanel.getBlock(newRow, newCol).canChange() )
    {
      if ( number == 10 )
      {
        board.resetAtPosition(newRow, newCol);
        
        if ( notOutOfBounds )
        {
          int newNumber = board.getNumberBefore(newRow, newCol);
          result = boardSolver(newRow, newCol-1, newNumber+1, "back");
        }
        else 
        {
          result = false;
        }
        
      }
      else if ( board.numberCanBePlaced(number, newRow, newCol) )
      {
        board.addAtPosition(number, newRow, newCol);
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
      if ( notOutOfBounds )
      {
        int newNumber = board.getNumberBefore(newRow, newCol);
        result = boardSolver(newRow, newCol-1, newNumber+1, "back");
      }
      else
      {
        result = false;
      }
    }
    
    return result;
  }
*/
  private void displaySolution()
  {
    for(int row = 0; row < 9; row++)
    {
      for(int col = 0; col < 9; col++)
      {
        int number = board.getNumber(row, col);
        boardPanel.getBlock(row, col).setText("" + number);
      }
    }
  }
 
  private boolean isANumber( char input )
  {
    boolean result = false;
    for ( int i = 1; i < 10; i++)
    {
      if ( input == Character.forDigit(i, 10) && input != '0' )
        result = true;
    }
    return result;
  }
  
  @Override
  public void keyPressed(KeyEvent e)
  {
    char keyInput = e.getKeyChar();
    ChangeableBlock block = (ChangeableBlock) e.getSource();
    
    
    if ( isANumber( keyInput ) && !boardPanel.didPlayerLose())
    {
      block.setEditable(true);
    }
    else if (!boardPanel.didPlayerLose() && keyInput == KeyEvent.VK_BACK_SPACE) {
      block.setText("");
    
    } else 
    {
    //block.setText("");
      block.setEditable(false);
      //block.setText("");
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    char keyInput = e.getKeyChar();
    if ( isANumber(keyInput ) && !boardPanel.didPlayerLose() )
    {
      ChangeableBlock block = (ChangeableBlock) e.getSource();
      int number = Integer.parseInt(keyInput + "");
    
      for(int row = 0; row < 9; row++)
      {
        for(int col = 0; col < 9; col++)
        {
          if (boardPanel.getBlock(row, col).equals(block))
          {
            if (board.getEntireBoard()[row][col] != number)
            {
              block.setText("" + number);
              //block.setBorder(
              //   BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
              block.setOpaque(true);
              block.setBackground(new Color(255,204,203));
              block.wrongAnswer();
              if ( bottomPanel.wrongAnswer() )
              {
                boardPanel.lostGame();
                timer.stop();
                JOptionPane.showMessageDialog(this, 
                    "           You have lost.\nClick on 'edit' and reset game!", 
                    "Loser", JOptionPane.INFORMATION_MESSAGE);
              }
            }
            else
            {
              block.setText("" + number);
              //block.setBorder(
              //    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GREEN));
              block.setOpaque(false);
              block.transferFocus();
              block.correctAnswer();
              correctAnswers++;
            }
          }
        }
      }
    }
    else 
    {
      //block.reset();
    }
    
    if (correctAnswers == 54 && hasNotWon) {
      hasNotWon = false;
      timer.stop();
      boardPanel.lostGame();
      JOptionPane.showMessageDialog(this, 
          "           You have Won!\nClick on 'edit' and reset game!", 
          "Winner", JOptionPane.INFORMATION_MESSAGE);
    }
      
  }

  @Override
  public void keyTyped(KeyEvent e)
  {}

  @Override
  public void actionPerformed(ActionEvent e)
  {
    //setVisible(true);
    boolean timerRelated = true;
   
    try {
    JMenuItem menuItem = (JMenuItem) e.getSource();
    String text = menuItem.getText();
    
    if (text.equals("Exit"))
    {
      dispose();
      System.exit(0);
    }
    else if (text.equals("Reset"))
    {
      boardPanel.reset();
      board.reset();
      board.boardSolver();
      bottomPanel.reset();
      correctAnswers = 0;
      hasNotWon = true;
      time = "9:59";
      timer.start();
    }
    else if (text.equals("Solve"))
    {
      displaySolution();
      boardPanel.setBorders();
      boardPanel.lostGame();
      timer.stop();
      JOptionPane.showMessageDialog(this, 
          "Click on 'edit' to reset game!", 
          "Solved", JOptionPane.INFORMATION_MESSAGE);
    }
    
    timerRelated = false;
    } catch (ClassCastException exception) {
      
    }
    
    if (timerRelated) {
      if (!time.equals("0:00")) {
        time = bottomPanel.setTimer(time);
      } else {
        boardPanel.lostGame();
        JOptionPane.showMessageDialog(this, 
            "           You have lost.\nClick on 'edit' and reset game!", 
            "Loser", JOptionPane.INFORMATION_MESSAGE);
        timer.stop();
      }
    }
    
    //setVisible(true);
  }

  @Override
  public void focusGained(FocusEvent e)
  {
    ChangeableBlock block = (ChangeableBlock) e.getSource();
    if ( !boardPanel.didPlayerLose() && !block.containsWrongAnswer() )
    {
      block.setEditable(false);
      block.setOpaque(true);
      block.setBackground(Color.LIGHT_GRAY);
      
    }
  }

  @Override
  public void focusLost(FocusEvent e)
  {
    ChangeableBlock block = (ChangeableBlock) e.getSource();
    if ( !block.containsWrongAnswer() )
    {
      block.setBackground(null);
      block.setOpaque(false);
    }
  }
}
