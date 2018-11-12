package buffer_pipelining;

import java.util.Random;
import java.util.stream.IntStream;

public class PipeProcessor extends Processor {


    public PipeProcessor(Processor nextProcessor, Buffer buffer, Semaphore semaphore) {
        super(semaphore, nextProcessor, buffer);
    }

    public void run(int p) {

        System.out.println("Thread no " + p + " starts working!");
        IntStream.range(0, 10).forEach(i -> {
            try {
                process(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void process(int id) throws InterruptedException {

        getSemaphore().take();

        Thread.sleep(new Random().nextInt(1000));

        int output = (int) buffer.read(id);
        buffer.put(id, output * 2);

        getNextProcessor().getSemaphore().post();

    }

}
