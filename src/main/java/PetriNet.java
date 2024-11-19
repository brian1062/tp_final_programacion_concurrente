import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class PetriNet {
  private List<Transition> transitions;
  private List<Place> places;
  private List<Transition> enabledTransitions = new ArrayList<>();
  private int[][] incidenceMatrixOut;
  private int[][] incidenceMatrixIn;
  private int[] marking;
  // private int[]               initialMarking      ; will be needed to check transition invariants
  private final int placesLength;

  // PolicyFactory policyFactory = new PolicyFactory(new PolicyBalancedType());
  // PetriNet petriNet;

  /**
   * Constructor for the PetriNet class.
   *
   * @param transitions
   * @param places
   * @param incidenceMatrixOut
   * @param incidenceMatrixIn
   * @param marking
   */
  public PetriNet(
      List<Transition> transitions,
      List<Place> places,
      int[][] incidenceMatrixOut,
      int[][] incidenceMatrixIn,
      int[] marking) {
    this.transitions = transitions;
    this.places = places;
    this.incidenceMatrixOut = incidenceMatrixOut;
    this.incidenceMatrixIn = incidenceMatrixIn;
    this.marking = marking;
    // this.initialMarking     = marking.clone()   ;
    this.placesLength = places.size();
    updateEnabledTransitions(); // Initialize the enabled transitions
  }

  /**
   * Fires a transition in the Petri net.
   *
   * @param transitionIndex The index of the transition to fire in the input incidence matriz
   */
  public void fireTransition(int transitionIndex) {
    Transition transitionFromIndex = transitions.get(transitionIndex);

    if (!enabledTransitions.contains(transitionFromIndex)) {
      System.out.printf("Transition %s is not enabled\n", transitionFromIndex.getName());
      return; // If not enabled, print a message and exit the function
    }

    // Iterate over all places in the Petri net
    IntStream.range(0, places.size()).forEach(placeIndex -> {
        // If there is an input arc from the place to the transition
        if (incidenceMatrixIn[placeIndex][transitionIndex] > 0) {
          // marking[placeIndex]--; // Remove tokens from the input places  
          marking[placeIndex] = marking[placeIndex] - incidenceMatrixIn[placeIndex][transitionIndex]; // Remove tokens from the input places
        }
        // If there is an output arc from the transition to the place
        if (incidenceMatrixOut[placeIndex][transitionIndex] > 0) {
            //marking[placeIndex]++; // Add tokens to the output places
            marking[placeIndex] = marking[placeIndex] + incidenceMatrixOut[placeIndex][transitionIndex]; // Add tokens to the output places
        }
    });

    // Update the enabled transitions after firing the transition
    updateEnabledTransitions();
  }

  /**
   * Checks if a transition is enabled in the Petri net by checking if all input places have enough
   * tokens.
   *
   * @param transitionIndex The index of the transition to check in the input incidence matrix
   * @return true if the transition is enabled, false otherwise
   */
  // public boolean isTransitionEnabled(int transitionIndex) {
  //  for (int placeIndex = 0; placeIndex < places.size(); placeIndex++) {
  //    // Verifies that the marking in the place is at least equal to the value in the input
  //    // incidence matrix
  //    if (marking[placeIndex] < incidenceMatrixIn[placeIndex][transitionIndex]) {
  //      return false;
  //    }
  //  }
  //  return true;
  // } // TODO: borrar. No es necesario, se puede utilizar la lista de enabledTransitions

  /** Prints the current marking of the Petri net. */
  public void printMarking() {
    String markingString = IntStream.range(0, marking.length)
                                    .mapToObj(placeIndex -> String.valueOf(marking[placeIndex]))
                                    .collect(Collectors.joining(" "));

    // Print marking string to the console
    System.out.println(markingString);
  }

  /** Updates the list of enabled transitions in the Petri net. */
  private void updateEnabledTransitions() {
    // Clear the enabledTransitions list to remove any previously stored transitions
    enabledTransitions.clear();

    // Iterate over all transitions in the incidence matrix using Streams
    IntStream.range(0, incidenceMatrixIn[0].length)
             .filter(transitionIndex -> 
                 IntStream.range(0, placesLength)
                          .allMatch(placeIndex -> marking[placeIndex] >= incidenceMatrixIn[placeIndex][transitionIndex])
             )
             .mapToObj(transitions::get)
             .forEach(enabledTransitions::add);
  }

  /** Prints the names of the currently enabled transitions in the Petri net. */
  public void printEnabledTransitions() {
    // Iterate over the enabledTransitions list and print each transition's name
    enabledTransitions.stream()
                      .map(Transition::getName)
                      .forEach(System.out::println);
  }

  // Getters
  public int[] getMarking() {
    return marking;
  }

  public List<Transition> getEnabledTransitions() {
    return enabledTransitions;
  }
}