package self_service_shop;

import java.util.Random;

public class Client {

    private final Shop shop;
    private Cart cart;

    public Client(Shop shop) {
        this.shop = shop;
    }


    public synchronized void run(int id) throws Exception {

        System.out.println("Client " + id + " trying to get cart..");
        cart = shop.getCart();

        try {
            System.out.println("Client " + id + " starting shopping");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Client " + id + " done shopping");

        shop.returnCart(cart);

        System.out.println("Client " + id + " returned cart");


    }

}

