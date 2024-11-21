import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PetriNet {
  private List<Transition> transitions;
  private List<Place> places;
  private List<Transition> enabledTransitions = new ArrayList<>();
  private int countInvariant = 0;
  private boolean invariantArchived = false;
  private final int maxInvariant;
  private int[][] incidenceMatrixOut;
  private int[][] incidenceMatrixIn;
  private int[] marking;
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
      int[] marking,
      int maxInvariant) {
    this.transitions = transitions;
    this.places = places;
    this.incidenceMatrixOut = incidenceMatrixOut;
    this.incidenceMatrixIn = incidenceMatrixIn;
    this.marking = marking;
    this.placesLength = places.size();
    this.maxInvariant = maxInvariant;
    updateEnabledTransitions(); // Initialize the enabled transitions
  }

  /**
   * Fires a transition in the Petri net.
   *
   * @param transitionIndex The index of the transition to fire in the input incidence matriz
   */
  public boolean fireTransition(int transitionIndex) {
    Transition transitionFromIndex = transitions.get(transitionIndex);

    if (!enabledTransitions.contains(transitionFromIndex)) {
      // System.out.printf("Transition %s is not enabled\n", transitionFromIndex.getName());
      return false; // If not enabled, print a message and exit the function
    }

    // Iterate over all places in the Petri net
    IntStream.range(0, places.size())
        .forEach(
            placeIndex -> {
              // If there is an input arc from the place to the transition
              if (incidenceMatrixIn[placeIndex][transitionIndex] > 0) {
                // marking[placeIndex]--; // Remove tokens from the input places
                marking[placeIndex] =
                    marking[placeIndex]
                        - incidenceMatrixIn[placeIndex][
                            transitionIndex]; // Remove tokens from the input places
              }
              // If there is an output arc from the transition to the place
              if (incidenceMatrixOut[placeIndex][transitionIndex] > 0) {
                // marking[placeIndex]++; // Add tokens to the output places
                marking[placeIndex] =
                    marking[placeIndex]
                        + incidenceMatrixOut[placeIndex][
                            transitionIndex]; // Add tokens to the output places
              }
            });
    // TODO:Descomentar y agregar logica
    // if(transitionIndex==11){
    //   countInvariant++;
    //   if(countInvariant>=maxInvariant){
    //     invariantArchived = true;
    //   }
    // }

    // Update the enabled transitions after firing the transition
    updateEnabledTransitions();
    return true;
  }

  /** Prints the current marking of the Petri net. */
  public String printMarking() {
    String markingString =
        IntStream.range(0, marking.length)
            .mapToObj(placeIndex -> String.valueOf(marking[placeIndex]))
            .collect(Collectors.joining(" "));

    // Print marking string to the console
    // System.out.println(markingString);
    return markingString;
  }

  /** Updates the list of enabled transitions in the Petri net. */
  private void updateEnabledTransitions() {
    // Clear the enabledTransitions list to remove any previously stored transitions
    enabledTransitions.clear();

    // Iterate over all transitions in the incidence matrix
    IntStream.range(0, incidenceMatrixIn[0].length)
        .filter(
            transitionIndex ->
                IntStream.range(0, placesLength)
                    .allMatch(
                        placeIndex ->
                            marking[placeIndex] >= incidenceMatrixIn[placeIndex][transitionIndex]))
        .mapToObj(transitions::get)
        .forEach(enabledTransitions::add);
  }

  /** Prints the names of the currently enabled transitions in the Petri net. */
  public void printEnabledTransitions() {
    // Iterate over the enabledTransitions list and print each transition's name
    enabledTransitions.stream().map(Transition::getName).forEach(System.out::println);
  }

  // Getters
  public int[] getMarking() {
    return marking;
  }

  public List<Transition> getEnabledTransitions() {
    return enabledTransitions;
  }

  public boolean invariantArchived() {
    return invariantArchived; // todo: protect this becauso many threads can access auque es para
                              // lectura
  }
}
