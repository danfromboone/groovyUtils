package com.dcandersen.groovyUtils.iterators

class EnumeratorIterator extends AbstractImplementsHasNextIterator{

    Iterator i = [1,2,3,4,5].iterator()

    @Override
    Object fetchNext() {
        i.next()
    }
}
