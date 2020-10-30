package server;

import application.Application;
import client.Generator;
import java.util.Scanner;
import java.util.ArrayList;

public class Server implements Runnable{

    private final Thread t;
    private final Queue queue;
    private final ArrayList<Application> app = new ArrayList<>();

    public Server(int n, Thread t) {
        queue = new Queue();
        ThreadsPool threadPool = new ThreadsPool(n, queue, t);
        Thread forThreadPool = new Thread(threadPool);
        forThreadPool.start();
        this.t = t;
    }

    public void setApp(Application app) {
        this.app.add(app);
    }

    public void run(){
        while(t.getState() != java.lang.Thread.State.TERMINATED) {
            synchronized (queue) {
                if (app.size() != 0) {
                    queue.add(app.get(0));
                    app.remove(0);
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.print("Введите количество потоков: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.print("Введите количество генераторов: ");
        int m = sc.nextInt();

        Thread t = Thread.currentThread();

        Server server = new Server(n, t);
        Thread genThread = new Thread(server);
        genThread.start();

        Generator[] generators = new Generator[m];
        Thread[] threads = new Thread[m];
        for (int i = 0; i < m; i++) {
            generators[i] = new Generator(server);
            threads[i] = new Thread(generators[i]);
            threads[i].start();
        }
        String s = sc.next();
    }
}
