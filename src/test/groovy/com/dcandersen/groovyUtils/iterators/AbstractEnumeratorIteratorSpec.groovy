package com.dcandersen.groovyUtils.iterators;

import spock.lang.Specification;

public class AbstractEnumeratorIteratorSpec extends Specification {

    def "works on elements"(){
        setup:
        AbstractImplementsHasNextIterator abstractEnumeratorIterator = new AbstractImplementsHasNextIterator() {

            Iterator i = [1,2,3,4,5].iterator()

            @Override
            Object fetchNext() {
                i.next()
            }
        }

        when:
        def collectedNumbers = abstractEnumeratorIterator.collect()


        then:
        collectedNumbers == [1,2,3,4,5]



    }


    def "works on empty iterator"(){
        setup:

        AbstractImplementsHasNextIterator abstractEnumeratorIterator = new AbstractImplementsHasNextIterator() {

            Iterator i = [].iterator()

            @Override
            Object fetchNext() {
                i.next()
            }
        }

        when:
        def collectedNumbers = abstractEnumeratorIterator.collect()

        then:
        collectedNumbers == []

    }

}
