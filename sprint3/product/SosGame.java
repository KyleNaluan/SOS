package sprint3.product;

import java.util.ArrayList;

public abstract class SosGame {
  
  public enum Cell {
    EMPTY, S, O
  }
  
  public enum GameState {
    SELECTION, PLAYING, DRAW, BLUE_WON, RED_WON
  }
  
  protected int totalRows;
  protected int totalCols;
  protected GameState currentGameState;
  protected char turn;
  protected Player bluePlayer = new Player('S');
  protected Player redPlayer = new Player('S');
  
  protected Cell[][] board;
  protected ArrayList<SosSequence> sosSequences = new ArrayList<SosSequence>();
  
  public SosGame() {
    if (totalRows == 0) {
    }
    board = new Cell[totalRows][totalCols];
    currentGameState = GameState.SELECTION;
  }
  
  public void startGame() {
    if (totalRows == 0) {
      throw new RuntimeException("Please Enter a Board Size");
    }
    
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        board[row][col] = Cell.EMPTY;
      }
    }
    
    bluePlayer.resetSosCount();
    redPlayer.resetSosCount();
    
    currentGameState = GameState.PLAYING;
    turn = 'B';
  }
  
  public void resetGame() {
    startGame();
  }
  
  // Set Functions
  public void setBoardSize(int n) {
    if (n < 3 || n > 10) {
      throw new IllegalArgumentException("Board Size Must be Between 3 and 10");
    }
    
    totalRows = n;
    totalCols = n;
    
    board = new Cell[totalRows][totalCols];
  }
    
  public void setBlueMove(char move) {
    bluePlayer.setMove(move);
  }
  
  public void setRedMove(char move) {
    redPlayer.setMove(move);
  }
  
  // Get Functions
  public int getTotalRows() {
    return totalRows;
  }
  
  public int getTotalColumns() {
    return totalCols;
  }
  
  public char getTurn() {
    return turn;
  }
  
  public GameState getGameState() {
    return currentGameState;
  }
  
  public ArrayList<SosSequence> getSosSequences(){
    return sosSequences;
  }
  
  public Cell getCell(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalCols) {
      return board[row][col];
    } else {
      return null;
    }
  }
  
  // Other Game Functions
  protected void switchTurn() {
    if (turn == 'B') {
      turn = 'R';
    } else if (turn == 'R') {
      turn = 'B';
    }
  }
  
  protected void placeLetter(int row, int col, int choice) {
    if (choice == 'S') {
      board[row][col] = Cell.S;
    } else if (choice == 'O') {
      board[row][col] = Cell.O;
    }
  }
  
  // Abstract Functions
  abstract void makeMove(int row, int col);
  
  abstract String showScore();
  
  abstract boolean hasWon();
  
  abstract boolean isDraw();
  
  // Validation Functions
  protected boolean isValidMove(int row, int col) {
    if (row >= 0 && row < totalRows && col >=0 && col < totalCols && board[row][col] == Cell.EMPTY) {
      return true;
    }
    
    return false;
  }
  
  protected boolean isBoardFull() {
    for (int row = 0; row < totalRows; row ++) {
      for (int col = 0; col < totalCols; col++) {
        if (board[row][col] == Cell.EMPTY) {
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * 
   * @precond: choice is either 'S' or 'O'
   * @postcond: returns an integer > 0 if a player has formed SOS,
   *            returns 0 if no SOS was formed
   * 
   */
  protected int hasMadeSos(int row, int col, char choice) {
    if (choice == 'O') {
      return madeSosFromO(row, col);
    }
    
    return madeSosFromS(row, col);
  }
  
  // Method to check if an SOS has been formed after placing an O
  private int madeSosFromO(int row, int col) {
    int sosCount = 0;
    
    // A vertical SOS can't be formed from O if it was placed at the top or bottom board cell
    if (!(row == 0 || row == totalRows-1)) {
      if (board[row-1][col] == Cell.S && board[row+1][col] == Cell.S) {
        sosSequences.add(new SosSequence((row-1),col,(row+1),col,turn));
        sosCount++;
      }
    }
    
    // A horizontal SOS can't be formed from O if it was placed at the left/rightmost board cell
    if (!(col == 0 || col == totalCols-1)) {
      if (board[row][col-1] == Cell.S && board[row][col+1] == Cell.S) {
        sosSequences.add(new SosSequence(row,(col-1),row,(col+1),turn));
        sosCount++;
      }
    }
    
    // A diagonal SOS can't be formed from an O on an edge board cell
    if (!((row == 0 || row == totalRows-1) || (col == 0 || col == totalCols-1))) {
      // Top left to bottom right
      if (board[row-1][col-1] == Cell.S && board[row+1][col+1] == Cell.S) {
        sosSequences.add(new SosSequence((row-1),(col-1),(row+1),(col+1),turn));
        sosCount++;
      }
      // Bottom left to top right
      if (board[row+1][col-1] == Cell.S && board[row-1][col+1] == Cell.S) {
        sosSequences.add(new SosSequence((row+1),(col-1),(row-1),(col+1),turn));
        sosCount++;
      }
    }
    
    return sosCount;
  }
  
  private int madeSosFromS(int row, int col) {    
    int sosCount = 0;
    
    // VERTICAL SOSs
    
    // Any S placed in at least the 3rd row can be checked for a vertical SOS going up
    if (row >= 2 && board[row-1][col] == Cell.O && board[row-2][col] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row-2),col,turn));
      sosCount++;
    }
    // Any S placed in at most the 3rd to last row can be checked for a vertical SOS going down
    if (row <= totalRows-3 && board[row+1][col] == Cell.O && board[row+2][col] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row+2),col,turn));
      sosCount++;
    }
    
    // HORIZONTAL SOSs
    
    // Any S placed in at least the 3rd column can be checked for a horizontal SOS going left
    if (col >= 2 && board[row][col-1] == Cell.O && board[row][col-2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,row,(col-2),turn));
      sosCount++;
    }
    // Any S placed in at most the 3rd to last column can be checked for a horizontal SOS going right
    if (col <= totalCols-3 && board[row][col+1] == Cell.O && board[row][col+2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,row,(col+2),turn));
      sosCount++;
    }
    
    // DIAGONAL SOSs
    
    // Check for bottom right to top left SOS | placed in at least 3rd row and 3rd column
    if (row >= 2 && col >= 2 && board[row-1][col-1] == Cell.O && board[row-2][col-2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row-2),(col-2),turn));
      sosCount++;
    }
    // Check for bottom left to top right SOS | placed in at least 3rd row at most 3rd to last column
    if (row >= 2 && col <= totalCols-3 && board[row-1][col+1] == Cell.O && board[row-2][col+2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row-2),(col+2),turn));
      sosCount++;
    }
    // Check for top right to bottom left SOS | placed in at most 3rd to last row and at least 3rd column
    if (row <= totalRows-3 && col >= 2 && board[row+1][col-1] == Cell.O && board[row+2][col-2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row+2),(col-2),turn));
      sosCount++;
    }
    // Check for top left to bottom right SOS | placed in at most 3rd to last row and 3rd to last column
    if (row <= totalRows-3 && col <= totalCols-3 && board[row+1][col+1] == Cell.O && board[row+2][col+2] == Cell.S) {
      sosSequences.add(new SosSequence(row,col,(row+2),(col+2),turn));
      sosCount++;
    }
    
    return sosCount;
  }
}