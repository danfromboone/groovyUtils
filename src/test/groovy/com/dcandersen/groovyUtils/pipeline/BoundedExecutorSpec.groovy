package com.dcandersen.groovyUtils.pipeline

import spock.lang.Specification

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class BoundedExecutorSpec extends Specification {

    def "it works"(){
        setup:
        //ExecutorService e = Executors.newCachedThreadPool()
        BoundedExecutor be = new BoundedExecutor(3)

        //ExecutorService e2 = Executors.newCachedThreadPool()
        BoundedExecutor be2 = new BoundedExecutor(3)

        //Runnable r = {sleep 1000; println it} as Runnable

        be.closure = {println it; it*10}
        be2.closure = {println it; }

        be.nextStage=be2

        (1..10).each{
            be.submit(it)
        }

        be.submit(null)

        //be.submitTask(r)
        //be.submitTask(r)

        be.awaitTermintation()
        be2.awaitTermintation()

    }

    def "ThreadPoolExecutor stuff"(){

        setup:

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            Thread newThread(Runnable runnable) {
                    Thread t = new Thread(runnable)
                    t.setName('foo')
                    t
            }
        }

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1,3,1,TimeUnit.DAYS, new SynchronousQueue<Runnable>(),threadFactory)

        Runnable r = {sleep 10000} as Runnable

        when:
        threadPoolExecutor.execute(r)
        threadPoolExecutor.execute(r)
        threadPoolExecutor.execute(r)
       // threadPoolExecutor.execute(r)

        then:
        threadPoolExecutor.
        true


    }
    def "blocking queue"(){

        setup:

       // SynchronousQueue q = new SynchronousQueue()
        ArrayBlockingQueue q = new ArrayBlockingQueue(2)
        //TransferQueue q = new LinkedTransferQueue() {}

        println q.put('foo')
        println q.put('bar')
        println q.put('zoo')  //wait for someone to receive


    }
    def "blocking executor"(){
        setup:

        //BlockingQueue q = new ArrayBlockingQueue(2)

//2        def q = new BlockingArrayBlockingQueue(2)
//        ThreadPoolExecutor threadPoolExecutor =
//                new ThreadPoolExecutor(1,3,1,TimeUnit.DAYS, q)

        ExecutorService e = Executors.blockingExecutor(1,2)

        Runnable r = {println 'foo';sleep 1000} as Runnable

        when:
        (1..10).each{
            e.execute(r)
            println 'ZOO'
        }

        then:
        true


    }



}
