import java.util.ArrayList;
import java.util.List;

public class PetriNetConf {
  private static final int MAX_TIME = 600000000;
  private static final int[] INITIAL_MARKING = {5, 1, 0, 0, 5, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};

  private static final int[][] INCIDENCE_MATRIX_OUT = { // I+
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, // P0
    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P1
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P2
    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P3
    {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // P4
    {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P5
    {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // P6
    {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, // P7
    {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // P8
    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, // P9
    {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}, // P10
    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // P11
    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // P12
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // P13
    {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0} // P14
  };
  private static final int[][] INCIDENCE_MATRIX_IN = { // I-
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P0
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P1
    {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P2
    {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // P3
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // P4
    {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // P5
    {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // P6
    {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // P7
    {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, // P8
    {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, // P9
    {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}, // P10
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // P11
    {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // P12
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // P13
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1} // P14
  };

  private static final int[][] TIME_TRANSITION_MATRIX = {
    {0, MAX_TIME}, // T0
    {30, MAX_TIME}, // T1
    {0, MAX_TIME}, // T2
    {0, MAX_TIME}, // T3
    {30, MAX_TIME}, // T4
    {30, MAX_TIME}, // T5
    {0, MAX_TIME}, // T6
    {0, MAX_TIME}, // T7
    {30, MAX_TIME}, // T8
    {30, MAX_TIME}, // T9
    {30, MAX_TIME}, // T10
    {0, MAX_TIME}, // T11
  };

  private static final int[][] TRANSITIONS_THREADS = {
    {0, 1}, // Thread 0
    {2, 5}, // Thread 1
    {3, 4}, // Thread 2
    {6, 9, 10}, // Thread 3
    {7, 8}, // Thread 4
    {11} // Thread 5
  };

  private final List<Place> places = new ArrayList<>();
  private final List<Transition> transitions = new ArrayList<>();

  public PetriNetConf() {
    // Initialize places list
    for (int i = 0; i < INITIAL_MARKING.length; i++) {
      places.add(new Place("P" + i, INITIAL_MARKING[i]));
    }
    // Initialize transitions list
    for (int i = 0; i < INCIDENCE_MATRIX_OUT[0].length; i++) {
      transitions.add(
          new Transition(i, TIME_TRANSITION_MATRIX[i][0], TIME_TRANSITION_MATRIX[i][1]));
    }
  }

  // Getters
  public int[] getInitialMarking() {
    return INITIAL_MARKING.clone();
  }

  public int[][] getIncidenceMatrixOut() {
    return INCIDENCE_MATRIX_OUT.clone();
  }

  public int[][] getIncidenceMatrixIn() {
    return INCIDENCE_MATRIX_IN.clone();
  }

  public List<Place> getPlaces() {
    return places;
  }

  public List<Transition> getTransitions() {
    return transitions;
  }

  public List<Transition> getSequence(int index){
    List<Transition> sequence = new ArrayList<>();
    int[] transitionIndex = TRANSITIONS_THREADS[index];
    for (int tIndex: transitionIndex){
      if(tIndex<0 || tIndex >= transitions.size()){
        throw new IllegalArgumentException("Index invalid");
      }
      sequence.add(transitions.get(tIndex));
    }
    return sequence;
  }
  public int getNumbersOfSequence(){
    return TRANSITIONS_THREADS.length;
  }
}
