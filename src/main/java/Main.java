public class Main {
    //static boolean condicion = true;

    public static void main(String[] args){

        PetriNetConf rdPConf= new PetriNetConf();

        PetriNet petriNet = new PetriNet(rdPConf.getTransitions(), rdPConf.getPlaces(), rdPConf.getIncidenceMatrixOut(), rdPConf.getIncidenceMatrixIn(), rdPConf.getInitialMarking());
        System.out.println("Initial marking:");
        petriNet.printMarking();
        petriNet.printEnabledTransitions();

        petriNet.fireTransition(0);
        System.out.println("After firing transition 0:");
        petriNet.printMarking();
        petriNet.printEnabledTransitions();


        petriNet.fireTransition(1);
        System.out.println("After firing transition 1:");
        petriNet.printMarking();
        petriNet.printEnabledTransitions();

        petriNet.fireTransition(3);
        System.out.println("After firing transition 3:");
        petriNet.printMarking();
        petriNet.printEnabledTransitions();
        
        petriNet.fireTransition(3);        
        System.out.println("After firing transition 3:");
        petriNet.printMarking();
        petriNet.printEnabledTransitions();
    }

}