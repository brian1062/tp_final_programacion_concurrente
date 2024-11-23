import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

class Monitor implements MonitorInterface {
  private static Monitor monitor = null;
  boolean isFireSuccessful = false;
  // Policy policyQueue;
  // Policy policy;
  PetriNet petriNet;

  private final Semaphore mutex;

  AtomicBoolean isRunning = new AtomicBoolean(true);

  // Using Singleton pattern to ensure that only one instance of Monitor is created
  public static Monitor getMonitor(PetriNet petriNet) {
    if (monitor == null) {
      monitor = new Monitor(petriNet);
    }
    return monitor;
  }

  private Monitor(PetriNet petriNet) {
    this.mutex = new Semaphore(1, true);
    // this.policyQueue = policyQueue;
    // this.policy = policy;
    this.petriNet = petriNet;
  }

  @Override
  public boolean fireTransition(int transitionIndex) {
    try {
      mutex.acquire();
    } catch (Exception e) {
      e.printStackTrace();
    }
    isFireSuccessful = true;
    while (isFireSuccessful) {
      isFireSuccessful = petriNet.fireTransition(transitionIndex);
      if (isFireSuccessful) {
        System.out.println(
            "Transition fired: " + transitionIndex + " Marking: " + petriNet.printMarking());
      }
    }
    mutex.release();
    return false;
  }

  public boolean petriNetIsShutdown() {
    return petriNet.invariantsTargetAchieved();
  }
}

interface MonitorInterface {
  boolean fireTransition(int transition);
}
