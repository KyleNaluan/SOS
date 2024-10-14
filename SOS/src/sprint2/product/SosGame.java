package sprint2.product;

public class SosGame {
  
  public enum GameMode {
    SIMPLE, GENERAL
  }
  
  public enum Cell {
    EMPTY, S, O
  }
  
  public enum GameState {
    SELECTION, PLAYING, DRAW, BLUE_WON, RED_WON
  }
  
  private int totalRows;
  private int totalCols;
  private GameMode gameMode;
  private GameState currentGameState;
  private char turn;
  private char blueMove = 'S';
  private char redMove = 'S';
  
  private Cell[][] board;
  
  public SosGame() {
    if (totalRows == 0) {
    }
    board = new Cell[totalRows][totalCols];
    currentGameState = GameState.SELECTION;
  }
  
  public void startGame() {
    if (this.gameMode == null) {
      throw new RuntimeException("Please Select a Game Mode");
    }
    
    if (totalRows == 0) {
      throw new RuntimeException("Please Enter a Board Size");
    }
    
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        board[row][col] = Cell.EMPTY;
      }
    }
    currentGameState = GameState.PLAYING;
    turn = 'B';
  }
  
  public void resetGame() {
    startGame();
  }
  
  /**
   * 
   * @precond: n is an integer value
   * @postcond: the board size values will be updated if n is within the allowable range,
   *            and will throw an exception if it is out of bounds
   * 
   * 
   */
  public void setBoardSize(int n) {
    if (n < 3 || n > 10) {
      throw new IllegalArgumentException("Board Size Must be Between 3 and 10");
    }
    
    totalRows = n;
    totalCols = n;
    
    board = new Cell[totalRows][totalCols];
    }
  
  /**
   * 
   * @precond: mode is either SIMPLE or GENERAL
   * @postcond: the gameMode of the SosGame is set to the selected GameMode
   * 
   * 
   */
  public void setGameMode(GameMode mode) {
    gameMode = mode;
  }
  
  public void setBlueMove(char move) {
    blueMove = move;
  }
  
  public void setRedMove(char move) {
    redMove = move;
  }
  
  public int getTotalRows() {
    return totalRows;
  }
  
  public int getTotalColumns() {
    return totalCols;
  }
  
  public Cell getCell(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalCols) {
      return board[row][col];
    } else {
      return null;
    }
  }
  
  public char getTurn() {
    return turn;
  }
  
  public GameMode getGameMode() {
    return gameMode;
  }
  
  public GameState getGameState() {
    return currentGameState;
  }
  
  /**
   * 
   * @precond: choice is either 'S' or 'O'
   * @postcond: if (row, col) is a valid empty cell, then the player's
   *            letter is placed in the cell
   * 
   * 
   */
  public boolean makeMove(int row, int col, char choice) {
    if (row >= 0 && row < totalRows && col >=0 && col < totalCols && board[row][col] == Cell.EMPTY) {
      if (choice == 'S') {
        board[row][col] = Cell.S;
      } else if (choice == 'O') {
        board[row][col] = Cell.O;
      }
      // Return true if player made a valid move
      return true;
    }
    return false;
  }
  
  /**
   * 
   * @precond:
   * @postcond: the player's letter is placed in the cell and the 
   *            turn is changed to the other player
   * 
   * 
   */
  public void makeMoveSimpleGame(int row, int col) {
    
    if (turn == 'B' && makeMove(row, col, blueMove)) {
      turn = 'R';
    } else if (turn == 'R' && makeMove(row, col, redMove)){
       turn = 'B';
    }
  }
  
  /**
   * 
   * @precond: none
   * @postcond: the player's letter is placed in the cell and if an SOS is formed
   *            the player keeps their turn, otherwise turn is changed to the other player
   * 
   * 
   */
  public void makeMoveGeneralGame(int row, int col) {
    if (turn == 'B') {
      if(makeMove(row, col, blueMove) && !hasMadeSos(row, col, blueMove)) {
        turn = 'R';
      }
    } else {
      if (makeMove(row, col, redMove) && !hasMadeSos(row, col, redMove)) {
        turn = 'B';
      }
    }
  }
  
  /**s
   * 
   * @precond: choice is either 'S' or 'O'
   * @postcond: true is returned if the player's choice has formed as SOS,
   *            false is returned otherwise
   * 
   */
  private boolean hasMadeSos(int row, int col, char choice) {
    if (choice == 'O') {
      return madeSosFromO(row, col);
    }
    
    return madeSosFromS(row, col);
  }
  
  // Method to check if an SOS has been formed after placing an O
  private boolean madeSosFromO(int row, int col) {
    boolean vertical = false;
    boolean horizontal = false;
    boolean diagonal = false;
    
    // A vertical SOS can't be formed from O if it was placed at the top or bottom board cell
    if (!(row == 0 || row == totalRows-1)) {
      vertical = (board[row-1][col] == Cell.S && board[row+1][col] == Cell.S);
    }
    
    // A horizontal SOS can't be formed from O if it was placed at the left/rightmost board cell
    if (!(col == 0 || col == totalCols-1)) {
      horizontal = (board[row][col-1] == Cell.S && board[row][col+1] == Cell.S);
    }
    
    // A diagonal SOS can't be formed from an O on an edge board cell
    if (!((row == 0 || row == totalRows-1) || (col == 0 || col == totalCols-1))) {
      diagonal =
          // Top left to bottom right
          (board[row-1][col-1] == Cell.S && board[row+1][col+1] == Cell.S)
          // Bottom left to top right
          || (board[row+1][col-1] == Cell.S && board[row-1][col+1] == Cell.S);
    }
    
    return vertical || horizontal || diagonal;
  }
  
  private boolean madeSosFromS(int row, int col) {    
    boolean vertical =
        // Any S placed in at least the 3rd row can be checked for a vertical SOS going up
        (row >= 2 && board[row-1][col] == Cell.O && board[row-2][col] == Cell.S)
        // Any S placed in at most the 3rd to last row can be checked for a vertical SOS going down
        || (row <= totalRows-3 && board[row+1][col] == Cell.O && board[row+2][col] == Cell.S);
    
    boolean horizontal =
        // Any S placed in at least the 3rd column can be checked for a horizontal SOS going left
        (col >= 2 && board[row][col-1] == Cell.O && board[row][col-2] == Cell.S)
        // Any S placed in at most the 3rd to last column can be checked for a horizontal SOS going right
        || (col <= totalCols-3 && board[row][col+1] == Cell.O && board[row][col+2] == Cell.S);
    
    boolean diagonal = 
        // Check for bottom right to top left SOS
        (row >= 2 && col >= 2 && board[row-1][col-1] == Cell.O && board[row-2][col-2] == Cell.S)
        // Check for bottom left to top right SOS
        || (row >= 2 && col <= totalCols-3 && board[row-1][col+1] == Cell.O && board[row-2][col+2] == Cell.S)
        // Check for top right to bottom left SOS
        || (row <= totalRows-3 && col >= 2 && board[row+1][col-1] == Cell.O && board[row+2][col-2] == Cell.S)
        // Check for top left to bottom right SOS
        || (row <= totalRows-3 && col <= totalCols-3 && board[row+1][col+1] == Cell.O && board[row+2][col+2] == Cell.S);
    
    return vertical || horizontal || diagonal;
  }
}