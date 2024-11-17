import java.util.ArrayList;
import java.util.List;

public class PetriNet {
    private List<Transition>    transitions         ;
    private List<Place>         places              ;
    private List<Transition>    enabledTransitions  ;
    private int[][]             incidenceMatrixOut  ;
    private int[][]             incidenceMatrixIn   ;
    private int[]               marking             ;
    private final int           placesLength        ;

    // PolicyFactory policyFactory = new PolicyFactory(new PolicyBalancedType());
    // PetriNet petriNet; 

    public PetriNet(List<Transition> transitions, List<Place> places, int[][] incidenceMatrixOut, int[][] incidenceMatrixIn, int[] marking) {
        this.transitions        = transitions       ;
        this.places             = places            ;
        this.incidenceMatrixOut = incidenceMatrixOut;
        this.incidenceMatrixIn  = incidenceMatrixIn ;
        this.marking            = marking           ;
        enabledTransitions      = new ArrayList<>() ;
        this.placesLength       = places.size()     ;
        updateEnabledTransitions();

    }


    public void fireTransition(int transitionIndex) {
        if (!isTransitionEnabled(transitionIndex)) {
            System.out.println("Transition is not enabled");
            return;
        }

        for (int placeIndex = 0; placeIndex < places.size(); placeIndex++) {
            if (incidenceMatrixIn[placeIndex][transitionIndex] == 1) {
                marking[placeIndex]--; // Remove tokens from the input places
            } 
            if (incidenceMatrixOut[placeIndex][transitionIndex] == 1) {
                marking[placeIndex]++; // Add tokens in the output places
            }
        }

        updateEnabledTransitions();
    }

    public boolean isTransitionEnabled(int transitionIndex) {
        for (int i = 0; i < places.size(); i++) {
            if (incidenceMatrixIn[i][transitionIndex] == 1 && marking[i] == 0) { 
                return false;
            }
        }
        return true;
    }


    public void printMarking() {
        String markingString = "";
        for (int i = 0; i < marking.length; i++) {
            markingString += marking[i] + " ";
        }
        System.out.println(markingString);
    }

    private void updateEnabledTransitions(){

        // Clear enabledTransitions list
        enabledTransitions.clear();
        // Initialize enabledTransitions list
        for (int i= 0; i<incidenceMatrixIn[0].length; i++){
            boolean enabled = true;
            for (int j= 0; j<placesLength; j++){
            // Si el lugar tiene más tokens de los necesarios para disparar la transición
                if (incidenceMatrixIn[j][i] > 0 && marking[j] < incidenceMatrixIn[j][i]) {
                    enabled = false;  // La transición no está habilitada si algún lugar no tiene suficientes tokens
                    break;
                }
            }

            if (enabled) {
                enabledTransitions.add(transitions.get(i));
            }

        }
    }

    public void  printEnabledTransitions(){
        for (Transition t : enabledTransitions) {
            System.out.println(t.getName());
        }
    }

    public int[] getMarking(){
        return marking;
    }

    public List<Transition> getEnabledTransitions(){
        return enabledTransitions;
    }

}


