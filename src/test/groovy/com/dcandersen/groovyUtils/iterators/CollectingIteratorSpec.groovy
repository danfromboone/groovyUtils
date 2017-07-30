package com.dcandersen.groovyUtils.iterators

import spock.lang.Specification

class CollectingIteratorSpec extends Specification {

    def "it collects"() {
        setup:
        Iterator innerIterator = (97..106).iterator()
        CollectingIterator collectingIterator = new CollectingIterator()
        collectingIterator.innerIterator = innerIterator

        expect:
        collectingIterator.collectedList == []

        when:
        collectingIterator.next()

        then:
        collectingIterator.collectedList == [97]

        when:
        collectingIterator.next()

        then:
        collectingIterator.collectedList == [97, 98]

        when:
        collectingIterator.next()

        then:
        collectingIterator.collectedList == [97, 98, 99]

        when:
        collectingIterator.each {} //drain the iterator

        then:
        collectingIterator.collectedList == (97..106)


    }

}
