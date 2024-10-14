package sprint2.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import sprint2.product.*;

public class TestBoardCreation {

  private SosGame game = new SosGame();
  
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
    game.setBoardSize(2);
  }
  
  @Test
  public void testSimpleGameSelection(){
    game.setGameMode(SosGame.GameMode.SIMPLE);
    
    assertEquals(game.getGameMode(), SosGame.GameMode.SIMPLE);
  }
  
  @Test
  public void testGeneralGameSelection(){
    game.setGameMode(SosGame.GameMode.GENERAL);
    
    assertEquals(game.getGameMode(), SosGame.GameMode.GENERAL);
  }
  
  @Test
  public void successfullGameCreation() {
    game.setBoardSize(5);
    game.setGameMode(SosGame.GameMode.SIMPLE);
    
    game.startGame();
    
    boolean gamePlaying = game.getGameState() == SosGame.GameState.PLAYING;
    boolean correctBoardSize = game.getTotalRows() == 5;
    boolean correctGameMode = game.getGameMode() == SosGame.GameMode.SIMPLE;
    boolean playerTurn = game.getTurn() == 'B';
    
    assertTrue(gamePlaying && correctBoardSize && correctGameMode && playerTurn);
  }
  
  @Test (expected = RuntimeException.class)
  public void unsuccessfulGameCreationFromMissingGameMode(){
    game.setBoardSize(5);
    
    game.startGame();
  }
  
  @Test (expected = RuntimeException.class)
  public void unsuccessfulGameCreationFromMissingSize(){
    game.setGameMode(SosGame.GameMode.SIMPLE);
    
    game.startGame();
  }

}
