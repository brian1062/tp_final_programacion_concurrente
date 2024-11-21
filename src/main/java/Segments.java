import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Segments implements Runnable {
  private final List<Transition> transitions;
  private final Monitor monitor;

  public Segments(List<Transition> transitions, Monitor monitor) {
    this.transitions = transitions;
    this.monitor = monitor;
  }

  @Override
  public void run() {
    AtomicBoolean isRunning = new AtomicBoolean(true);

    while (isRunning.get()) {
      for (Transition t : transitions) {
        monitor.fireTransition(t.getName());
      }
    }
  }
}
