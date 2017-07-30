package com.dcandersen.groovyUtils.iterators;

import spock.lang.Specification;

/*
    An example of how iterating works with Groovy

 */

public class ChainingSpec extends Specification {

    def "does the right thing"() {

        setup:

        use(IteratorCategory) {

            def array = [[1, 2, 3], [4, 5, 6], [7, 8, 9]].iterator()
                    .forEach { println it }
                    .flatten()
                    .forEach { println it }
                    .filter { it != 4 }.transform { it * 10 }
                    .forEach { println "* " + it }
                    .forEach {
                if (it == 70) {
                    throw new RuntimeException()
                }
            }
                    .onException(RuntimeException) { println it }
                    .collect()

            println array

        }

        expect: true

    }

}
