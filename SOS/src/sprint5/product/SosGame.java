package sprint5.product;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SosGame {

  public enum Cell {
    EMPTY, S, O
  }

  public enum GameStatus {
    SELECTION, PLAYING, DRAW, BLUE_WON, RED_WON
  }

  protected int totalRows;
  protected int totalCols;
  protected GameStatus currentGameStatus;
  protected Player bluePlayer = new Player("Blue", 'S');
  protected Player redPlayer = new Player("Red", 'S');
  protected Player currentPlayer = bluePlayer;

  protected Cell[][] board;
  protected ArrayList<SosSequence> sosSequences = new ArrayList<SosSequence>();

  protected File moveHistory = new File("moveHistory.txt");
  protected ArrayList<String> recordedMoves = new ArrayList<String>();
  protected boolean isRecording;

  public SosGame() {
    if (totalRows == 0) {
    }
    board = new Cell[totalRows][totalCols];
    currentGameStatus = GameStatus.SELECTION;
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
    sosSequences.clear();
    recordedMoves.clear();

    currentGameStatus = GameStatus.PLAYING;
    currentPlayer = bluePlayer;
  }

  public ArrayList<String[]> replayGame() {
    startGame();
    return loadMovesFromFile(moveHistory);
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

  public void setBluePlayer(char type) {
    char currentMove = bluePlayer.getMove();
    if (type == 'H') {
      bluePlayer = new Player("Blue", currentMove);
    } else if (type == 'C') {
      bluePlayer = new ComputerPlayer("Blue", currentMove);
    }
  }

  public void setRedPlayer(char type) {
    char currentMove = redPlayer.getMove();
    if (type == 'H') {
      redPlayer = new Player("Red", currentMove);
    } else if (type == 'C') {
      redPlayer = new ComputerPlayer("Red", currentMove);
    }
  }

  public void setRecording(boolean recording) {
    isRecording = recording;
  }

  // Get Functions
  public int getTotalRows() {
    return totalRows;
  }

  public int getTotalColumns() {
    return totalCols;
  }

  public GameStatus getGameStatus() {
    return currentGameStatus;
  }

  public Player getBluePlayer() {
    return bluePlayer;
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public ArrayList<SosSequence> getSosSequences() {
    return sosSequences;
  }

  public Cell getCell(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalCols) {
      return board[row][col];
    } else {
      return null;
    }
  }

  public int getNumberOfEmptyCells() {
    int count = 0;
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        if (board[row][col] == Cell.EMPTY) {
          count++;
        }
      }
    }
    return count;
  }
  
  public boolean isGameRecorded() {
    return isRecording;
  }

  // Other Game Functions
  protected void switchTurn() {
    currentPlayer = (currentPlayer == bluePlayer) ? redPlayer : bluePlayer;
  }

  public void placeLetter(int row, int col, char choice) {
    board[row][col] = (choice == 'S') ? Cell.S : Cell.O;
  }

  public void removeLetter(int row, int col) {
    board[row][col] = Cell.EMPTY;
  }

  public void selectMove(int row, int col) {
    if (currentPlayer instanceof ComputerPlayer) {
      int[] move = ((ComputerPlayer) currentPlayer).calculateMove(this);
      if (isValidMove(move[0], move[1])) {
        makeMove(move[0], move[1], currentPlayer.getMove());
      }
    } else {
      if (isValidMove(row, col)) {
        makeMove(row, col, currentPlayer.getMove());
      }
    }
  }

  public void updateGameStatus() {

    if (hasBlueWon()) {
      currentGameStatus = GameStatus.BLUE_WON;
    } else if (hasRedWon()) {
      currentGameStatus = GameStatus.RED_WON;
    } else if (isDraw()) {
      currentGameStatus = GameStatus.DRAW;
    }
    
    if (!(currentGameStatus == GameStatus.PLAYING || currentGameStatus == GameStatus.SELECTION)) {
      saveMoveHistory(moveHistory);
    }

  }

  // Record/Replay Functions
  protected void recordMove(int row, int col, char moveType) {
    String move = row + "," + col + "," + moveType;
    recordedMoves.add(move);
  }

  protected void saveMoveHistory(File file) {
    try {
      FileWriter moveFile = new FileWriter(file);
      BufferedWriter writer = new BufferedWriter(moveFile);
      for (int i = 0; i < recordedMoves.size(); i++) {
        writer.write(recordedMoves.get(i));
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("Error handling file");
    }
    
  }
  
  protected ArrayList<String[]> loadMovesFromFile(File file){
    ArrayList<String[]> moves = new ArrayList<String[]>();
    
    try {
      Scanner reader = new Scanner(file);
      while (reader.hasNextLine()) {
        String temp = reader.nextLine();
        moves.add(temp.split(","));
      }
      reader.close();
    } catch (FileNotFoundException e){
      System.out.println("Error reading file");
    }
    
    return moves;
  }

  // Abstract Functions
  public abstract void makeMove(int row, int col, char moveType);

  public abstract String showScore();

  protected abstract boolean hasBlueWon();

  protected abstract boolean hasRedWon();

  // Predicate Functions
  protected boolean isValidMove(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalCols && board[row][col] == Cell.EMPTY) {
      return true;
    }

    return false;
  }

  public boolean isCellEmpty(int row, int col) {
    if (board[row][col] == Cell.EMPTY) {
      return true;
    }
    return false;
  }

  protected boolean isBoardFull() {
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        if (board[row][col] == Cell.EMPTY) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isDraw() {
    if (isBoardFull() && (bluePlayer.getSosCount() == redPlayer.getSosCount())) {
      return true;
    }
    return false;
  }

  // Method to determine if an SOS was formed after a move | Modifies sosCount and
  // sosSequences
  protected int hasMadeSos(int row, int col, char choice) {
    if (choice == 'O') {
      return madeSosFromO(row, col);
    }

    return madeSosFromS(row, col);
  }

  // Method to check if an SOS has been formed after placing an O
  private int madeSosFromO(int row, int col) {
    int sosCount = 0;

    // A vertical SOS can't be formed from O if it was placed at the top or bottom
    // board cell
    if (!(row == 0 || row == totalRows - 1)) {
      if (board[row - 1][col] == Cell.S && board[row + 1][col] == Cell.S) {
        sosSequences.add(new SosSequence((row - 1), col, (row + 1), col, currentPlayer));
        sosCount++;
      }
    }

    // A horizontal SOS can't be formed from O if it was placed at the
    // left/rightmost board cell
    if (!(col == 0 || col == totalCols - 1)) {
      if (board[row][col - 1] == Cell.S && board[row][col + 1] == Cell.S) {
        sosSequences.add(new SosSequence(row, (col - 1), row, (col + 1), currentPlayer));
        sosCount++;
      }
    }

    // A diagonal SOS can't be formed from an O on an edge board cell
    if (!((row == 0 || row == totalRows - 1) || (col == 0 || col == totalCols - 1))) {
      // Top left to bottom right
      if (board[row - 1][col - 1] == Cell.S && board[row + 1][col + 1] == Cell.S) {
        sosSequences.add(new SosSequence((row - 1), (col - 1), (row + 1), (col + 1), currentPlayer));
        sosCount++;
      }
      // Bottom left to top right
      if (board[row + 1][col - 1] == Cell.S && board[row - 1][col + 1] == Cell.S) {
        sosSequences.add(new SosSequence((row + 1), (col - 1), (row - 1), (col + 1), currentPlayer));
        sosCount++;
      }
    }

    return sosCount;
  }

  private int madeSosFromS(int row, int col) {
    int sosCount = 0;

    // VERTICAL SOSs

    // Any S placed in at least the 3rd row can be checked for a vertical SOS going
    // up
    if (row >= 2 && board[row - 1][col] == Cell.O && board[row - 2][col] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row - 2), col, currentPlayer));
      sosCount++;
    }
    // Any S placed in at most the 3rd to last row can be checked for a vertical SOS
    // going down
    if (row <= totalRows - 3 && board[row + 1][col] == Cell.O && board[row + 2][col] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row + 2), col, currentPlayer));
      sosCount++;
    }

    // HORIZONTAL SOSs

    // Any S placed in at least the 3rd column can be checked for a horizontal SOS
    // going left
    if (col >= 2 && board[row][col - 1] == Cell.O && board[row][col - 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, row, (col - 2), currentPlayer));
      sosCount++;
    }
    // Any S placed in at most the 3rd to last column can be checked for a
    // horizontal SOS going right
    if (col <= totalCols - 3 && board[row][col + 1] == Cell.O && board[row][col + 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, row, (col + 2), currentPlayer));
      sosCount++;
    }

    // DIAGONAL SOSs

    // Check for bottom right to top left SOS | placed in at least 3rd row and 3rd
    // column
    if (row >= 2 && col >= 2 && board[row - 1][col - 1] == Cell.O && board[row - 2][col - 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row - 2), (col - 2), currentPlayer));
      sosCount++;
    }
    // Check for bottom left to top right SOS | placed in at least 3rd row at most
    // 3rd to last column
    if (row >= 2 && col <= totalCols - 3 && board[row - 1][col + 1] == Cell.O && board[row - 2][col + 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row - 2), (col + 2), currentPlayer));
      sosCount++;
    }
    // Check for top right to bottom left SOS | placed in at most 3rd to last row
    // and at least 3rd column
    if (row <= totalRows - 3 && col >= 2 && board[row + 1][col - 1] == Cell.O && board[row + 2][col - 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row + 2), (col - 2), currentPlayer));
      sosCount++;
    }
    // Check for top left to bottom right SOS | placed in at most 3rd to last row
    // and 3rd to last column
    if (row <= totalRows - 3 && col <= totalCols - 3 && board[row + 1][col + 1] == Cell.O
        && board[row + 2][col + 2] == Cell.S) {
      sosSequences.add(new SosSequence(row, col, (row + 2), (col + 2), currentPlayer));
      sosCount++;
    }

    return sosCount;
  }

  // Versions of hasMadeSos that only checks for a potential SOS formation | Make
  // no modifications
  protected boolean checkSosFromO(int row, int col) {

    boolean verticalO = (!(row == 0 || row == totalRows - 1)
        && (board[row - 1][col] == Cell.S && board[row + 1][col] == Cell.S));

    boolean horizontalO = (!(col == 0 || col == totalCols - 1)
        && (board[row][col - 1] == Cell.S && board[row][col + 1] == Cell.S));

    boolean diagonalO = (!((row == 0 || row == totalRows - 1) || (col == 0 || col == totalCols - 1))
        && ((board[row - 1][col - 1] == Cell.S && board[row + 1][col + 1] == Cell.S)
            || (board[row + 1][col - 1] == Cell.S && board[row - 1][col + 1] == Cell.S)));

    return verticalO || horizontalO || diagonalO;

  }

  protected boolean checkSosFromS(int row, int col) {
    boolean verticalS = (row >= 2 && board[row - 1][col] == Cell.O && board[row - 2][col] == Cell.S)
        || (row <= totalRows - 3 && board[row + 1][col] == Cell.O && board[row + 2][col] == Cell.S);

    boolean horizontalS = (col >= 2 && board[row][col - 1] == Cell.O && board[row][col - 2] == Cell.S)
        || (col <= totalCols - 3 && board[row][col + 1] == Cell.O && board[row][col + 2] == Cell.S);

    boolean diagonalS = (row >= 2 && col >= 2 && board[row - 1][col - 1] == Cell.O && board[row - 2][col - 2] == Cell.S)
        || (row >= 2 && col <= totalCols - 3 && board[row - 1][col + 1] == Cell.O && board[row - 2][col + 2] == Cell.S)
        || (row <= totalRows - 3 && col >= 2 && board[row + 1][col - 1] == Cell.O && board[row + 2][col - 2] == Cell.S)
        || (row <= totalRows - 3 && col <= totalCols - 3 && board[row + 1][col + 1] == Cell.O
            && board[row + 2][col + 2] == Cell.S);

    return verticalS || horizontalS || diagonalS;
  }

}