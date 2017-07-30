package com.dcandersen.groovyUtils.iterators

import spock.lang.Specification
import spock.lang.Unroll

class ForEachIteratorSpec extends Specification {


    @Unroll
    def "it works - when #desc "(){
        setup:
        Iterator innerIterator = (1..233).iterator()
        ForEachIterator forEachIterator = new ForEachIterator(innerIterator,closure)

        when:
        def results = forEachIterator.collect()

        then:
        results == (1..233)

        where:
        desc | closure
        'assign it' | {it='foo'}
        'return something else' | {'bar'}
        'print it' | {println it}


    }

    @Unroll
    def "closure can mutate object - so be careful"(){
        setup:
        Iterator innerIterator =[
                [name:'bob'],
                [name:'fred'],
                [name:'orville']
        ].iterator()


        ForEachIterator forEachIterator = new ForEachIterator(innerIterator,{it.name='larry'})

        when:
        def results = forEachIterator.collect()

        then:
        results == [
                [name:'larry'],
                [name:'larry'],
                [name:'larry']
        ]



    }

}
