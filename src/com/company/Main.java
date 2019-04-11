package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {
    int p_max = 24;
    int size = 4096;
    ArrayList<Integer> n_ops = new ArrayList<>();
    n_ops.add(512);
    n_ops.add(1024);
    n_ops.add(2048);
    int test_count  = 100;



for (int n: n_ops){
    for(int p = 1; p < p_max + 1; p++) {
        int average_p = 0;
        for (int c = 0; c < test_count; c++) {


            Ring ring = new Ring(size);
            Ring results = new Ring(p);
            CyclicBarrier BARRIER = new CyclicBarrier(p);
            ArrayList<Tester> testers = new ArrayList<>();
            for (int i = 0; i < p; i++) {
                testers.add(new Tester(ring, BARRIER, n, results, p));
            }
            for (Tester t : testers) {
                t.start();
            }
            for (Tester t : testers) {
                try {
                    t.join();
                } catch (java.lang.InterruptedException e) {
                    e.printStackTrace(System.err);
                }

            }
            //System.out.println(results);
            int summ = 0;
            for (int i : results.buffer) {
                summ += i;
            }
            int aver = summ / p;

            average_p += aver;
        }

        String filename = Integer.toString(n) + ".txt";
        try{

            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(filename),true));
            printWriter.write(p + " " + Integer.toString(average_p/test_count) + "\n");
            printWriter.close();
        }
        catch (java.io.IOException e ){
            e.printStackTrace(System.err);

        }



        System.out.println(p + ": " + average_p/test_count);
    }

}




	//ArrayList<Writer> writers = new ArrayList();
	//ArrayList<Reader> readers = new ArrayList();




    }
}
