package com.dcandersen.groovyUtils.iterators

abstract class AbstractImplementsHasNextIterator implements Iterator {

    private Object nextElement = null
    private boolean hasNext = false
    private boolean initialized = false

    /*
        throw NoSuchElementException on end of stream

     */

    abstract protected Object fetchNext() throws NoSuchElementException;

    @Override
    boolean hasNext() {
        if (!initialized) {
            lookAhead()
        }
        return hasNext

    }

    private void lookAhead() {

        initialized = true

        try {
            nextElement = fetchNext()
            hasNext = true
        } catch (NoSuchElementException e) {
            nextElement = null
            hasNext = false
        }

    }

    Object next() {

        if (!initialized) {
            lookAhead()
            return next()
        }

        if (initialized && !hasNext) {
            throw new NoSuchElementException()
        }

        Object retVal = nextElement

        lookAhead()

        return retVal
    }
}
