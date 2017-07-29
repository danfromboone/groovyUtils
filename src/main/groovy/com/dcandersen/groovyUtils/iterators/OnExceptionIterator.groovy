package com.dcandersen.groovyUtils.iterators

import java.awt.print.Pageable


class OnExceptionIterator implements Iterator{

    Iterator innerIterator
    Class exceptionClass
    Closure closure

    OnExceptionIterator(Iterator innerIterator,Class exceptionClass, Closure closure){
        this.innerIterator=innerIterator
        this.exceptionClass=exceptionClass
        this.closure=closure
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
        }catch (Throwable t){

            if (t in exceptionClass){
                closure(t)
            }else{
                throw t
            }

            next()
        }

    }
}
