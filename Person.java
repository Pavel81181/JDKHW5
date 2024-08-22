package org.example.HW5;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Person extends Thread {
    private String name;
    private int leftFork;
    private int rightFork;
    private int counter;
    private Random random;
    private CountDownLatch cdl;
    private Dinner dinner;

    public Person(String name, Dinner dinner, int leftFork, int rightFork, CountDownLatch cdl) {
        this.dinner = dinner;
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cdl = cdl;
        counter = 0;
        random = new Random();
    }

    @Override
    public void run() {

        while (counter < 3) {
            try {
                thinking();
                eating();
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }

        System.out.println(name + " прием пищи закончил");
        cdl.countDown();
    }

    private void eating() throws InterruptedException {
        if (dinner.tryGetForks(leftFork, rightFork)) {
            System.out.println(name + " ест вилками: " + leftFork
                    + " и " + rightFork);
            sleep(random.nextInt(1000, 3000));
            dinner.putForks(leftFork, rightFork);
            System.out.println(name + " размышляет, освободив вилки " + leftFork + " и " + rightFork);
            counter++;
        }

    }

    private void thinking() throws InterruptedException {
        sleep(random.nextInt(1000, 3000));
    }
}