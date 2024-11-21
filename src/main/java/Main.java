public class Main {
  // static boolean condicion = true;

  public static void main(String[] args) {

    PetriNetConf rdPConf = new PetriNetConf();

    PetriNet petriNet =
        new PetriNet(
            rdPConf.getTransitions(),
            rdPConf.getPlaces(),
            rdPConf.getIncidenceMatrixOut(),
            rdPConf.getIncidenceMatrixIn(),
            rdPConf.getInitialMarking(),
            186);

    // Initialize threads
    Thread[] threads = new Thread[rdPConf.getNumbersOfSequence()];
    Monitor monitor = Monitor.getMonitor(petriNet);
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Segments(rdPConf.getSequence(i), monitor));
    }
    for (int i = 0; i < threads.length; i++) {
      threads[i].start();
    }
  }
}
