package com.yinxt.surveyscale.common.threadpool;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author yinxt
 * @version 1.0
 * @date 2020/4/20 15:08
 */
public class ThreadPoolUtil {

    static ThreadFactory threadFactory = new ThreadFactoryBuilder().build();
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static class ThreadPoolHolder {
        private static ThreadPoolUtil INSTANCE = new ThreadPoolUtil();
    }

    public static ThreadPoolUtil getInstance() {
        return ThreadPoolHolder.INSTANCE;
    }

    public void executeTask(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
