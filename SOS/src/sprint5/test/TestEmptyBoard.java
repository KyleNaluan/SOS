package sprint5.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import sprint5.product.*;

import org.junit.Test;

public class TestEmptyBoard {
  
  private SosGame game;
  
  @Before
  public void setUp() throws Exception {
    game = new SimpleGame();
    game.setBoardSize(5);
    game.startGame();
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  
  @Test
  public void testEmptyBoardSetup() {
      for (int row = 0; row < game.getTotalRows(); row++) {
          for (int col = 0; col < game.getTotalColumns(); col++) {
              assertEquals(SosGame.Cell.EMPTY, game.getCell(row, col));
          }
      }
  }
  
  @Test
  public void testInvalidRowAccess() {
      // Test with a row that is out of bounds (-1 and greater than the max rows)
      assertNull(game.getCell(-1, 2));
      assertNull(game.getCell(5, 2));
  }
  
  @Test
  public void testInvalidColumnAccess() {
      // Test with a column that is out of bounds (-1 and greater than the max columns)
      assertNull(game.getCell(2, -1));
      assertNull(game.getCell(2, 5));
  }
  
}
