package com.pro516.thrifttogether.util;

import android.os.Looper;
import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by hncboy on 2018/6/9.
 */
public class ThreadUtils {

    /**
     * 得到一个线程池
     * SingleThreadExecutor使用单线程执行任务
     * SingleThreadExecutor保证了任务执行的顺序，不会存在多线程活动
     */
    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    /**
     * new Handler(),那么这个会默认用当前线程的looper
     * Looper.getMainLooper()表示放到主UI线程去处理
     */
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    /**
     * 处理UI
     * 将Runnable作为callback属性然后产生一个新的Message对象，通过Handler发送出去
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    /**
     * 开启一个线程池
     * @param runnable
     */
    public static void runOnBackgroundThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }
}
