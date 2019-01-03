package basic_buffer_pipelining;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student15 on 2018-10-08.
 */
public class Test {

    public final static int pipeNumber = Buffer.bufferSize;
    public final static int producerNumber = 10;
    public final static int consumerNumber = 10;


    public static void main(String[] args) {

        Buffer buffer = new Buffer();
        List<Thread> allThreads = new ArrayList<>();

        // Adding producers

        // First pipe processor
        PipeProcessor nextPipeProcessor = (new PipeProcessor(null, buffer, new Semaphore(0, pipeNumber)));

        Semaphore producerSemaphore = new Semaphore(1, 1);
        for (int i = 1; i <= producerNumber; i++) {

            Producer producer = new Producer(null, buffer, producerSemaphore);
            producer.setNextProcessor(nextPipeProcessor);
            allThreads.add(handleProducerThread(producer, i));

        }


        // Adding few pipe processors
        List<Runnable> pipeTasks = new ArrayList<>();

        PipeProcessor pipeProcessor;

        for (int p = 1; p < pipeNumber; p++) {

            pipeProcessor = new PipeProcessor(null, buffer, new Semaphore(0, pipeNumber));
            nextPipeProcessor.setNextProcessor(pipeProcessor);

            PipeProcessor finalNextPipeProcessor = nextPipeProcessor;
            int finalP = p;
            pipeTasks.add(() -> {
                finalNextPipeProcessor.run(finalP);
            });

            nextPipeProcessor = pipeProcessor;
        }

        // Consumers
        Semaphore consumerReceiveSemaphore = new Semaphore(0, Buffer.bufferSize);
        Semaphore consumerSemaphore = new Semaphore(1, 1);
        for (int i = 1; i <= consumerNumber; i++) {

            Consumer consumer = new Consumer(null, buffer, consumerReceiveSemaphore, consumerSemaphore);
            nextPipeProcessor.setNextProcessor(consumer);
            allThreads.add(handleConsumerThread(consumer, i));
        }


        PipeProcessor finalNextPipeProcessor = nextPipeProcessor;
        pipeTasks.add(() -> {
            finalNextPipeProcessor.run(pipeNumber);
        });


        List<Thread> pipeThreads = new ArrayList<>();
        pipeTasks.forEach(t -> pipeThreads.add(new Thread(t)));

        pipeThreads.forEach(Thread::start);

        allThreads.addAll(pipeThreads);


        // Join them
        allThreads.forEach(thread -> {
            try {
                thread.join(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    private static Thread handleProducerThread(Producer producer, int id) {

        Runnable producerTask = () -> producer.run(id);

        Thread producerThread = new Thread(producerTask);
        producerThread.start();

        return producerThread;
    }


    private static Thread handleConsumerThread(Consumer consumer, int id) {

        Runnable consumerTask = () -> {
            consumer.run(id);
        };

        Thread consumerThread = new Thread(consumerTask);
        consumerThread.start();

        return consumerThread;
    }
}

