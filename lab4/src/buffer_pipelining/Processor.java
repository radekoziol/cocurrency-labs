package buffer_pipelining;

public abstract class Processor {

    private Processor nextProcessor;
    private Semaphore semaphore;
    protected Buffer buffer;

    public Processor(Semaphore semaphore, Processor previousProcessor, Buffer buffer) {
        this.semaphore = semaphore;
        this.nextProcessor = previousProcessor;
        this.buffer = buffer;
    }

    public abstract void process(int id) throws InterruptedException;

    public Processor getNextProcessor() {
        return nextProcessor;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setNextProcessor(Processor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }
}
