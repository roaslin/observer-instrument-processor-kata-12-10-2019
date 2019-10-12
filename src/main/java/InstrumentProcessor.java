public class InstrumentProcessor {
    private TaskDispatcher taskDispatcher;
    private Instrument instrument;

    public InstrumentProcessor(TaskDispatcher taskDispatcher, Instrument instrument) {
        this.taskDispatcher = taskDispatcher;
        this.instrument = instrument;
    }

    public void process() {
        String task = taskDispatcher.getTask();
        instrument.execute(task);
    }
}
