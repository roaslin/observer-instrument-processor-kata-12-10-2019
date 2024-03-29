import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentProcessorShould {

    private static final String TASK = "task";
    @Mock
    private TaskDispatcher taskDispatcher;

    private Instrument instrument;
    private InstrumentProcessor instrumentProcessor;

//   1- When the method Process is called then the InstrumentProcessor gets the next task from the
//    task dispatcher  and executes it on the instrument.
//    2- When the Execute method of the instrument throws an exception then this exception is passed
//    on to the caller of the Process method.
//   TODO
//    3- When the instrument fires the finished event then the InstrumentProcessor calls the task
//    dispatcher’s FinishedTask method with the correct task.
//    4- When the instrument fires the Error event then the InstrumentProcessor writes the string
//    “Error occurred” to the console.

    @Before
    public void setUp() {
        this.instrument = new TestableInstrument();
        this.instrumentProcessor = new InstrumentProcessor(taskDispatcher, instrument);
        ((TestableInstrument) instrument).addProcessor(instrumentProcessor);
    }

    @Test
    public void get_a_task_from_the_task_dispatcher() {
        given(taskDispatcher.getTask()).willReturn(TASK);

        instrumentProcessor.process();

        verify(taskDispatcher, times(1)).getTask();
    }

    @Test
    public void execute_a_task() {
        given(taskDispatcher.getTask()).willReturn(TASK);

        instrumentProcessor.process();

        verify(instrument, times(1)).execute(TASK);
    }

    @Test(expected = ArgumentNullException.class)
    public void pass_exception_on_to_the_caller() {
        given(taskDispatcher.getTask()).willReturn(null);

        instrumentProcessor.process();
    }

    @Test
    public void handle_finished_task_event_fired_by_instrument() {
        given(taskDispatcher.getTask()).willReturn(TASK);

        instrumentProcessor.process();

        verify(taskDispatcher, times(1)).finishedTask(TASK);
    }

    private static class TestableInstrument implements Instrument {
        private List<Processor> processors = new ArrayList<>();

        @Override
        public void execute(String task) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.finished();
        }

        @Override
        public void finished() {
            setStatus();
        }

        void addProcessor(Processor processor) {
            this.processors.add(processor);
        }

        void setStatus() {
            this.processors.forEach(p -> p.update("finished"));
        }
    }
}
