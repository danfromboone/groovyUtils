package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.RejectedExecutionException
import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

//delete this

public class BoundedExecutor {
    private final ExecutorService exec = Executors.newCachedThreadPool();
    private final Semaphore semaphore;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor()

//   ThreadFactory threadFactory = new Th
//    eadFactoryBuilder()

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
                        if (o == null) {

                            if (nextStage) nextStage.submit(null)
                            println 'Shutting down'
                            exec.shutdown()
                        }else{
                            Object nextValue = closure(o)
                            if (nextStage) nextStage.submit(nextValue)
                        }

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

    public awaitTermintation(){
        exec.awaitTermination(1,TimeUnit.DAYS)
    }
}