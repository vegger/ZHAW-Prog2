/*
 * Test Class for Heart
 */
package ch.zhaw.prog2.heartbeat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.zhaw.prog2.heartbeat.parts.Valve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import ch.zhaw.prog2.heartbeat.Heart.State;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HeartTest {
    @Mock Half mockedRightHalf;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This is a very simple test to check if Junit and Mockito are properly set up.
     */
    @Test
    void testTheTest() {
        Heart classUnderTest = new Heart();
        assertNotNull(classUnderTest.getState(), "The heart must have a state.");
    }

    /**
     * Tests a single heartbeat
     */
    @Test
    void testHeartBeat() throws Heart.HeartBeatDysfunctionException {
        Heart heart = new Heart();
        State startState = heart.getState();

        heart.executeHeartBeat();

        // after one heartbeat, the heart must be in the same state as before
        assertEquals(startState, heart.getState());
    }

    /**
     * Tests if the valves are open or closed depending on the status of the heart
     */
    @Test
    void testValveStatus() throws Heart.HeartBeatDysfunctionException {
        Heart heart = new Heart();

        heart.executeHeartBeat();

        State state = heart.getState();

        if (state.equals(Heart.State.DIASTOLE)) {
            for (Half half : heart.getHalves()) {
                assertFalse(half.isAtrioventricularValveOpen());
                assertTrue(half.isSemilunarValveOpen());
            }
        } else if ((state.equals(Heart.State.SYSTOLE))) {
            for (Half half : heart.getHalves()) {
                assertTrue(half.isAtrioventricularValveOpen());
                assertFalse(half.isSemilunarValveOpen());
            }
        }
    }

    /**
     * Tests if the hart throws the appropriate Exception, when malfunction was detected during hartBeat
     */
    @Test
    void testExecuteHeartBeatErrorBehaviour() {
        Heart heart = new Heart();
        // prepare error situation due to wrong initialization
        heart.setState(State.SYSTOLE);

        assertThrows(Heart.HeartBeatDysfunctionException .class, // verification using lambda
            () -> heart.executeHeartBeat());
    }

    /**
     * Tests if the hart throws the appropriate Exception, when malfunction was detected during hartBeat
     * with exception Stubbing
     */
    @Test
    void testExecuteHartBeatErrorBehaviourWithStubbing() throws Valve.IllegalValveStateException {
        Half mockedLeftHalf = mock(Half.class);
        Heart heart = new Heart(mockedLeftHalf, mockedRightHalf);
        // prepare error situation due to wrong initialization
        heart.setState(State.SYSTOLE);

        doThrow(Valve.IllegalValveStateException.class).when(mockedLeftHalf).openAtrioventricularValve();
        /**
         * Folgendes funktioniert nicht weil die Methode openAtrioventricularValve return void ist
         * when(mockedLeftHalf).openAtrioventricularValve().thenThrow(Valve.IllegalValveStateException.class);
         */

        assertThrows(Heart.HeartBeatDysfunctionException .class, // verification using lambda
            () -> heart.executeHeartBeat());
    }

    @Test
    void testDiastoleException() {
        Half mockedLeftHalf = mock(Half.class);
        Heart heart = new Heart(mockedLeftHalf, mockedRightHalf);
        heart.setState(State.SYSTOLE);

        assertThrows(Valve.InvalidValvePositionException.class, // verification using lambda
            () -> heart.executeDiastole());
    }

    @Test
    void testSystoleException() {
        Half mockedLeftHalf = mock(Half.class);
        Heart heart = new Heart(mockedLeftHalf, mockedRightHalf);
        heart.setState(State.DIASTOLE);

        assertThrows(Valve.InvalidValvePositionException.class, // verification using lambda
            () -> heart.executeSystole());
    }

    /**
     * We test if Heart::executeHeartbeat() sends the right signals to both of its
     * halves.
     *
     * When Half::contractVentricle() is called, Half::closeAtrioventricularValve()
     * and Half::openSemilunarValve() must have been called earlier.
     *
     */
    @Test
    void testValvesBehavior() throws Exception {
        Half mockedLeftHalf = mock(Half.class);
        InOrder inOrderLeft = inOrder(mockedLeftHalf);
        InOrder inOrderRight = inOrder(mockedRightHalf);
        Heart heart = new Heart(mockedLeftHalf, mockedRightHalf);

        heart.setState(State.DIASTOLE);
        heart.executeHeartBeat();

        inOrderLeft.verify(mockedLeftHalf).openSemilunarValve();
        inOrderLeft.verify(mockedLeftHalf).closeAtrioventricularValve();
        inOrderLeft.verify(mockedLeftHalf).contractVentricle();

        inOrderRight.verify(mockedRightHalf).openSemilunarValve();
        inOrderRight.verify(mockedRightHalf).closeAtrioventricularValve();
        inOrderRight.verify(mockedRightHalf).contractVentricle();

        inOrderLeft.verifyNoMoreInteractions();
        inOrderRight.verifyNoMoreInteractions();
    }

}
