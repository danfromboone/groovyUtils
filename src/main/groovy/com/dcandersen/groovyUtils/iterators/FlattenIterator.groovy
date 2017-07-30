package com.dcandersen.groovyUtils.iterators

class FlattenIterator implements WrappingIterator{

    Iterator innerIterator
    List thisList = null

    FlattenIterator(Iterator innerIterator){
        this.innerIterator=innerIterator
    }

    @Override
    boolean hasNext() {
        if (thisList){
            return true
        }else{
            return innerIterator.hasNext()
        }
    }

    @Override
    Object next() {
        if (thisList){
            return thisList.pop()
        }else if (innerIterator.hasNext()){
            thisList = innerIterator.next().reverse()
            next()
        }else{
            throw new NoSuchElementException()
        }
    }


}

