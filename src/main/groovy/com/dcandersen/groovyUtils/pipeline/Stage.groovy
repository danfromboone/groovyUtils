package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong


class Stage {

    String name
    ExecutorService executorService
    Stage nextStage
    Processor processor
    BlockingQueue queue
    Runnable runnable

    AtomicLong completedTaskCount = new AtomicLong(0)
    int threadPoolSize
  //  int keepAliveTime = 1
   // TimeUnit keepAliveTimeUnit = TimeUnit.SECONDS

    Stage(String name, int threadPoolSize, Processor processor ){

      //  BlockingQueue threadPoolQueue = new SynchronousQueue()
        //this.executorService = java.util.concurrent.Executors.newCachedThreadPool()
        this.executorService = java.util.concurrent.Executors.newFixedThreadPool(threadPoolSize)
       // this.executorService =  new ThreadPoolExecutor(threadPoolSize,threadPoolSize+10,keepAliveTime,keepAliveTimeUnit, threadPoolQueue)
        this.processor = processor
        this.name=name
        this.queue = new SynchronousQueue()
        this.threadPoolSize=threadPoolSize

        runnable = new Runnable() {


            @Override
            void run() {
                while (true) {

                    Object element = queue.take()

                    if (element in ShutdownSignal){
                        break
                    }

                    Object nextElement = processor.process(element)
                    completedTaskCount.incrementAndGet()
                    if (nextStage) nextStage.put(nextElement)

                }
            }
        }

        (1..threadPoolSize).each{
            this.executorService.submit(runnable)
        }


    }

    void put(Object element){
        this.queue.put(element)
    }





    boolean isActive(){
        !executorService.isTerminated()
    }

    void shutdown(){
        (1..threadPoolSize).each{
            queue.put(new ShutdownSignal())
        }

        executorService.shutdown()
        executorService.awaitTermination(1,TimeUnit.DAYS)
        println "$name is shutdown"
    }


    Map status(){
        [
                name:name,
                completedTaskCount:completedTaskCount.get(),
                queueSize:queue.size(),
                activeCount:executorService.activeCount,
                poolSize:executorService.poolSize,
                largestPoolSize:executorService.largestPoolSize

        ]
    }




}
