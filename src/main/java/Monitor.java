import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

class Monitor implements MonitorInterface {
  private static Monitor monitor= null;
  boolean succesFire=false;
  // Policy policyQueue;
  // Policy policy;
  PetriNet petriNet;

  private final Semaphore mutex;

  AtomicBoolean isRunning = new AtomicBoolean(true);

  //
  public static Monitor getMonitor(PetriNet petriNet){
    if(monitor==null){
      monitor = new Monitor(petriNet);
    }
    return monitor;
  }


  private Monitor(PetriNet petriNet) {
    this.mutex = new Semaphore(1,true);
    // this.policyQueue = policyQueue;
    // this.policy = policy;
    this.petriNet = petriNet;
  }

  @Override
  public boolean fireTransition(int transitionIndex) {
    try {
      mutex.acquire();
    } catch (Exception e) {
    }
    succesFire=true;
    while(succesFire){
      succesFire = petriNet.fireTransition(transitionIndex);
      if(succesFire){
        System.out.println("Transition fire: " +transitionIndex+ " Marcado: "+petriNet.printMarking());

      }
      else{
        mutex.release();
      }
    }
    mutex.release();
    // System.out.println("Current thread: " + Thread.currentThread().getName()+ " Transition: " + transitionIndex);
    return false;
  }

}

interface MonitorInterface {
  boolean fireTransition(int transition);
}
