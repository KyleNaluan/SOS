package sprint4.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import sprint4.product.*;

import org.junit.Test;

public class TestGeneralGame {
  
  private SosGame game;
  
  @Before
  public void setUp() throws Exception {
    game = new GeneralGame();
    game.setBoardSize(3);
    game.startGame();
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test
  public void testRedMoveOnNonVacantCell() {
    game.selectMove(0, 0);
    assertEquals(game.getTurn(), 'R');
    game.selectMove(0, 0);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testBlueMoveOnInvalidRow() {
    game.selectMove(5, 0);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveOnInvalidColumn() {
    game.selectMove(0, 5);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveToMakeSOS() {
    game.setRedMove('O');
    
    game.selectMove(0,0);
    assertEquals(game.getTurn(), 'R');
    game.selectMove(0,1);
    assertEquals(game.getTurn(), 'B');
    game.selectMove(0, 2);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveToNotMakeSOS() {
    game.setRedMove('S');
    
    game.selectMove(0,0);
    assertEquals(game.getTurn(), 'R');
    game.selectMove(0,1);
    assertEquals(game.getTurn(), 'B');
    game.selectMove(0, 2);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testBlueWin() {
      // Set Red's move to 'O' initially for different configurations
      game.setRedMove('O');

      // Row 1
      game.selectMove(0, 0); // Blue move
      assertEquals('R', game.getTurn());

      game.selectMove(0, 1); // Red move
      assertEquals('B', game.getTurn());

      game.selectMove(0, 2); // Blue move, forms SOS horizontally
      assertEquals('B', game.getTurn()); // Blue retains turn after forming SOS

      // Row 2
      game.setBlueMove('O'); // Change Blue's move to 'O' for setup
      game.selectMove(1, 0); // Blue move, no SOS
      assertEquals('R', game.getTurn()); // Turn passes to Red as no SOS was formed

      game.selectMove(1, 1); // Red move
      assertEquals('B', game.getTurn());

      game.setRedMove('S'); // Change Red's move to 'S'
      game.selectMove(1, 2); // Blue move, no SOS
      assertEquals('R', game.getTurn());

      // Row 3
      game.setBlueMove('S'); // Reset Blue's move to 'S'
      game.selectMove(2, 0); // Red move, forms single SOS vertically
      assertEquals('R', game.getTurn()); // Red retains turn due to SOS formation

      game.selectMove(2, 1); // Red move, no SOS
      assertEquals('B', game.getTurn());

      game.selectMove(2, 2); // Blue move, final move filling the board

      // Verify Blue's win after the final move
      assertEquals(SosGame.GameStatus.BLUE_WON, game.getGameStatus());
  }

  @Test
  public void testBlueLoss() {
      // Set Red's move to 'O' initially for different configurations
      game.setRedMove('O');

      // Row 1
      game.selectMove(0, 0); // Blue move
      assertEquals('R', game.getTurn());

      game.selectMove(0, 1); // Red move
      assertEquals('B', game.getTurn());

      game.selectMove(0, 2); // Blue move, forms SOS horizontally
      assertEquals('B', game.getTurn()); // Blue retains turn after forming SOS

      // Row 2
      game.setBlueMove('O'); // Change Blue's move to 'O' for setup
      game.selectMove(1, 0); // Blue move, no SOS
      assertEquals('R', game.getTurn()); // Turn passes to Red as no SOS was formed

      game.selectMove(1, 1); // Red move
      assertEquals('B', game.getTurn());

      game.setRedMove('S'); // Change Red's move to 'S'
      game.selectMove(1, 2); // Blue move, no SOS
      assertEquals('R', game.getTurn());

      // Row 3
      game.selectMove(2, 0); // Red move, forms single SOS vertically
      assertEquals('R', game.getTurn()); // Red retains turn due to SOS formation

      game.selectMove(2, 1); // Red move, no SOS
      assertEquals('B', game.getTurn());

      game.selectMove(2, 2); // Blue move, final move filling the board

      // Verify Red's win after the final move by Blue
      assertEquals(SosGame.GameStatus.RED_WON, game.getGameStatus());
  }
  
  @Test
  public void testDraw() {
      // Row 1
      game.selectMove(0, 0);
      assertEquals(game.getTurn(), 'R');
      game.selectMove(0, 1);
      assertEquals(game.getTurn(), 'B');
      game.selectMove(0, 2);
      assertEquals(game.getTurn(), 'R');

      // Row 2
      game.selectMove(1, 0);
      assertEquals(game.getTurn(), 'B');
      game.selectMove(1, 1);
      assertEquals(game.getTurn(), 'R');
      game.selectMove(1, 2); 
      assertEquals(game.getTurn(), 'B');

      // Row 3
      game.selectMove(2, 0); 
      assertEquals(game.getTurn(), 'R'); 
      game.selectMove(2, 1);
      assertEquals(game.getTurn(), 'B');
      game.selectMove(2, 2);

      assertEquals(game.getGameStatus(), SosGame.GameStatus.DRAW);
  }
  
}