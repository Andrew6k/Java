package com.company;

public class Buffer {
    private Token t;
    private boolean available = false;
    public Token getToken() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false; notifyAll();
        return t;
    }
        public synchronized void setToken(Token t) {
            while (available) {
                try {
                    wait();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            this.t = t; available = true; notifyAll();
    }
}