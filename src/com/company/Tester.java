package com.company;
import com.company.Ring;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import java.util.concurrent.CyclicBarrier;

public class Tester extends Thread {
    private Ring buffer;
    private CyclicBarrier BARRIER;
    private int n;
    private Ring results;
    private int n_threads;

    Tester(Ring buffer, CyclicBarrier BARRIER, int n, Ring result, int n_threads){
        this.buffer = buffer;
        this.BARRIER = BARRIER;
        this.n = n;
        this.results = result;
        this.n_threads = n_threads;


    }



    @Override
    public void run(){
       int max_write = this.buffer.size / this.n_threads;
       int write = 0;
       Random random = new Random();
       try{
           this.BARRIER.await();
       }
       catch (Exception e) {
           e.printStackTrace(System.err);
       }
       StopWatch timer = new StopWatch();
       timer.start();
       for(int i = 0; i < this.n; i++){
            if ((random.nextBoolean() && write != 0) || write == max_write){
                this.buffer.get();
                write--;
            }else {
                this.buffer.put(random.nextInt());
                write++;
            }
       }
       timer.stop();

       float result = this.n * 1000000 / timer.getNanoTime();
       this.results.put((int)result);
       //System.out.println((int)result);


    }
}
