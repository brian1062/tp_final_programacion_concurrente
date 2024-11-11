



public class Main {
    //static boolean condicion = true;

    public static void main(String[] args){

        PetriNet petriNet = new PetriNet(PetriNetConf.getTransitions(), PetriNetConf.getPlaces(), PetriNetConf.INCIDENCE_MATRIX_OUT, PetriNetConf.INCIDENCE_MATRIX_IN, PetriNetConf.INITIAL_MARKING);
        System.out.println("Initial marking:");
        petriNet.printMarking();

        petriNet.fireTransition(0);
        System.out.println("After firing transition 0:");
        petriNet.printMarking();
        petriNet.fireTransition(1);
        System.out.println("After firing transition 0:");
        petriNet.printMarking();

    }

}