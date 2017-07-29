package com.dcandersen.groovyUtils.iterators


class IgnoreErrorIterator implements Iterator{

    Iterator innerIterator

    IgnoreErrorIterator(Iterator innerIterator){
        this.innerIterator=innerIterator
    }

    @Override
    boolean hasNext() {
        innerIterator.hasNext()
    }

    @Override
    Object next() {
        try{
            innerIterator.next()
        }catch (NoSuchElementException noSuchElementException){
            //do nothing
        }catch (Exception e){
            println "Exception: e"
            next()
        }

    }
}
