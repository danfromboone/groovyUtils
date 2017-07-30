package com.dcandersen.groovyUtils.iterators;

interface WrappingIterator extends Iterator {

    void setInnerIterator(Iterator iterator)

}
