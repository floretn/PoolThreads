package client;

import java.util.Random;
import java.util.function.BiFunction;
import application.Application;
import server.Server;

public class Generator implements Runnable{

    final Server server;

    public Generator(Server gen) {
        this.server = gen;
    }

    public void run(){
        int i = 1;
        Application app;
        Random rnd = new Random();
        while(i < 100){
            int a1 = (rnd.nextInt(1000) + i)/i + rnd.nextInt(1000)*i;
            int a2 = (rnd.nextInt(100) + i*i)/(i + i) + rnd.nextInt(1000)/i;
            int numFunc = rnd.nextInt(4);

            double rez = 0.0;
            long time1 = 0;
            long time2 = 0;
            BiFunction<Integer, Integer, Double> func = Generator::amount;

            if(numFunc == 0){
                time1 = System.nanoTime();
                rez = func.apply(a1, a2);
                time2 = System.nanoTime();
            }
            if(numFunc == 1){
                func = Generator::multiplication;
                time1 = System.nanoTime();
                rez = func.apply(a1, a2);
                time2 = System.nanoTime();
            }
            if(numFunc == 2){
                func = Generator::subtraction;
                time1 = System.nanoTime();
                rez = func.apply(a1, a2);
                time2 = System.nanoTime();
            }
            if(numFunc == 0){
                func = Generator::division;
                time1 = System.nanoTime();
                rez = func.apply(a1, a2);
                time2 = System.nanoTime();
            }
            long time = time2 - time1;

            app = new Application(a1, a2, rez, time, func);
            synchronized (server) {
                server.setApp(app);
            }
            i++;
        }
    }

    public static double amount(int a1, int a2){
        return a1 + a2;
    }

    public static double multiplication(int a1, int a2){
        return a1 * a2;
    }

    public static double subtraction(int a1, int a2){
        return a1 - a2;
    }

    public static double division(int a1, int a2){
        if (a2 != 0) {
            return a1 / a2;
        }
        return 0;
    }
}





