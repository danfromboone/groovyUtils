package com.dcandersen.groovyUtils.iterators;

import java.util.Iterator;


public interface WrappingIterator extends Iterator{

    void setInnerIterator(Iterator iterator);

}
