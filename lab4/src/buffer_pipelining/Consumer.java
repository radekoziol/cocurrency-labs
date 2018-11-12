package buffer_pipelining;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Consumer extends Processor {


    public Consumer(Processor previousProcessor, Buffer buffer, Semaphore semaphore) {
        super(semaphore, previousProcessor, buffer);
    }

    public void run(int id) {
        System.out.println("Consumer starts working!");
        IntStream.range(0, 10).forEach(this::process);
    }

    @Override
    public void process(int i) {

        getSemaphore().take();

        System.out.println("Consumer - just read message: " + buffer.read(i));

    }


}
