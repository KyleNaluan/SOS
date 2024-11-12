package sprint4.product;

import java.util.Random;

public class ComputerPlayer extends Player {

  public ComputerPlayer(char move) {
    super(move);
  }

  public int[] makeRandomMove(SosGame game) {
    int[] moveCoords = {-1, -1};
    
    int numberOfCells = game.getTotalRows() * game.getTotalColumns();
    Random random = new Random();

    char[] moves = { 'S', 'O' };
    this.move = moves[random.nextInt(2)];
    int targetCoords = random.nextInt(numberOfCells);
    int index = 0;
    for (int row = 0; row < game.getTotalRows(); ++row) {
      for (int col = 0; col < game.getTotalColumns(); ++col) {
        if (targetCoords == index) {
          moveCoords[0] = row;
          moveCoords[1] = col;
          return moveCoords;
        } else
          index++;
      }
    }
    return moveCoords;
  }

  public int[] calculateMove(SosGame game) {
    int[] moveCoords = calculateSosMakingMove(game);
    
    if (moveCoords[0] == -1) {
      moveCoords = makeRandomMove(game);
    }
    
    return moveCoords;
  }

  public int[] calculateSosMakingMove(SosGame game) {
    int[] moveCoords = {-1, -1};

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.isCellEmpty(row, col)) {
          if (isPotentialSos(game, row, col, 'S')) {
            this.setMove('S');
            moveCoords[0] = row;
            moveCoords[1] = col;
            return moveCoords;
          }
          if (isPotentialSos(game, row, col, 'O')) {
            this.setMove('O');
            moveCoords[0] = row;
            moveCoords[1] = col;
            return moveCoords;
          }
        }
      }
    }
    return moveCoords;
  }

  private boolean isPotentialSos(SosGame game, int row, int col, char move) {
    game.placeLetter(row, col, move);
    boolean isSos = (move == 'S') ? game.checkSosFromS(row, col) : game.checkSosFromO(row, col);
    game.removeLetter(row, col);

    return isSos;
  }

}