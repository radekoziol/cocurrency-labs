package basic_buffer_pipelining;


import java.util.Random;
import java.util.stream.IntStream;

public class PipeProcessor extends Processor {

    private boolean isBufferEmpty;

    public PipeProcessor(Processor nextProcessor, Buffer buffer, Semaphore semaphore) {
        super(semaphore, nextProcessor, buffer);
        isBufferEmpty = false;
    }

    public void run(int p) {

//        System.out.println("Pipe Processor " + p + " starts working!");
        IntStream.range(0, Buffer.bufferSize).forEach(i -> {
            try {
                if (!isBufferEmpty) {
                    process(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void process(int id) throws InterruptedException {

        getSemaphore().take();

        Object input = buffer.read(id);
        if (input != null) {

            int output = (int) input;

//            System.out.println("Pipe processor: starting processing " + output);

            Thread.sleep(new Random().nextInt(300));

//            System.out.println("Pipe processor: putting " + output * 2);

            buffer.put(id, output * 2);
        } else {
            isBufferEmpty = true;
        }

        getNextProcessor().getSemaphore().post();

    }

}
