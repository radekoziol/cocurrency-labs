package self_service_shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {

        List<Cart> cartList = new ArrayList<>();

        cartList.add(new Cart(Availability.FREE, "1"));
        cartList.add(new Cart(Availability.FREE, "2"));
        cartList.add(new Cart(Availability.FREE, "3"));
        cartList.add(new Cart(Availability.FREE, "4"));

        Shop shop = new Shop(cartList);


        List<Runnable> clientTasks = new ArrayList<>();

        IntStream.range(1, 16).forEach(i -> {
            clientTasks.add(() -> {
                Client client = new Client(shop);
                try {
                    client.run(i);
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
                t.join();
                t.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

}
