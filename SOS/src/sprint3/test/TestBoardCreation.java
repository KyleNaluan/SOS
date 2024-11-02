package sprint3.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import sprint3.product.*;

public class TestBoardCreation {

  private SosGame game = new SimpleGame();
  
  @Before
  public void setUp() throws Exception {
  }
  
  @After
  public void tearDown() throws Exception {
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testBoardSizeBelowRangeFails() {
    game.setBoardSize(2);
  }
  
  @Test
  public void setBoardSizeToThreeSucceeds() {
    game.setBoardSize(3);
    
    assertEquals(game.getTotalRows(), 3);
  }
  
  @Test
  public void setBoardSizeToFiveSucceeds() {
    game.setBoardSize(5);
    
    assertEquals(game.getTotalRows(), 5);
  }
  
  @Test
  public void setBoardSizeToTenSucceeds() {
    game.setBoardSize(10);
    
    assertEquals(game.getTotalRows(), 10);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testBoardSizeAboveRangeFails() {
    game.setBoardSize(11);
  }
  
  @Test
  public void testSimpleGameSelection() {
    game = new SimpleGame();
    
    assertTrue(game instanceof SimpleGame);
  }
  
  @Test
  public void testGeneralGameSelection() {
    game = new GeneralGame();
    
    assertTrue(game instanceof GeneralGame);
  }
   
  @Test
  public void successfulSimpleGameCreation(){
    game = new SimpleGame();
    
    game.setBoardSize(5);
    
    game.startGame();
    
    assertEquals(game.getGameState(), SosGame.GameState.PLAYING);
    assertTrue(game instanceof SimpleGame);
  }
  
  @Test
  public void successfulGeneralGameCreation(){
    game = new GeneralGame();
    
    game.setBoardSize(5);
    
    game.startGame();
    
    assertEquals(game.getGameState(), SosGame.GameState.PLAYING);
    assertTrue(game instanceof GeneralGame);
  }
  
  @Test (expected = RuntimeException.class)
  public void unsuccessfulGameCreationFromMissingSize(){    
    game.startGame();
  }

}
