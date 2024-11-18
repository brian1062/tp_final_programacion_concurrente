import  java.util.ArrayList;
import  java.util.List;

public class PetriNetConf {
    private static final int[] INITIAL_MARKING = {5, 1, 0, 0, 5, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};

    private static final int[][] INCIDENCE_MATRIX_OUT = {  //I+
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},  //P0
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P1
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P2
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P3
        {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},  //P4
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P5
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},  //P6
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},  //P7
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},  //P8
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},  //P9
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0},  //P10
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},  //P11
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},  //P12
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},  //P13
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}   //P14
    };
    private static final int[][] INCIDENCE_MATRIX_IN = {  //I-
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P0
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P1
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P2
        {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},  //P3
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  //P4
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},  //P5
        {0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},  //P6
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},  //P7
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},  //P8
        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},  //P9
        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},  //P10
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},  //P11
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},  //P12
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},  //P13
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}   //P14
    };

    private final List<Place> places = new ArrayList<>();
    private final List<Transition> transitions= new ArrayList<>();


    public PetriNetConf(){
        // Initialize places list
        for (int i= 0; i<INITIAL_MARKING.length; i++){
            places.add(new Place("P"+i,INITIAL_MARKING[i]));
        }
        // Initialize transitions list
        for (int i= 0; i<INCIDENCE_MATRIX_OUT[0].length; i++){
            transitions.add(new Transition("T"+i));
        }
        //TODO: set time for transitions
    }

    // Getters
    public int[] getInitialMarking() { return INITIAL_MARKING.clone(); }

    public int[][] getIncidenceMatrixOut(){ return INCIDENCE_MATRIX_OUT.clone(); }

    public int[][] getIncidenceMatrixIn(){ return INCIDENCE_MATRIX_IN.clone(); }

    public List<Place> getPlaces(){ return places; }

    public List<Transition> getTransitions(){ return transitions; }

}
