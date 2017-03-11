import scala.math
import scala.collection.mutable.ListBuffer

object Perf {
    def main(args: Array[String]) {
        // http://stackoverflow.com/a/4194408/2066736
        var nRange = Iterator.iterate(1)(_ * 10) takeWhile (_ <= 1e4.toInt)
        val times = printTimes(nRange, 5, (n) => () => sieve(n))
    }

    // Sieve of Eratosthenes
    // pseudo code from https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
    def sieve (n: Integer) = {
        // Let A be an array of Boolean values, indexed by integers 2 to n,
        // initially all set to true.
        var A = new ListBuffer[Boolean]()
        var i = 2
        for (i <- 2 to n) {
            A += true
        }

        // for i = 2, 3, 4, ..., not exceeding √n:
        // if A[i] is true:
        //    for j = i^2, i^2+i, i^2+2i, i^2+3i, ..., not exceeding n:
        //      A[j] := false.
        i = 2
        for ( i <- 2 to Math.sqrt(n.toDouble).toInt ) {
            if(A(i - 2)) {
                var j = 0
                for ( j <- i * i to n by i ) {
                    A(j - 2) = false
                }
            }
        }
        

        // Output: all i such that A[i] is true.
        A.zipWithIndex.filter{case (b, i) => b }.map{ case (b, i) => i + 2 };
    }

    def printMeanAndStandardDev (times : ListBuffer[Double], n : Integer) = {
        val count = times.size
        val mean = times.sum / count
        val devs = times.map(time => (time - mean) * (time - mean))
        val stddev = Math.sqrt(devs.sum / (count - 1))
        println(f"The mean for size n=$n is $mean milliseconds and the standard deviation is $stddev milliseconds")
    }

    def printTimes ( nIter: Iterator[Int], iters: Integer, func: (Integer) => () => Any ) = {
        var n = 0
        for ( n <- nIter ) {
            var times = ListBuffer[Double]()
            for( i <- 1 to iters ) {
                times += doPerf(func(n))
            }
            printMeanAndStandardDev(times, n)
        }
    }

    // time is returned in milliseconds
    def doPerf (func: () => Any) = {
        val t0 = System.nanoTime()
        func();
        val t1 = System.nanoTime()
        (t1 - t0) * 1e-6
    }
}
