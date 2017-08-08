package com.dcandersen.groovyUtils.pipeline

import spock.lang.Specification

class StageSpec extends Specification {

    def "works"(){
        setup:

        Stage stage3 = new Stage(
                executorService: Executors.blockingExecutor(3,2),
                processor: {println it;it},
                nextStage: null
        )

        Stage stage2 = new Stage(
                executorService: Executors.blockingExecutor(3,2),
                processor: {it-1},
                nextStage: stage3
        )

        Stage stage1 = new Stage(
                executorService: Executors.blockingExecutor(3,2),
                processor: {it*10},
                nextStage: stage2
        )

        when:
        (1..1000).each{
            stage1.next(it)
        }

        stage1.next(null)

       // sleep 10000

        then:
        true

    }
}
