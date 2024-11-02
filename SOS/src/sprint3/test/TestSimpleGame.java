package sprint3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import sprint3.product.*;

import org.junit.Test;

public class TestSimpleGame {
  
  private SosGame game;
  
  @Before
  public void setUp() throws Exception {
    game = new SimpleGame();
    game.setBoardSize(3);
    game.startGame();
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void testBlueMoveOnVacantCell() {
    game.makeMove(0, 0);
    
    assertEquals(game.getCell(0, 0), SosGame.Cell.S);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testRedMoveOnNonVacantCell() {
    game.makeMove(0, 0);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(0, 0);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testBlueMoveOnInvalidRow() {
    game.makeMove(5, 0);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveOnInvalidColumn() {
    game.makeMove(0, 5);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueWin() {
    game.setRedMove('O');
    
    game.makeMove(0, 0);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(0, 1);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(0, 2);
    
    assertEquals(game.getGameState(), SosGame.GameState.BLUE_WON);
  }
  
  @Test
  public void testDraw() {
 // Row 1
    game.makeMove(0, 0);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(0, 1);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(0, 2);
    assertEquals(game.getTurn(), 'R');

    // Row 2
    game.makeMove(1, 0);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(1, 1);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(1, 2); 
    assertEquals(game.getTurn(), 'B');

    // Row 3
    game.makeMove(2, 0); 
    assertEquals(game.getTurn(), 'R'); 
    game.makeMove(2, 1);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(2, 2);

    assertEquals(game.getGameState(), SosGame.GameState.DRAW);
  }
  
  @Test
  public void testContinuingGame() {
    game.makeMove(0,2);
    assertEquals(game.getTurn(), 'R');
    
    game.setRedMove('O');
    game.makeMove(1, 2);
    assertEquals(game.getTurn(), 'B');
    
    game.makeMove(2,0);
    assertEquals(game.getTurn(), 'R');
    
    assertEquals(game.getGameState(), SosGame.GameState.PLAYING);
  }
}