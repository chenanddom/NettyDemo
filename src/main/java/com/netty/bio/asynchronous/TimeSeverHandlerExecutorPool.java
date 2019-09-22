package com.netty.bio.asynchronous;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeSeverHandlerExecutorPool {

private ExecutorService executor;

    public TimeSeverHandlerExecutorPool(int maxPoolSize,int queueSize) {
    executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                                        maxPoolSize,
                                        120L, TimeUnit.SECONDS,
                                        new ArrayBlockingQueue<Runnable>(queueSize));
    }
    public void execute(Runnable task){
        executor.submit(task);
    }
}
