package sprint3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import sprint3.product.*;

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
  public void testBlueMoveToMakeSOS() {
    game.setRedMove('O');
    
    game.makeMove(0,0);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(0,1);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(0, 2);
    assertEquals(game.getTurn(), 'B');
  }
  
  @Test
  public void testBlueMoveToNotMakeSOS() {
    game.setRedMove('S');
    
    game.makeMove(0,0);
    assertEquals(game.getTurn(), 'R');
    game.makeMove(0,1);
    assertEquals(game.getTurn(), 'B');
    game.makeMove(0, 2);
    assertEquals(game.getTurn(), 'R');
  }
  
  @Test
  public void testBlueWin() {
      // Set Red's move to 'O' initially for different configurations
      game.setRedMove('O');

      // Row 1
      game.makeMove(0, 0); // Blue move
      assertEquals('R', game.getTurn());

      game.makeMove(0, 1); // Red move
      assertEquals('B', game.getTurn());

      game.makeMove(0, 2); // Blue move, forms SOS horizontally
      assertEquals('B', game.getTurn()); // Blue retains turn after forming SOS

      // Row 2
      game.setBlueMove('O'); // Change Blue's move to 'O' for setup
      game.makeMove(1, 0); // Blue move, no SOS
      assertEquals('R', game.getTurn()); // Turn passes to Red as no SOS was formed

      game.makeMove(1, 1); // Red move
      assertEquals('B', game.getTurn());

      game.setRedMove('S'); // Change Red's move to 'S'
      game.makeMove(1, 2); // Blue move, no SOS
      assertEquals('R', game.getTurn());

      // Row 3
      game.setBlueMove('S'); // Reset Blue's move to 'S'
      game.makeMove(2, 0); // Red move, forms single SOS vertically
      assertEquals('R', game.getTurn()); // Red retains turn due to SOS formation

      game.makeMove(2, 1); // Red move, no SOS
      assertEquals('B', game.getTurn());

      game.makeMove(2, 2); // Blue move, final move filling the board

      // Verify Blue's win after the final move
      assertEquals(SosGame.GameState.BLUE_WON, game.getGameState());
  }

  @Test
  public void testBlueLoss() {
      // Set Red's move to 'O' initially for different configurations
      game.setRedMove('O');

      // Row 1
      game.makeMove(0, 0); // Blue move
      assertEquals('R', game.getTurn());

      game.makeMove(0, 1); // Red move
      assertEquals('B', game.getTurn());

      game.makeMove(0, 2); // Blue move, forms SOS horizontally
      assertEquals('B', game.getTurn()); // Blue retains turn after forming SOS

      // Row 2
      game.setBlueMove('O'); // Change Blue's move to 'O' for setup
      game.makeMove(1, 0); // Blue move, no SOS
      assertEquals('R', game.getTurn()); // Turn passes to Red as no SOS was formed

      game.makeMove(1, 1); // Red move
      assertEquals('B', game.getTurn());

      game.setRedMove('S'); // Change Red's move to 'S'
      game.makeMove(1, 2); // Blue move, no SOS
      assertEquals('R', game.getTurn());

      // Row 3
      game.makeMove(2, 0); // Red move, forms single SOS vertically
      assertEquals('R', game.getTurn()); // Red retains turn due to SOS formation

      game.makeMove(2, 1); // Red move, no SOS
      assertEquals('B', game.getTurn());

      game.makeMove(2, 2); // Blue move, final move filling the board

      // Verify Red's win after the final move by Blue
      assertEquals(SosGame.GameState.RED_WON, game.getGameState());
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
  
}