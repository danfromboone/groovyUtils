package com.dcandersen.groovyUtils.iterators

import org.apache.commons.collections4.iterators.FilterIterator
import org.apache.commons.collections4.iterators.TransformIterator

import java.util.stream.Stream
import java.util.stream.StreamSupport

class IteratorCategory {

    static ForEachIterator forEach(Iterator iterator, Closure closure) {
        new ForEachIterator(iterator, closure)
    }

    static FilterIterator filter(Iterator iterator, Closure closure) {
        new FilterIterator(iterator, closure)
    }

    static FlattenIterator flatten(Iterator iterator) {
        new FlattenIterator(iterator)
    }

    static TransformIterator transform(Iterator iterator, Closure closure) {
        return new TransformIterator(iterator, closure)
    }

    static OnExceptionIterator onException(Iterator iterator, Class exceptionClass, Closure closure) {
        return new OnExceptionIterator(iterator, exceptionClass, closure)
    }


    static TeeIterator tee(Iterator innerIterator,int threads, Closure closure){
        new TeeIterator(innerIterator,threads,closure)
    }

    static Iterable toIterable(Iterator iterator) {
        new Iterable() {
            @Override
            Iterator iterator() {
                return iterator
            }
        }
    }


    static WhileIterator doWhile(Iterator iterator, Closure closure) {
        new WhileIterator(iterator, closure)
    }

    static UntilIterator doUntil(Iterator iterator, Closure closure) {
        new UntilIterator(iterator, closure)
    }

    static Stream toStream(Iterator iterator, boolean parallel) {

        Iterable iterable = toIterable(iterator)

        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    static WrappingIterator next(Iterator iterator, WrappingIterator wrappingIterator) {
        wrappingIterator.innerIterator = iterator
        wrappingIterator
    }

    static void drain(Iterator iterator) {
        iterator.forEachRemaining({})
    }


}
