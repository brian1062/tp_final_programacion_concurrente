
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetriNetTest {

    private PetriNet petriNet;
    private static int[] INITIAL_MARKING ={5, 1, 0, 0, 5, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};

    @BeforeEach
    public void setUp() {
        PetriNetConf conf = new PetriNetConf();
        petriNet = new PetriNet(
                conf.getTransitions(),
                conf.getPlaces(),
                conf.getIncidenceMatrixOut(),
                conf.getIncidenceMatrixIn(),
                conf.getInitialMarking()
        );
    }

    @Test
    public void testInitialMarking() {
        assertArrayEquals(INITIAL_MARKING, petriNet.getMarking());
    }
    @Test
    public void testEnabledTransitions() {
        Transition expectedTransition = new Transition("T0");
        assertEquals(expectedTransition.getName(), petriNet.getEnabledTransitions().get(0).getName());
    }

    @Test
    public void testFailFireTransitions() {
        petriNet.fireTransition(1);
        assertArrayEquals(INITIAL_MARKING, petriNet.getMarking());
    }

    @Test
    public void testFireTransitions() {
        petriNet.fireTransition(0);
        int[] newMarking = {4, 0, 1, 0, 4, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0};
        assertArrayEquals(newMarking, petriNet.getMarking());
        newMarking[1] =1;
        newMarking[2] =0;
        newMarking[3] =1;
        petriNet.fireTransition(1);
        assertArrayEquals(newMarking, petriNet.getMarking());
    }

}
