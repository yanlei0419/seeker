package org.seeker.thread.Queue;




public class Produce implements Runnable {

    private MyResource list;

    public Produce(MyResource list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true){
            try {
                list.add();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
