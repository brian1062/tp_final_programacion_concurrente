import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Segments implements Runnable {
  private final List<Transition> sequence;
  private final Monitor monitor;

  public Segments(List<Transition> sequence, Monitor monitor) {
    this.sequence = sequence;
    this.monitor = monitor;
  }

  @Override
  public void run() {
    AtomicBoolean isRunning = new AtomicBoolean(true);

    while (isRunning.get()) {
      for (Transition t : sequence) {
        monitor.fireTransition(t.getNumber());

        if (monitor.petriNetHasFinished()) {
          System.out.println("Petri net has finished. Exiting thread.");
          System.out.println("Este es el mensaje del hilo: " + Thread.currentThread().getName());
          isRunning.set(false);
          break;
        }
      }
    }
    System.out.println("Este es el mensaje del hilo: " + Thread.currentThread().getName());
  }
}
