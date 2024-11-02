package sprint3.product;

public class Player {
  
  protected char move;
  protected int sosCount = 0;
  
  public Player(char initialMove) {
    move = initialMove;
  }
  
  public char getMove() {
    return move;
  }
  
  public int getSosCount() {
    return sosCount;
  }
  
  public void setMove(char m) {
    move = m;
  }
  
  public void increaseSosCount(int n) {
    sosCount += n;
  }
  
  public void resetSosCount() {
    sosCount = 0;
  }
}