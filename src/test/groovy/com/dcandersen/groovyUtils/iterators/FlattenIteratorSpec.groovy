package com.dcandersen.groovyUtils.iterators

import groovy.transform.ToString
import spock.lang.Specification

@ToString
class FlattenIteratorSpec extends Specification {

    def "it works"() {
        setup:

        Iterator innterIterator = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11]].iterator()

        FlattenIterator flattenIterator = new FlattenIterator(innterIterator)

        when:
        def collectedItems = flattenIterator.collect()

        then:

        collectedItems == [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

    }

}
