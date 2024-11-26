package sprint5.product;

public class Player {
  
  protected String name;
  protected char move;
  protected int sosCount = 0;
  
  public Player(String name, char initialMove) {
    this.name = name;
    move = initialMove;
  }
  
  
  public String getName() {
    return name;
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
  
  public void setSosCount(int count) {
    sosCount = count;
  }
  
  public void increaseSosCount(int n) {
    sosCount += n;
  }
  
  public void resetSosCount() {
    sosCount = 0;
  }
}