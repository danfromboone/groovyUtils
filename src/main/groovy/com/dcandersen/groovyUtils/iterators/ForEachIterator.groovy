package com.dcandersen.groovyUtils.iterators

class ForEachIterator implements WrappingIterator {

    Iterator innerIterator
    Closure closure

    ForEachIterator(Iterator innerIterator = null, Closure closure) {
        this.innerIterator = innerIterator
        this.closure = closure
    }

    @Override
    boolean hasNext() {
        innerIterator.hasNext()
    }

    @Override
    Object next() {

        Object o = innerIterator.next()
        closure(o)
        o
    }

}
