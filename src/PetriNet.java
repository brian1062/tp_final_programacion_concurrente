
import java.util.List;

public class PetriNet {
    private List<Transition>    transitions         ;
    private List<Place>         places              ;
    private int[][]             incidenceMatrixOut  ;
    private int[][]             incidenceMatrixIn   ;
    private int[]               marking             ;

    // PolicyFactory policyFactory = new PolicyFactory(new PolicyBalancedType());
    // PetriNet petriNet; 

    public PetriNet(List<Transition> transitions, List<Place> places, int[][] incidenceMatrixOut, int[][] incidenceMatrixIn, int[] marking) {
        this.transitions        = transitions       ;
        this.places             = places            ;
        this.incidenceMatrixOut = incidenceMatrixOut;
        this.incidenceMatrixIn  = incidenceMatrixIn ;
        this.marking            = marking           ;
    }

    public void printMarking() {
        for (int i = 0; i < marking.length; i++) {
            System.out.println("Place " + i + " has " + marking[i] + " tokens");
        }
    }

    public void fireTransition(int transitionIndex) {

        for (int i = 0; i < places.size(); i++) {
            if (incidenceMatrixIn[i][transitionIndex] == 1) {
                marking[i]--; // Remove tokens from the input places
            }
            if (incidenceMatrixOut[i][transitionIndex] == 1) {
                marking[i]++; // Add tokens in the output places
            }
    }



}
}

