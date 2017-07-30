package com.dcandersen.groovyUtils.iterators

import spock.lang.Specification

class CountingIteratorSpec extends Specification {

    def "it counts and passes through"() {
        setup:
        def innerIterator = [1, 2, 3, 4, 5, 6].iterator()
        CountingIterator countingIterator = new CountingIterator(innerIterator)

        when:
        def entities = countingIterator.collect()

        then:
        entities == [1, 2, 3, 4, 5, 6]
        countingIterator.count == 6

    }

}
