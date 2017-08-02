package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

public class BoundedExecutor {
    private final ExecutorService exec = Executors.newCachedThreadPool();
    private final Semaphore semaphore;

    Closure closure //closure

    BoundedExecutor nextStage

    public BoundedExecutor(int bound) {
        //this.exec = exec;
        this.semaphore = new Semaphore(bound)
    }

    public void submit(Object o)
            throws InterruptedException, RejectedExecutionException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        def nextValue = closure(o)
                        if (nextStage) nextStage.submit(nextValue)
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

    public void submitTask(final Runnable command)
            throws InterruptedException, RejectedExecutionException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
            throw e;
        }
    }

    public shutdown(){
        exec.shutdown()
        exec.awaitTermination(1,TimeUnit.DAYS)
    }
}