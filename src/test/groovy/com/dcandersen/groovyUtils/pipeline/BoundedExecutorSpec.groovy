package com.dcandersen.groovyUtils.pipeline

import spock.lang.Specification

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
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

        //be.submitTask(r)
        //be.submitTask(r)

        be.shutdown()
        be2.shutdown()

    }

}
