package com.dcandersen.groovyUtils.iterators

class CountingIterator implements WrappingIterator{

    CountingIterator(Iterator innerIterator){
        this.innerIterator=innerIterator
    }

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
