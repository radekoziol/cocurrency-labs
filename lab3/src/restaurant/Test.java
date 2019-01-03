package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by student10 on 2018-11-05.
 */
public class Test {


    public static void main(String[] args) {

        DoubleTable table = new DoubleTable(Availability.FREE);
        Waiter waiter = new Waiter(table);

        List<Runnable> clientTasks = new ArrayList<>();

        IntStream.range(1, 16).forEach(i -> {
            Person person = new Person("Person" + i, waiter, null);
            Person partner = new Person("Partner" + i, waiter, person);
            person.setPartner(partner);

            clientTasks.add(() -> {
                try {
                    partner.run(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            clientTasks.add(() -> {
                try {
                    person.run(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });


        List<Thread> clientThreads = new ArrayList<>();

        clientTasks.forEach(t -> clientThreads.add(new Thread(t)));

        clientThreads.forEach(Thread::start);

        clientThreads.forEach(t -> {
            try {
                t.join(100000);
                t.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }


}
