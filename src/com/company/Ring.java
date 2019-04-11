package com.company;

import sun.awt.Mutex;

import java.util.Iterator;
import java.util.concurrent.*;

public class Ring {
    public int size;
    private int position_write = 0;
    private int position_read = 0;
    public int[] buffer;
    private Semaphore sem_read, sem_write;
    private Mutex mutex_read, mutex_write;


    Ring(int size) {
        this.mutex_read = new Mutex();
        this.mutex_write = new Mutex();

        this.size = size;
        this.buffer = new int[size];
        this.sem_read = new Semaphore(size);
        this.sem_write = new Semaphore(size);
        try {
            this.sem_read.acquire(size);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace(System.err);
        }


        this.sem_read.release();
    }

    public void put(int elem) {
        try {
            this.mutex_write.lock();
            this.sem_write.acquire();

            this.buffer[this.position_write] = elem;
            this.sem_read.release();
            this.position_write = (this.position_write + 1) % this.size;
            this.mutex_write.unlock();
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace(System.err);
        }

    }

    public int get() {
        int buf = -1;
        try {
            this.mutex_read.lock();
            this.sem_read.acquire();
            buf = this.buffer[this.position_read];
            this.sem_write.release();
            this.position_read = (this.position_read + 1) % this.size;
            this.mutex_read.unlock();
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace(System.err);
        }
        return buf;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i : this.buffer){
            str = str.concat(Integer.toString(i)) + " ";
        }
        return str;
    }


}