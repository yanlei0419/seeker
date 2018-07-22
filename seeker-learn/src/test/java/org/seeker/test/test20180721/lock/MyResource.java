package org.seeker.test.test20180721.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyResource {
    private Lock lock=new ReentrantLock();
    private List list=new ArrayList();
    private Condition a=lock.newCondition();
    private int flag=3;
    
    
    
    
    public void add(){
        lock.lock();
        try {
            if (list.size()>flag) {
                String val=UUID.randomUUID().toString();
                list.add(val);
                System.out.println("添加一个商品"+val);
                a.signalAll();
            } else {
                System.out.println("商品已满");
                a.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void sub(){
        lock.lock();
        try {
            if (list.size()>=flag-2) {
                System.out.println("消费一个商品"+list.remove(0));
                a.signalAll();
            } else {
                System.out.println("商品已空");
                a.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] a){
        MyResource my = new MyResource();
        Thread t1 = new Thread(new Consumer(my));
        Thread t2 = new Thread(new Produce(my));
        t1.start();
        t2.start();

    }

}