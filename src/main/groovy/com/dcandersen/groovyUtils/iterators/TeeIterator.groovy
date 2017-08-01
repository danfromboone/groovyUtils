package com.dcandersen.groovyUtils.iterators

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

//todo use semaphore to block executor submission
//todo allow termination timeout to be configurable


class TeeIterator implements Iterator{

    Iterator innerIterator
    Closure closure
    ExecutorService threadPool

    TeeIterator(Iterator innerIterator, int threads, Closure closure){
        this.innerIterator=innerIterator
        this.closure=closure
        threadPool = Executors.newFixedThreadPool(threads)
    }

    boolean hasNext() {
        boolean hasNext = innerIterator.hasNext()

        if (!hasNext){
            threadPool.shutdown()
            threadPool.awaitTermination(1,TimeUnit.DAYS)
        }
        hasNext
    }

    Object next() {

        Object o = innerIterator.next()

        Runnable runnable = new Runnable() {

            @Override
            void run() {
                closure(o)
            }
        }


        closure.curry(o)

        threadPool.execute(runnable)

        o
    }
}
