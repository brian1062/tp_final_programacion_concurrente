public class Transition {
  private String name;
  private int time;

  public Transition(String name) {
    this.name = name;
    time = 0; // Default time is 0
  }

  public String getName() {
    return name;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    if (time < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    this.time = time;
  }
}
