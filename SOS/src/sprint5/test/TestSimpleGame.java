package sprint5.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import sprint5.product.*;

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
    game.selectMove(0, 0);
    
    assertEquals(game.getCell(0, 0), SosGame.Cell.S);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
  }
  
  @Test
  public void testRedMoveOnNonVacantCell() {
    game.selectMove(0, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    game.selectMove(0, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
  }
  
  @Test
  public void testBlueMoveOnInvalidRow() {
    game.selectMove(5, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
  }
  
  @Test
  public void testBlueMoveOnInvalidColumn() {
    game.selectMove(0, 5);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
  }
  
  @Test
  public void testBlueWin() {
    game.setRedMove('O');
    
    game.selectMove(0, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    game.selectMove(0, 1);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
    game.selectMove(0, 2);
    
    assertEquals(game.getGameStatus(), SosGame.GameStatus.BLUE_WON);
  }
  
  @Test
  public void testDraw() {
 // Row 1
    game.selectMove(0, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    game.selectMove(0, 1);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
    game.selectMove(0, 2);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");

    // Row 2
    game.selectMove(1, 0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
    game.selectMove(1, 1);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    game.selectMove(1, 2); 
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");

    // Row 3
    game.selectMove(2, 0); 
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red"); 
    game.selectMove(2, 1);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
    game.selectMove(2, 2);

    assertEquals(game.getGameStatus(), SosGame.GameStatus.DRAW);
  }
  
  @Test
  public void testContinuingGame() {
    game.selectMove(0,2);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    
    game.setRedMove('O');
    game.selectMove(1, 2);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Blue");
    
    game.selectMove(2,0);
    assertEquals("Blue", game.getCurrentPlayer().getName(), "Red");
    
    assertEquals(game.getGameStatus(), SosGame.GameStatus.PLAYING);
  }
}