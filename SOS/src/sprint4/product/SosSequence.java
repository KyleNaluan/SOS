package sprint4.product;

public class SosSequence {
  
  // The coordinates of the two Ss in the SOS sequence
  // Represent the start and end of the SOS sequence
  // Only need the two Ss, the direction can be determined from these coordinates
  private int r1;
  private int c1;
  private int r2;
  private int c2;
  private char player;
  
  public SosSequence (int r1, int c1, int r2, int c2, char player) {
    this.r1 = r1;
    this.c1 = c1;
    this.r2 = r2;
    this.c2 = c2;
    this.player = player;
  }
  
  public int getR1() {
    return r1;
  }
  
  public int getC1() {
    return c1;
  }
  
  public int getR2() {
    return r2;
  }
  
  public int getC2() {
    return c2;
  }
  
  public char getPlayer() {
    return player;
  }
  
}