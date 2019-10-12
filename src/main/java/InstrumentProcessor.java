public class InstrumentProcessor implements Processor {
    private TaskDispatcher taskDispatcher;
    private Instrument instrument;
    private String instrumentStatus;

    public InstrumentProcessor(TaskDispatcher taskDispatcher, Instrument instrument) {
        this.taskDispatcher = taskDispatcher;
        this.instrument = instrument;
        this.instrumentStatus = "";
    }

    public void process() {
        String task = taskDispatcher.getTask();
        if (task == null) {
            throw new ArgumentNullException();
        }
        instrument.execute(task);

        while (true) {
            if (isFinishedTask()) {
                taskDispatcher.finishedTask(task);
                break;
            }
        }
    }

    private boolean isFinishedTask() {
        return instrumentStatus.equalsIgnoreCase("finished");
    }

    private void setInstrumentStatus(String instrumentStatus) {
        this.instrumentStatus = instrumentStatus;
    }

    @Override
    public void update(Object status) {
        setInstrumentStatus((String) status);
    }
}
