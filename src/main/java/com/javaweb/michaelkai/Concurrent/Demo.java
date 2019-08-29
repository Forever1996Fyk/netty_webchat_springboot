package com.javaweb.michaelkai.Concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Demo
 * @Description TODO
 * @Author YuKai Fan
 * @Date 2019/8/27 20:51
 * @Version 1.0
 **/
public class Demo {
    private static int money;
    //重入锁 如果拿到锁线程的线程A出来之后，发现又是这个线程A要获取锁，那就不会在获取锁了
    //但是很容易死锁
    private final static ReentrantLock lock = new ReentrantLock();

    //线程副本对象
    private final static ThreadLocal<Integer> moneyLocal = new ThreadLocal<>();

    public void order() {
        money = new Random().nextInt(1000) + 1;
        moneyLocal.set(money);//存入金额
        System.out.println(Thread.currentThread().getName() + "订单金额为：" + moneyLocal.get());
    }

    public void orderDetail() {
        System.out.println(Thread.currentThread().getName() + "订单详情金额为：" + moneyLocal.get());
    }

    public void orderPay() {
        System.out.println(Thread.currentThread().getName() + "订单交易金额为：" + moneyLocal.get());
    }

    //重入锁
    public void handle1() {
        try {

            //====还是需要进行等待的状态
            //加锁
            lock.lock();
            order();
            orderDetail();
            orderPay();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //用完之后必须释放，否则就是死锁
            lock.unlock();
        }

    }

    //加锁
    public void handle2() {
        synchronized (Demo.class) {
            order();
            orderDetail();
            orderPay();
        }
    }

    public void handle3() {
        order();
        orderDetail();
        orderPay();
    }

    public static void main(String[] args) {
        int count = 20;
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i <count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Demo demo = new Demo();
                    //多线程处理订单
                    demo.handle3();
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
