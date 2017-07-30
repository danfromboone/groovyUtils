package com.dcandersen.groovyUtils.iterators

class CollectingIterator implements WrappingIterator {

    List collectedList = []
    Iterator innerIterator

    @Override
    boolean hasNext() {
        return innerIterator.hasNext()
    }

    @Override
    Object next() {
        Object o = innerIterator.next()
        collectedList.add(o)
        o
    }

}
