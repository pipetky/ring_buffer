package com.company;

public class Reader extends Thread {
    private Ring buffer;

    Reader(Ring buffer){
        this.buffer = buffer;

    }

    @Override
    public void run(){
       for(int i = 0; i < 1000000; i++) {
           System.out.println(this.buffer.get());
       }
    }
}
