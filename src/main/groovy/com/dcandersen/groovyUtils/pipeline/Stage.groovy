package com.dcandersen.groovyUtils.pipeline


import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit


class Stage {

    String name

    ThreadPoolExecutor executorService

    Stage nextStage

    Processor processor

    Stage(String name, int corePoolSize, int maximumPoolSize,int queueSize, Processor processor ){
        executorService = Executors.blockingExecutor(corePoolSize,maximumPoolSize,queueSize)
        this.processor = processor
        executorService.allowCoreThreadTimeOut(true)
      //  executorService.prestartAllCoreThreads()
        this.name=name
    }

    void next(Object element){

       Runnable runnable = new Runnable() {
           @Override
           void run() {
                try {
                    Object nextElement = null

                    if (element != null) {
                        nextElement = processor.process(element)
                    }

                    if (nextStage) nextStage.next(nextElement)

                }catch (Throwable t){
                    t.printStackTrace()
                    throw t
                }

           }
       }


       executorService.submit(runnable)


    }

    boolean isActive(){
       // true
        executorService.queue.size()>0 || executorService.poolSize>0 || executorService.activeCount>0
    }

    void shutdown(){

        while(isActive()){
            sleep 100
        }

        executorService.shutdown()
        executorService.awaitTermination(1,TimeUnit.DAYS)
    }


    void printStatus(){
        println "$name ${executorService.activeCount} ${executorService.queue.size()} ${executorService.completedTaskCount} ${executorService.poolSize}"
//        println "$name activeCount=${executorService.activeCount}"
//        println "$name queueSize=${executorService.queue.size()}"
//        println "$name completedCount=${executorService.completedTaskCount}"
//        println "$name poolSize=${executorService.poolSize}"

    }


}
