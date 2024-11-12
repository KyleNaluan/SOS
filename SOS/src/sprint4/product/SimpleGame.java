package sprint4.product;

import sprint4.product.SosGame.GameStatus;

public class SimpleGame extends SosGame {

  public SimpleGame() {

  }

  @Override
  public String showScore() {
    return "";
  }

  @Override
  public void makeMove(int row, int col, char move) {
    if (isValidMove(row, col)) {
      placeLetter(row, col, move);

      currentPlayer.increaseSosCount(hasMadeSos(row, col, move));
      
      updateGameStatus();

      switchTurn();
    }
  }

  @Override
  public boolean hasBlueWon() {
    if (bluePlayer.getSosCount() > 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean hasRedWon() {
    if (redPlayer.getSosCount() > 0) {
      return true;
    }
    return false;
  }
  
}