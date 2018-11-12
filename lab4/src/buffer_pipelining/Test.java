package buffer_pipelining;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student15 on 2018-10-08.
 */
public class Test {

    private final static int pipeNumber = 10;


    public static void main(String[] args) {

        Buffer buffer = new Buffer();
        List<Thread> allThreads = new ArrayList<>();

        // Adding producer
        Producer producer = new Producer(null, buffer, new Semaphore(0, pipeNumber));
        allThreads.add(handleProducerThread(producer));

        // First pipe processor
        PipeProcessor nextPipeProcessor = (new PipeProcessor(null, buffer, new Semaphore(0, pipeNumber)));
        producer.setNextProcessor(nextPipeProcessor);

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

        Consumer consumer = new Consumer(null, buffer, new Semaphore(0, pipeNumber));
        nextPipeProcessor.setNextProcessor(consumer);

        PipeProcessor finalNextPipeProcessor = nextPipeProcessor;
        pipeTasks.add(() -> {
            finalNextPipeProcessor.run(pipeNumber);
        });


        List<Thread> pipeThreads = new ArrayList<>();
        pipeTasks.forEach(t -> pipeThreads.add(new Thread(t)));

        pipeThreads.forEach(Thread::start);

        allThreads.addAll(pipeThreads);
        allThreads.add(handleConsumerThread(consumer));


        // Join them
        allThreads.forEach(thread -> {
            try {
                thread.join(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }


    private static Thread handleProducerThread(Producer producer) {

        Runnable producerTask = () -> producer.run(1);

        Thread producerThread = new Thread(producerTask);
        producerThread.start();

        return producerThread;
    }


    private static Thread handleConsumerThread(Consumer consumer) {

        Runnable consumerTask = () -> {
            consumer.run(1);
        };

        Thread consumerThread = new Thread(consumerTask);
        consumerThread.start();

        return consumerThread;
    }
}

