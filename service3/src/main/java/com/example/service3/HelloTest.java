package com.example.service3;

/**
 * @author 码劲
 */
public class HelloTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{

                while (true){
                    try {
                        Thread.sleep(1000);
                        System.out.println(111);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

        }

        Object monitor = new Object();
        Object monitor2 = new Object();

        new Thread(()->{

            synchronized (monitor){
                synchronized (monitor2){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                     }
                }
            }
        }).start();

        new Thread(()->{

            synchronized (monitor2){
                synchronized (monitor){}
            }
        }).start();

    }
}
