import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentProcessorShould {

    @Mock
    private TaskDispatcher taskDispatcher;
    private InstrumentProcessor instrumentProcessor;

//   XXXXXXX 1- When the method Process is called then the InstrumentProcessor gets the next task from the
//    task dispatcher and executes it on the instrument.
//    2- When the Execute method of the instrument throws an exception then this exception is passed
//    on to the caller of the Process method.
//    3- When the instrument fires the finished event then the InstrumentProcessor calls the task
//    dispatcher’s FinishedTask method with the correct task.
//    4- When the instrument fires the Error event then the InstrumentProcessor writes the string
//    “Error occurred” to the console.

    @Before
    public void setUp() {
        this.instrumentProcessor = new InstrumentProcessor(taskDispatcher);
    }

    @Test
    public void get_a_task_from_the_task_dispatcher() {
        given(taskDispatcher.getTask()).willReturn("nextTaskToExecute");

        instrumentProcessor.process();

        Mockito.verify(taskDispatcher, Mockito.times(1)).getTask();
    }
}
