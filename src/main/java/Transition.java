public class Transition {
  private String name;
  private int number;
  private int delayTime;
  private int maxTime;
  private boolean isImmediate;

  public Transition(int number, int delayTime, int maxTime) {
    this.number = number;
    this.name = "T" + number;

    this.delayTime = delayTime;
    this.maxTime = maxTime;
    this.isImmediate = delayTime == 0;
  }

  // TODO: remove (unused?)
  public void setTime(int delayTime) {
    if (delayTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    this.delayTime = delayTime;
  }

  public boolean isImmediate() {
    return isImmediate;
  }

  // Getters
  public int getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public int getTime() {
    return delayTime;
  }

  public int getMaxTime() {
    return maxTime;
  }
}
