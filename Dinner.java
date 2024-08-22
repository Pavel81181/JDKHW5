package org.example.HW5;

import java.util.concurrent.CountDownLatch;

public class Dinner extends Thread {
    private final int COUNT = 5;
    private Fork[] forks;
    private Person[] persons;
    private CountDownLatch cdl;


    public Dinner() {
        forks = new Fork[COUNT];
        persons = new Person[COUNT];
        cdl = new CountDownLatch(COUNT);
        init();
    }

    @Override
    public void run() {
        System.out.println("START");
        try {
            thinkingProcess();
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FINISH");
    }

    public synchronized boolean tryGetForks(int leftFork, int rightFork) {
        if (!forks[leftFork].isUsing() && !forks[rightFork].isUsing()) {
            forks[leftFork].setUsing(true);
            forks[rightFork].setUsing(true);
            return true;
        }
        return false;
    }

    public void putForks(int leftFork, int rightFork){
        forks[leftFork].setUsing(false);
        forks[rightFork].setUsing(false);
    }

    private void init() {
        for (int i = 0; i < COUNT; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < COUNT; i++) {
            persons[i] = new Person("Философ №" + (i+1), this,
                    i, (i + 1) % COUNT, cdl);
        }
    }

    private void thinkingProcess() {
        for (Person person : persons) {
            person.start();
        }
    }
}