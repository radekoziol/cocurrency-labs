package counter;

/**
 * Created by student15 on 2018-10-08.
 */
public class Counter {

    public Integer counter;

    public Counter(Integer counter) {
        this.counter = counter;
    }

    public  void incrementCounter(){
        synchronized (counter) {
            counter++;
        }
    }

    public  void decrementCounter(){
        synchronized (counter) {
            counter--;
        }
    }

}
