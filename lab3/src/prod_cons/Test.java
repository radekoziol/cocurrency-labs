package prod_cons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Test {

    public static void main(String[] args) {

        BoundedBuffer buffer = new BoundedBuffer();

        List<Runnable> producerTasks = new ArrayList<>();

        IntStream.range(1, 6).forEach(i -> {
            producerTasks.add(() -> {
                Consumer consumer = new Consumer(buffer);
                try {
                    consumer.run(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });

        List<Runnable> consumerTasks = new ArrayList<>();

        IntStream.range(1, 4).forEach(i -> {
            consumerTasks.add(() -> {
                Producer producer = new Producer(buffer);
                try {
                    producer.run(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });


        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        consumerTasks.forEach(t -> consumerThreads.add(new Thread(t)));
        producerTasks.forEach(t -> producerThreads.add(new Thread(t)));

        producerThreads.forEach(Thread::start);
        consumerThreads.forEach(Thread::start);


        producerThreads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        consumerThreads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }


}

