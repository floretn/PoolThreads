package server;

import application.Application;

public class Queue {

    private Node first = null;
    private Node last = null;
    private int counter = 0;

    public void add(Application app){
        if (counter == 0){
            first = new Node(app);
            last = first;
        }else{
            last.next = new Node(app);
            last = last.next;
        }
        counter++;

    }

    public boolean hasNext(){
        return first != null;
    }

    public Application next(){
        Node next = first;
        first = first.next;
        counter--;
        return next.app;
    }

    public int getCounter(){
        return counter;
    }

    private static class Node{
        Application app;
        Node next;

        Node(Application app){
            this.app = app;
            next = null;
        }
    }
}
