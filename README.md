# groovyUtils
Helper Utilities for Groovy, including Iterators

## iterators

lots of helpers to enable streaming of nested Iterators

example:

```$xslt


        use(IteratorCategory){

            def array = [[1,2,3],[4,5,6],[7,8,9]].iterator() //get an iterator
                    .forEach {println it}  // print each one
                    .flatten() //flatten into array of sinlge elements
                    .forEach {println it}
                    .filter {it!=4}.transform {it*10} //multiply each by 10
                    .forEach {println "* " + it}
                    .forEach {if (it==70){throw new RuntimeException()}} // throw an exception if element == 70
                    .onException(RuntimeException){println it} // print out any RuntimeException, allowing pipeline to continue
                    .collect() // need to terminate the pipeline to start iteration.
                       // .drain() // use drain() to terminate pipeline without collecting

            println array


        }
        
        RESULTS:
        
        [1, 2, 3]
        1
        * 10
        2
        * 20
        3
        * 30
        [4, 5, 6]
        4
        5
        * 50
        6
        * 60
        [7, 8, 9]
        7
        * 70
        java.lang.RuntimeException
        8
        * 80
        9
        * 90
        [10, 20, 30, 50, 60, 80, 90]

```