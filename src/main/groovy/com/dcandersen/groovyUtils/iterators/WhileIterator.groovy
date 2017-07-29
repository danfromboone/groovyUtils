package com.dcandersen.groovyUtils.iterators

import org.apache.commons.collections4.iterators.PeekingIterator

class WhileIterator implements Iterator{

    PeekingIterator peekingIterator
    Closure closure


    WhileIterator(Iterator innerIterator, Closure closure){
        this.peekingIterator = new PeekingIterator(innerIterator)
        this.closure=closure
    }

    @Override
    boolean hasNext() {
        Object next

        try{
            next = peekingIterator.peek()
        }catch(NoSuchElementException e){
            return false
        }

        boolean isOk = closure(next)

        if (isOk){
            return true
        }else{
            return false
        }

    }

    @Override
    Object next() {
       if (hasNext()){
           return peekingIterator.next()
       }
    }
}
