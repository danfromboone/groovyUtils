package com.dcandersen.groovyUtils.pipeline

import spock.lang.Specification

class PipelineSpec extends Specification {

    def "pipeline"(){
        setup:

        Pipeline pipeline = new Pipeline()
        .stage(new Stage(2,3,{println "1 $it";it}))
        .stage(3,5,{println "2 $it";it*10})
        .stage(5,7,{it-1})
        .stage(4,5,{println "4 $it";it})

        when:
        (1..10000).each{
            pipeline.stages[0].next(it)
        }

        then:
        pipeline.stages.size()==4
        pipeline.stages[0].nextStage == pipeline.stages[1]

    }

    def "pipelinewith list of stages"(){
        setup:

        Pipeline pipeline = new Pipeline()
        pipeline.stages=[
                new Stage('a',100,200,30000,{
                  //  println "1 $it"
                    it
                }),
                new Stage('b',100,300,1,{
                 //   println "2 $it";
                    sleep 100;
                    it
                }),
                new Stage('c',100,500,100,{
                  //  println "3 $it";
                    sleep 100
                    it
                }),
                new Stage('d',100,100,300,{
                  //  println "4 $it"
                    sleep 100
                    it
                })
        ]


       // sleep 1000

        when:
        long startTime = System.currentTimeMillis()
        (1..1000).each{
            pipeline.stages[0].next(it)
        }
        pipeline.stages[0].next(null)


       while(pipeline.stages.any{it.isActive()}) {
            pipeline.stages.each{it.printStatus()}
            println ''
            sleep 100
        }


        //sleep 10000
        pipeline.waitToComplete()

        long endTime = System.currentTimeMillis()

       // sleep 10000

        println pipeline.stages.each{
            println it.executorService
        }
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
