package com.example.okhttp;

import com.example.okhttp.utils.ThreadUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * </p>
 * Create by huangzhijun on 2020/1/14 17:38
 */
public class Dispatcher {

    private int maxRequests = 64;

    // dispatcher中的任务都处理完成，变为空闲状态时，回调
    private Runnable idleCallback;

    private ExecutorService executorService;


    // 待处理的任务
    private final Deque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();

    // 正在处理过程中的异步请求任务
    private final Deque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque<RealCall.AsyncCall>();

    // 正在处理过程中中的同步请求任务
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<RealCall>();

    public Dispatcher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    Dispatcher() {
    }

    /**
     * 暴露该方法，以便在支持TaskClient在销毁时，可以销毁该线程池
     * taskClient.dispatcher().executorService().shutdown();
     * <p>
     * 另外，该方法也支持在外部未设置线程池的情况下，自动创建线程池，支持任务处理
     */
    synchronized ExecutorService executorService() {
        if (executorService == null || executorService.isShutdown()) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), ThreadUtil.threadFactory("OkHttp Dispatcher", false));
        }
        return executorService;
    }

    /**
     * dispatcher最大支持的同时处理的任务数
     *
     * @param maxRequests
     */
    public synchronized void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("max < 1: " + maxRequests);
        }
        this.maxRequests = maxRequests;
        promoteCalls();
    }

    public synchronized int getMaxRequests() {
        return maxRequests;
    }

    public synchronized void setIdleCallback(Runnable idleCallback) {
        this.idleCallback = idleCallback;
    }

    /**
     * 处理异步请求
     */
    synchronized void enqueue(RealCall.AsyncCall call) {
        System.out.println("Dispatcher.enqueue()");
        if (runningAsyncCalls.size() < maxRequests) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            readyAsyncCalls.add(call);
        }
    }

    /**
     * 取消所有请求
     */
    synchronized void cancelAll() {
        for (RealCall.AsyncCall call : readyAsyncCalls) {
            call.get().cancel();
        }

        for (RealCall.AsyncCall call : runningAsyncCalls) {
            call.get().cancel();
        }

        for (RealCall call : runningSyncCalls) {
            call.cancel();
        }
    }

    /**
     * 检测是否还存在待处理的异步任务，如果存在，继续处理
     */
    private void promoteCalls() {
        if (runningAsyncCalls.size() >= maxRequests) {
            return; // Already running max capacity.
        }
        if (readyAsyncCalls.isEmpty()) {
            return; // No ready calls to promote.
        }

        for (Iterator<RealCall.AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            RealCall.AsyncCall call = i.next();

            i.remove();
            runningAsyncCalls.add(call);
            executorService().execute(call);

            if (runningAsyncCalls.size() >= maxRequests) {
                return; // Reached max capacity.
            }
        }
    }


    /**
     * 记录正在处理过程中的同步任务
     */
    synchronized void executed(RealCall call) {
        System.out.println("Dispatcher.executed()");
        runningSyncCalls.add(call);
    }


    /**
     * 异步任务处理结束，通知dispatcher，以便检测是否还存在待处理的异步任务
     * 如果存在，继续触发相关的异步任务
     */
    void finished(RealCall.AsyncCall call) {
        finished(runningAsyncCalls, call, true);
    }

    /**
     * 同步请求结束，通知dispatcher，以便检测是否切换到了空闲状态
     */
    void finished(RealCall call) {
        finished(runningSyncCalls, call, false);
    }

    private <T> void finished(Deque<T> calls, T call, boolean promoteCalls) {
        System.out.println("Dispatcher.finished()");
        int runningCallsCount;
        Runnable idleCallback;
        synchronized (this) {
            if (!calls.remove(call)) {
                throw new AssertionError("Call wasn't in-flight!");
            }
            if (promoteCalls) {
                promoteCalls();
            }
            runningCallsCount = runningCallsCount();
            idleCallback = this.idleCallback;
        }

        if (runningCallsCount == 0 && idleCallback != null) {
            idleCallback.run();
        }
    }

    /**
     * 待进行请求的请求
     */
    public synchronized List<Call> queuedCalls() {
        List<Call> result = new ArrayList<Call>();
        for (RealCall.AsyncCall asyncCall : readyAsyncCalls) {
            result.add(asyncCall.get());
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * 处于请求过程中的请求
     */
    public synchronized List<Call> runningCalls() {
        List<Call> result = new ArrayList<Call>();
        result.addAll(runningSyncCalls);
        for (RealCall.AsyncCall asyncCall : runningAsyncCalls) {
            result.add(asyncCall.get());
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * 待进行请求的任务数
     */
    public synchronized int queuedCallsCount() {
        return readyAsyncCalls.size();
    }

    /**
     * 处于请求过程中的任务数
     *
     * @return
     */
    private synchronized int runningCallsCount() {
        return runningAsyncCalls.size() + runningSyncCalls.size();
    }

}
