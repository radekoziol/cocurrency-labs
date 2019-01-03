package basic_buffer_pipelining;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Producer extends Processor {

    private static int putObjectCounter;
    private static Semaphore isFullBufferSemaphore;

    public Producer(Processor nextProcessor, Buffer buffer, Semaphore semaphore) {
        super(semaphore, nextProcessor, buffer);
        putObjectCounter = 0;
        isFullBufferSemaphore = new Semaphore(0, Buffer.bufferSize);
    }

    public static boolean areProducersWaiting() {
        return isFullBufferSemaphore.getCounter() > 0;
    }

    public void run(int id) {
        System.out.println("Producer " + id + " starts working!");
        IntStream.range(0, new Random().nextInt(Buffer.bufferSize / 2)).forEach(i -> {
            try {
                Thread.sleep(new Random().nextInt(1000));
                process(putObjectCounter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void process(int id) throws InterruptedException {

        long startTime = System.nanoTime();

        getSemaphore().take();

        // Updating counter
        Object readObject = new Object();

        while (readObject != null && id < Buffer.bufferSize) {
            readObject = buffer.read(id++);
        }

        if (id == Buffer.bufferSize) {
            System.out.println("Producer can not find slot in buffer!");
            getSemaphore().post();

            while (buffer.isEmpty()) {
                isFullBufferSemaphore.wait();
            }

            putObjectCounter = 0;
            process(putObjectCounter);

        } else {

            buffer.put(id - 1, id * 2);
            System.out.println("Producer just put in slot number " + (id - 1)
                    + " object: " + id * 2);

            putObjectCounter = (id) % Buffer.bufferSize;

            getSemaphore().post();
            getNextProcessor().getSemaphore().post();
        }

//        System.out.println( "Total Time: " + (System.nanoTime() - startTime) / 1000000);


    }


    public static Semaphore getIsFullBufferSemaphore() {
        return isFullBufferSemaphore;
    }


}
