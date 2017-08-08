package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Executors {

    static ThreadPoolExecutor blockingExecutor(int corePoolSize,int maximumPoolSize, int queueSize){

      //  int corePoolSize = corePoolSize
       // int minimumPoolSize = maximumPoolSize
        long keepAliveTime = 1 //how long an idle thread lives
        TimeUnit timeUnit = TimeUnit.MILLISECONDS //how long an idle thread lives

        //BlockingQueue blockingQueue = new ArrayBlockingQueue(queueSize)
        BlockingQueue blockingQueue = new BlockingArrayBlockingQueue(queueSize)

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,timeUnit, blockingQueue)

        threadPoolExecutor
    }
}
