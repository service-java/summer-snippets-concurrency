package test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class offerFirst_3 {

    public static void main(String[] args) {
        try {
            LinkedBlockingDeque deque = new LinkedBlockingDeque(3);
            System.out.println("1 " + System.currentTimeMillis());
            System.out.println(deque.offerFirst("anyString1", 1,
                    TimeUnit.SECONDS));
            System.out.println("2 " + System.currentTimeMillis());
            System.out.println(deque.offerFirst("anyString2", 1,
                    TimeUnit.SECONDS));
            System.out.println("3 " + System.currentTimeMillis());
            System.out.println(deque.offerFirst("anyString3", 1,
                    TimeUnit.SECONDS));
            System.out.println("4 " + System.currentTimeMillis());
            System.out.println(deque.offerFirst("anyString3", 4,
                    TimeUnit.SECONDS));
            System.out.println("5 " + System.currentTimeMillis());
            System.out.println(deque.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
