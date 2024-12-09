import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Monitor class for managing synchronized interactions with a Petri Net. Ensures only one instance
 * of Monitor is created using the Singleton pattern.
 */
class Monitor implements MonitorInterface {

  // Singleton instance of the Monitor
  private static Monitor monitor = null;
  boolean isFireSuccessful = false;
  PetriNet petriNet; // The associated Petri Net instance
  private final String LOG_PATH = "/tmp/petriNetResults.txt";

  private boolean end = false;
  private final Semaphore mutex; // Mutex to ensure thread safety
  private final Semaphore waitSem;
  private final HashMap<Transition,Semaphore> transitionsMap;

  /**
   * Private constructor to enforce Singleton pattern.
   *
   * @param petriNet the PetriNet instance to control.
   */
  private Monitor(PetriNet petriNet) {
    this.mutex = new Semaphore(1, true);
    this.waitSem = new Semaphore(1, true);
    this.petriNet = petriNet;
    this.transitionsMap = new HashMap<>();
    for (Transition transition : petriNet.getTransitionList()){
      this.transitionsMap.put(transition, new Semaphore(0));
    }
  }

  /**
   * Returns the singleton instance of the Monitor.
   *
   * @param petriNet the PetriNet instance to associate with the Monitor.
   * @return the Monitor instance.
   */
  public static Monitor getMonitor(PetriNet petriNet) {
    if (monitor == null) {
      monitor = new Monitor(petriNet);
    }
    return monitor;
  }

  /**
   * Attempts to fire a transition in the associated Petri Net.
   *
   * @param transitionIndex the index of the transition to fire.
   * @return true if the transition was successfully fired at least once, false otherwise.
   */
  @Override
  public boolean fireTransition(Transition transition) {
    try {
      mutex.acquire();

      if(petriNetHasFinished() || end){
        mutex.release();
        transitionsMap.get(transition).acquire();
      }


      while(true){
        if(!petriNet.getEnabledTransitions().contains(transition)){
          // System.out.println("transition not ready: " + transition.getName());
          mutex.release();
          transitionsMap.get(transition).acquire();
          continue;
        }
        if (transition.getTime() > 0) {
          if (waitSem.availablePermits() == 1){
             waitSem.acquire();

            mutex.release();
            Transition trans = petriNet.getTransitionPerIndex(transition.getNumber());
            if(trans.getRunningTime()==0){
              // System.out.println("transition here: " + transition.getName());
              trans.sensitizeTime();
            }
            // isFireSuccessful = petriNet.tryFireTransition(transition.getNumber());
            Thread.sleep(transition.getTime());
            mutex.acquire();
            waitSem.release();
            if(transition.getRemainingTime()<=0){
              break;
            }
          }
          
        }else{break;}//can fire transition
      }


      isFireSuccessful = petriNet.tryFireTransition(transition.getNumber());
      if (isFireSuccessful) {
        // Print Transition fire and log it!!
        String outputMessage =
            "Transition fired: {T"
                + transition.getNumber()
                + "}"
                + " Marking: {"
                + petriNet.getStringMarking()
                + "}";
        System.out.println(outputMessage);
        String timestamp = LocalDateTime.now().toString();
        writeLog(timestamp + ": " + outputMessage);
      }

      if(!petriNet.getEnabledTransitions().isEmpty()){
        Transition next_t = petriNet.getEnabledTransitions().get(0);//here put the policy logic for select transition
        transitionsMap.get(next_t).release();
      }

      if(petriNetHasFinished()){
        System.exit(0);
      }

  } catch (Exception e) {
    e.printStackTrace();
 }
    mutex.release();
    return true;
  }

  /**
   * Checks if the Petri Net has achieved its invariants target.
   *
   * @return true if the invariants target is achieved, false otherwise.
   */
  public boolean petriNetHasFinished() {
    return petriNet.invariantsTargetAchieved();
  }

  /**
   * Writes a message to the log file.
   *
   * @param message the message to write to the log file.
   */
  private void writeLog(String message) {
    try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
      writer.write(message + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

/** Interface for Monitor functionality. */
interface MonitorInterface {
  boolean fireTransition(Transition transition);
}
