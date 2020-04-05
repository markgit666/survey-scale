package com.yinxt.surveyscale;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinxt
 * @version 1.0
 * @date 2020-03-26 23:25
 */
@Slf4j
public class LockTest {

    public static void main(String[] args) {
        log.info("=============start============");

        LockTest lockTest = new LockTest();
        LockTest lockTest1 = new LockTest();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockTest) {
                    log.info("1.1");
                    synchronized (lockTest1) {
                        log.info("1.2");
                    }
                }
            }
        }, "t1");
        thread.setDaemon(true);
        thread.start();

//        thread.start();

        //再开启一个线程，造成死锁现象
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            synchronized (lockTest1) {
//                log.info("2.1");
//                synchronized (lockTest) {
//                    log.info("2.2");
//                }
//            }
//        }, "t2").start();


        new Thread(() -> {
            for (int i = 0; i < 9; i++) {
                log.info("i={}", i);
            }
        }).start();


    }

    public void methodA() {
        log.info("{}进入methodA", Thread.currentThread().getName());
        synchronized (this) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("methodA, {}=========醒了", Thread.currentThread().getName());
        }
    }

    public void methodB() {
        log.info("{}进入methodB", Thread.currentThread().getName());
        synchronized (this) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("methodA, {}=========醒了", Thread.currentThread().getName());
        }
    }


}
