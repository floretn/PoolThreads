package server;

import application.Application;

public class ThreadsPool implements Runnable{

    MyThread[] myThreads;
    Thread[] threads;
    final Queue queue;
    Thread threadMain;

    public ThreadsPool(int n, Queue queue, Thread threadMain){
        myThreads = new MyThread[n];
        threads = new Thread[n];
        for(int i = 0; i < n; i++){
            myThreads[i] = new MyThread(i + 1);
            threads[i] = new Thread(myThreads[i]);
        }
        this.queue = queue;
        this.threadMain = threadMain;
    }

    public void run(){
        for(Thread thread : threads){
            thread.start();
        }
    }

    private class MyThread implements Runnable{

        int i;

        public MyThread(int i) {
            this.i = i;
        }

        public void run(){

                System.out.println("Поток " + i + " запущен");
                while (threadMain.getState() != java.lang.Thread.State.TERMINATED || queue.getCounter() != 0) {
                    synchronized (queue) {
                        if (queue.hasNext()) {
                            Application app = queue.next();
                            System.out.println("Результат = " + app.rez + "; Время обработки = " + app.time + "; Поток №" + i);
                            try {
                                java.lang.Thread.sleep(app.time / 1000000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

        }
    }
}
