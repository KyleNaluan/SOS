package sprint4.product;

public class GeneralGame extends SosGame {

  public GeneralGame() {

  }

  @Override
  public String showScore() {
    return "Blue: " + bluePlayer.getSosCount() + " | Red: " + redPlayer.getSosCount();
  }

  @Override
  public void makeMove(int row, int col, char move) {
    if (isValidMove(row, col)) {
      placeLetter(row, col, move);

      int sosCount = hasMadeSos(row, col, move);
      currentPlayer.increaseSosCount(sosCount);
      
      updateGameStatus();

      if (sosCount == 0) {
        switchTurn();
      }
    }
  }

  @Override
  protected boolean hasBlueWon() {
    if (isBoardFull()) {
      if (bluePlayer.getSosCount() > redPlayer.getSosCount()) {
        return true;
      }
    }
    return false;
  }

  @Override
  protected boolean hasRedWon() {
    if (isBoardFull()) {
      if (redPlayer.getSosCount() > bluePlayer.getSosCount()) {
        return true;
      }
    }
    return false;
  }

}