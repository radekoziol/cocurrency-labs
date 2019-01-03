package basic_buffer_pipelining;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Consumer extends Processor {

    private static int readObjectCounter;
    private Semaphore consumerSemaphore;

    public Consumer(Processor previousProcessor, Buffer buffer, Semaphore semaphore, Semaphore consumerSemaphore) {
        super(semaphore, previousProcessor, buffer);
        this.consumerSemaphore = consumerSemaphore;
        readObjectCounter = 0;
    }

    public void run(int id) {
        System.out.println("Consumer " + id + " starts working!");
        IntStream.range(0, new Random().nextInt(Buffer.bufferSize / (2))).forEach(i -> {
            try {
                Thread.sleep(new Random().nextInt(1000));
                process(readObjectCounter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void process(int id) {

        getSemaphore().take();
        System.out.println("Go!");
        consumerSemaphore.take();

        // Updating counter
        Object readObject = null;

        while (readObject == null && id < Buffer.bufferSize) {
            readObject = buffer.read(id++);
        }

        System.out.println("Consumer - just read message: " + readObject);

        if (Producer.areProducersWaiting())
            Producer.getIsFullBufferSemaphore().post();

        readObjectCounter = id % Buffer.bufferSize;

        consumerSemaphore.post();
    }


}
