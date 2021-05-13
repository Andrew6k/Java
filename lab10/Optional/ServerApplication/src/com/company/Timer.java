package com.company;

public class Timer implements Runnable{
    float timeToWaitSeconds = 10;
    @Override
    public void run() {
        long Start = System.nanoTime();
        long End = System.nanoTime();
        boolean isRunnable = true;
        while((End - Start)/1_000_000_000 <= timeToWaitSeconds && isRunnable) {
            End = System.nanoTime();
        }

    }
}
