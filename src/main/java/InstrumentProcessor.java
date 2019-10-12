public class InstrumentProcessor {
    private TaskDispatcher taskDispatcher;

    public InstrumentProcessor(TaskDispatcher taskDispatcher) {
        this.taskDispatcher = taskDispatcher;
    }

    public void process() {
        taskDispatcher.getTask();
    }
}
