public class Transition {
  private String name;
  private int delayTime;
  private int maxTime;
  private boolean isImmediate;

  public Transition(String name,int delayTime,int maxTime) {
    this.name = name;
    this.delayTime = delayTime; 
    this.maxTime = maxTime;
    this.isImmediate = delayTime==0;
  }

  public String getName() {
    return name;
  }

  public int getTime() {
    return delayTime;
  }

  //todo: remove unused
  public void setTime(int delayTime) { 
    if (delayTime < 0) {
      throw new IllegalArgumentException("Time cannot be negative");
    }
    this.delayTime = delayTime;
  }

  public boolean isImmediate(){
    return isImmediate;
  }
}
