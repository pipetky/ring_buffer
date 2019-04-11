package com.company;

public class Writer extends Thread {
    private Ring buffer;

    Writer(Ring buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run()
    {
        for(int i = 0; i < 1000000; i++) {
            this.buffer.put(i);
        }
    }
}
