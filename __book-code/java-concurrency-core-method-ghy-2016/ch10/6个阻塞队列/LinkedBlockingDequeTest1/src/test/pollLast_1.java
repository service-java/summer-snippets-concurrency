package test;

import java.util.concurrent.LinkedBlockingDeque;

public class pollLast_1 {

    public static void main(String[] args) {
        LinkedBlockingDeque deque = new LinkedBlockingDeque(3);
        deque.addFirst("anyString1");
        deque.addFirst("anyString2");
        deque.addFirst("anyString3");
        System.out.println(deque.pollLast());
    }

}
