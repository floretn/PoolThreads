package application;

import java.util.function.BiFunction;

public class Application {

        public int a1;
        public int a2;
        public double rez;
        public long time;
        public BiFunction<Integer, Integer, Double> func;

        public Application(int a1, int a2, double rez, long time, BiFunction<Integer, Integer, Double> func){
            this.a1 = a1;
            this.a2 = a2;
            this.rez = rez;
            this.time = time;
            this.func = func;
        }
}
