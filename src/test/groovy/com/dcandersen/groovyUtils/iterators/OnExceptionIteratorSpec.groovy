package com.dcandersen.groovyUtils.iterators

import spock.lang.Specification
import spock.util.mop.Use

class OnExceptionIteratorSpec extends Specification {

    @Use(IteratorCategory)
    def "results contain all elements except the one that blew up"(){
        setup:
        Exception throwThisException = new IllegalArgumentException('barf')
        Exception caughtException

        when:
        List results = [1,2,3,4,5,6,7,8,9].iterator()
                .forEach{ if (it==6) throw throwThisException}
                .onException(IllegalArgumentException){caughtException=it}
                .collect()

        then:
        results == [1,2,3,4,5,7,8,9]
        !results.contains(6)
        caughtException == throwThisException

    }

    @Use(IteratorCategory)
    def "other exceptions blow up the pipeline"(){
        setup:
        Exception throwThisException = new IllegalArgumentException('barf')
        Exception caughtException

        when:
        List results = [1,2,3,4,5,6,7,8,9].iterator()
                .forEach{ if (it==6) throw throwThisException}
                .onException(NullPointerException){caughtException=it}
                .collect()

        then:
        thrown(IllegalArgumentException)
        results == null

    }

}
