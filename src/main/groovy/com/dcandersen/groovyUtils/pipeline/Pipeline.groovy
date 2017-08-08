package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.TimeUnit

class Pipeline {

    List<Stage> stages = []

    Pipeline stage(Stage stage){

        Stage previousStage

        if (stages.size()>0){
            previousStage = stages.last()
            previousStage.nextStage = stage
        }



        //stage.nextStage=previousStage
        stages.add(stage)

        this
    }

    Pipeline stage(int maximumPoolSize,int queueSize, Processor processor){

        Stage stage = new Stage(maximumPoolSize,queueSize,processor)
        this.stage(stage)
    }

    void setStages(List stages){

        int lastIndex = stages.size()-1
        stages.eachWithIndex{ Stage stage, int index ->
            if (index < lastIndex){
                stage.nextStage = stages[index+1]
            }
        }
        this.stages = stages

    }


    boolean isActive(){
        stages.any{it.isActive()}
    }
    void shutdown(){

        stages.each{
            it.shutdown()
        }

    }

}
