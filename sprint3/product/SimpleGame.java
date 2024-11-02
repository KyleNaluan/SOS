package sprint3.product;

public class SimpleGame extends SosGame {
  
  public SimpleGame() {
    
  }
  
  @Override
  public String showScore() {
    return "";
  }
  
  @Override
  public void makeMove(int row, int col) {
    if (isValidMove(row,col)) {
      if (turn == 'B') {
        char blueMove = bluePlayer.getMove();
        placeLetter(row, col, blueMove);
        bluePlayer.increaseSosCount(hasMadeSos(row, col, blueMove));
        
        if (hasWon()) {
          currentGameState = GameState.BLUE_WON;
        } else if (isDraw()) {
          currentGameState = GameState.DRAW;
        } else {
          switchTurn();
        }
      } else if (turn == 'R') {
        char redMove = redPlayer.getMove();
        placeLetter(row, col, redMove);
        redPlayer.increaseSosCount(hasMadeSos(row, col, redMove));
        
        if (hasWon()) {
          currentGameState = GameState.RED_WON;
        } else if(isDraw()) {
          currentGameState = GameState.DRAW;
        } else {
          switchTurn();
        }
      }
    }
  }
  
  
  @Override
  public boolean hasWon() {
    if (turn == 'B') {
      if (bluePlayer.getSosCount() > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      if (redPlayer.getSosCount() > 0) {
        return true;
      } else {
        return false;
      } 
    }    
  }
  
  @Override
  public boolean isDraw() {
    if (isBoardFull() && (bluePlayer.getSosCount() == redPlayer.getSosCount())) {
      return true;
    }
    
    return false;
  }
}