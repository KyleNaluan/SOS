package sprint3.product;

public class GeneralGame extends SosGame {
  
  public GeneralGame() {
    
  }
  
  @Override
  public String showScore() {
    return "Blue: " + bluePlayer.getSosCount() + " | Red: " + redPlayer.getSosCount();
  }
  
  @Override
  public void makeMove(int row, int col) {
    if (isValidMove(row,col)) {
      if (turn == 'B') {
        char blueMove = bluePlayer.getMove();
        placeLetter(row, col, blueMove);
        
        // represents the number of SOSs formed from that specific move
        int sosCount = hasMadeSos(row, col, blueMove);
        bluePlayer.increaseSosCount(sosCount);
        
        // Check for game end first
        if (isBoardFull()) {
          if (hasWon()) {
            currentGameState = GameState.BLUE_WON;
          } else if (isDraw()) {
            currentGameState = GameState.DRAW;
          } else {
            currentGameState = GameState.RED_WON;
          }
        } else if (sosCount == 0) {
          switchTurn();
        }
      } else if (turn == 'R') {
        char redMove = redPlayer.getMove();
        placeLetter(row, col, redMove);
        
        int sosCount = hasMadeSos(row, col, redMove);
        redPlayer.increaseSosCount(sosCount);
        
        // Check for game end first
        if (isBoardFull()) {
          if (hasWon()) {
            currentGameState = GameState.RED_WON;
          } else if(isDraw()) {
            currentGameState = GameState.DRAW;
          } else {
            currentGameState = GameState.BLUE_WON;
          }
        } else if (sosCount == 0){
          switchTurn();
        }
      }
    }
  }
  
  
  // For General Game, hasWon() and isDraw() should only be called when Board is full
  // No need to check if board is full in these methods, check before calling them in makeMove
  
  @Override
  public boolean hasWon() {
    if (turn == 'B') {
      if (bluePlayer.getSosCount() > redPlayer.getSosCount()) {
        return true;
      } else {
        return false;
      }
    } else {
      if (redPlayer.getSosCount() > bluePlayer.getSosCount()) {
        return true;
      } else {
        return false;
      }
    }
  }
  
  @Override
  public boolean isDraw() {
    if (bluePlayer.getSosCount() == redPlayer.getSosCount()) {
        return true;
    }
    
    return false;
  }
}