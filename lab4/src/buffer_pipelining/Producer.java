package buffer_pipelining;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Producer extends Processor {


    public Producer(Processor nextProcessor, Buffer buffer, Semaphore semaphore) {
        super(semaphore, nextProcessor, buffer);
    }

    public void run(int id) {
        System.out.println("Producer starts working!");
        IntStream.range(0, 10).forEach(i -> {
            try {
                Thread.sleep(new Random().nextInt(1000));
                process(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer.put(id, id * 2);
        });
    }

    @Override
    public void process(int id) {

        buffer.put(id, id * 2);
        System.out.println("Producer just put " + "message: " + id * 2);

        getNextProcessor().getSemaphore().post();
    }


}
