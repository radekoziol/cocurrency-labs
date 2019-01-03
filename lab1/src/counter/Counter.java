package counter;

/**
 * Created by student15 on 2018-10-08.
 */
public class Counter {

    private Integer counter;

    public Counter(Integer counter) {
        this.counter = counter;
    }

    public synchronized void incrementCounter() throws InterruptedException {
//         (counter) {
        Thread.sleep(10000);
            counter++;
        //}
    }

    public void decrementCounter() throws InterruptedException {
//        synchronized (counter) {
        Thread.sleep(10000);
//
//            counter--;
//        }
    }

    public Integer getCounter() {
        return counter;
    }
}
