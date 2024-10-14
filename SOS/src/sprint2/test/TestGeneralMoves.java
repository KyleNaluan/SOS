package sprint2.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import sprint2.product.*;

public class TestGeneralMoves {
  
  private SosGame game;
  
  @Before
  public void setUp() throws Exception {
    game = new SosGame();
    game.setBoardSize(5);
    game.setGameMode(SosGame.GameMode.SIMPLE);
    game.setBlueMove('S');
    game.setRedMove('S');
    game.startGame();
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void testBlueMoveOnVacantCell() {
    game.makeMoveGeneralGame(0, 0);
    
    assertEquals(game.getCell(0, 0), SosGame.Cell.S);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testRedMoveOnNonVacantCell() {
    game.makeMoveGeneralGame(0, 0);
    assertEquals(game.getTurn(), 'R');
    game.makeMoveSimpleGame(0, 0);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testBlueMoveOnInvalidRow() {
    game.makeMoveGeneralGame(5, 0);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveOnInvalidColumn() {
    game.makeMoveGeneralGame(0, 5);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveToMakeSOS() {
    game.setRedMove('O');
    
    game.makeMoveGeneralGame(0,0);
    assertEquals(game.getTurn(), 'R');
    game.makeMoveGeneralGame(0,1);
    assertEquals(game.getTurn(), 'B');
    game.makeMoveGeneralGame(0, 2);
    assertEquals(game.getTurn(), 'B');
  }
  
  public void testBlueMoveToNotMakeSOS() {
    game.setRedMove('S');
    
    game.makeMoveGeneralGame(0,0);
    assertEquals(game.getTurn(), 'R');
    game.makeMoveGeneralGame(0,1);
    assertEquals(game.getTurn(), 'B');
    game.makeMoveGeneralGame(0, 2);
    assertEquals(game.getTurn(), 'R');
  }
  
  
}
