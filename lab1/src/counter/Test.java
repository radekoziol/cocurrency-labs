package counter;

import java.util.stream.IntStream;

/**
 * Created by student15 on 2018-10-08.
 */
public class Test {

    public static void main(String[] args) {

        Counter counter = new Counter(0);

        Runnable incrementTask = () -> {
            IntStream.range(1, 100000).forEach(i -> {
                counter.incrementCounter();
            });
        };

        Runnable decrementTask = () -> {
            IntStream.range(1, 100000).forEach(i -> {
                counter.decrementCounter();
            });
        };

        Thread incrementThread = new Thread(incrementTask);
        Thread decrementThread = new Thread(decrementTask);

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        }


        System.out.println(counter.counter);

    }
}
