package sprint4.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import sprint4.product.*;

public class TestComputerPlayer {

  private SosGame game;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testFirstMoveSimpleGame() {
    game = new SimpleGame();
    game.setBoardSize(3);
    game.setBluePlayer('C');

    game.startGame();

    int nonEmpty = 0;

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) != SosGame.Cell.EMPTY) {
          nonEmpty++;
        }
      }
    }
    
    assertEquals(game.getTurn(), 'R');
    assertEquals(nonEmpty, 1);
  }

  @Test
  public void testComputerMoveToMakeSosSimpleGame() {
    game = new SimpleGame();
    game.setBoardSize(3);
    game.setRedPlayer('C');

    game.startGame();

    game.setBlueMove('O');

    game.placeLetter(0, 0, 'S');

    game.selectMove(0, 1);

    assertEquals(game.getTurn(), 'R');

    game.selectMove(0, 0);

    assertEquals(game.getCell(0, 1), SosGame.Cell.O);
    assertEquals(game.getGameStatus(), SosGame.GameStatus.RED_WON);
  }

  @Test
  public void testComputerRandomMoveSimpleGame() {
    game = new SimpleGame();
    game.setBoardSize(3);
    game.setRedPlayer('C');

    game.startGame();

    game.setBlueMove('S');

    game.placeLetter(0, 0, 'S');

    game.placeLetter(0, 1, 'S');

    assertEquals(game.getTurn(), 'B');

    game.selectMove(0, 2);

    assertEquals(game.getTurn(), 'R');

    game.selectMove(-1, -1);

    int nonEmpty = 0;

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) != SosGame.Cell.EMPTY) {
          nonEmpty++;
        }
      }
    }

    assertEquals(game.getTurn(), 'B');
    assertEquals(game.getGameStatus(), SosGame.GameStatus.PLAYING);
    assertEquals(nonEmpty, 4);
  }

  @Test
  public void testFirstMoveGeneralGame() {
    game = new GeneralGame();
    game.setBoardSize(3);
    game.setBluePlayer('C');

    game.startGame();

    int nonEmpty = 0;

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) != SosGame.Cell.EMPTY) {
          nonEmpty++;
        }
      }
    }

    assertEquals(game.getTurn(), 'R');
    assertEquals(nonEmpty, 1);
  }

  @Test
  public void testComputerMoveToMakeSosGeneralGame() {
    game = new GeneralGame();
    game.setBoardSize(3);
    game.setRedPlayer('C');

    game.startGame();

    game.setBlueMove('O');

    game.placeLetter(0, 0, 'S');

    game.selectMove(0, 1);

    assertEquals(game.getTurn(), 'R');

    game.selectMove(-1, -1);

    assertEquals(game.getTurn(), 'R');
    assertEquals(game.getCell(0, 1), SosGame.Cell.O);
    assertEquals(game.getGameStatus(), SosGame.GameStatus.PLAYING);
  }

  @Test
  public void testComputerRandomMoveGeneralGame() {
    game = new GeneralGame();
    game.setBoardSize(3);
    game.setRedPlayer('C');

    game.startGame();

    game.setBlueMove('S');

    game.placeLetter(0, 0, 'S');

    game.placeLetter(0, 1, 'S');

    assertEquals(game.getTurn(), 'B');

    game.selectMove(0, 2);

    assertEquals(game.getTurn(), 'R');

    game.selectMove(-1, -1);

    int nonEmpty = 0;

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) != SosGame.Cell.EMPTY) {
          nonEmpty++;
        }
      }
    }
    
    assertEquals(game.getTurn(), 'B');
    assertEquals(game.getGameStatus(), SosGame.GameStatus.PLAYING);
    assertEquals(nonEmpty, 4);
  }

}