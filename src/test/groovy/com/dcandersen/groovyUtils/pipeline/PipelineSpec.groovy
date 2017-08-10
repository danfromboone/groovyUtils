package com.dcandersen.groovyUtils.pipeline

import spock.lang.Specification

class PipelineSpec extends Specification {

    def "pipeline"(){
        setup:

        Pipeline pipeline = new Pipeline()
        .stage(new Stage('a',2,{println "1 $it";it}))
        .stage('b',5,{println "2 $it";it*10})
        .stage('c',7,{it-1})
        .stage('d',5,{println "4 $it";it})

        when:
        (1..10000).each{
            pipeline.stages[0].put(it)
        }

        then:
        pipeline.stages.size()==4
        pipeline.stages[0].nextStage == pipeline.stages[1]

    }

    def "pipelinewith list of stages"(){
        setup:


        Pipeline pipeline = new Pipeline()
        pipeline.stages=[
                new Stage('a',100,{
                  //  println "1 $it"
                    it
                }),
                new Stage('b',200,{
                 //   println "2 $it";
                    sleep 100;
                    it*3.14
                }),
                new Stage('c',200,{
                  //  println "3 $it";
                    sleep 100
                    it*10
                }),
                new Stage('d',300,{
               //     println "4 $it"
                    sleep 100
                    it
                })
        ]


       // sleep 1000

        when:
        long startTime = System.currentTimeMillis()
        (1..1000).each{
            pipeline.stages[0].put(it)
        }
//        pipeline.stages[0].put(null)


        pipeline.shutdown()
       while(pipeline.isActive()) {
           println pipeline.status()// pipeline.stages.each{it.printStatus()}
           // println ''
            sleep 100
        }

        println pipeline.status()

        //sleep 10000
      //  pipeline.shutdown()

        long endTime = System.currentTimeMillis()

       // sleep 10000

//        println pipeline.stages.each{
//            println it.executorService
//        }
        pipeline.stages.executorService.each{
            println '*****'
            println it.completedTaskCount
        }
       // pipeline.waitToComplete()

        println endTime - startTime

       // sleep 10000

        then:
        true
//        pipeline.stages.size()==4
//        pipeline.stages[0].nextStage == pipeline.stages[1]

    }
}
