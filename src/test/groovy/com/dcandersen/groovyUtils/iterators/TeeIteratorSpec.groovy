package com.dcandersen.groovyUtils.iterators

import spock.lang.Specification
import spock.util.mop.Use

class TeeIteratorSpec extends Specification {

    //todo improve this test.

    @Use(IteratorCategory)
    def "it works"(){

        List foo = Collections.synchronizedList([])

        when:

        def numbers = (1..100).iterator()
                .forEach{println "**** $it"}
                .tee(100){foo.add(it)}
                .collect()

        then:
        foo.sort() == (1..100)
        numbers == (1..100)

    }

}
