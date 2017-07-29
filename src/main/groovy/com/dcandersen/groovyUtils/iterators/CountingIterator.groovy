package com.dcandersen.groovyUtils.iterators

class CountingIterator implements WrappingIterator{

    Iterator innerIterator
    long count = 0

    @Override
    boolean hasNext() {
        innerIterator.hasNext()
    }

    @Override
    Object next() {
        Object o = innerIterator.next()
        count++
        o
    }
}
