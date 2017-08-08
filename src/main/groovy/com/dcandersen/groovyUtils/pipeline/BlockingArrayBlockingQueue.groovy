package com.dcandersen.groovyUtils.pipeline

import java.util.concurrent.ArrayBlockingQueue

class BlockingArrayBlockingQueue extends ArrayBlockingQueue{


    BlockingArrayBlockingQueue(int i) {
        super(i)
    }


    boolean offer(Object o){
        super.put(o)
        true
    }

}
