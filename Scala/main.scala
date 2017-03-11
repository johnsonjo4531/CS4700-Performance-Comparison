import scala.math
import scala.collection.mutable.ListBuffer

object Perf {
    def main(args: Array[String]) {
        // http://stackoverflow.com/a/4194408/2066736
        var nRange = Iterator.iterate(1)(_ * 10) takeWhile (_ <= 1e6.toInt)
        val times = printTimes(nRange, 5, (n) => () => sieve(n))
        //println(doPerf(() => sieve(20)))
    }

    // Sieve of Eratosthenes
    // pseudo code from https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
    def sieve (n: Integer) = {
        // Let A be an array of Boolean values, indexed by integers 2 to n,
        // initially all set to true.
        val a = Array.fill(n + 1)(true)
        a(0)  = false
        a(1)  = false
        val k = scala.math.sqrt(n.toDouble).toInt

        // for i = 2, 3, 4, ..., not exceeding √n:
        // if A[i] is true:
        //    for j = i^2, i^2+i, i^2+2i, i^2+3i, ..., not exceeding n:
        //      A[j] := false.
        for (i <- 2 to k; if a(i)) 
            for (j <- i * i to n by i)
                a(j) = false
        

        // Output: all i such that A[i] is true.
        var primes = a.zipWithIndex.filter(_._1).map(_._2);
        primes
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
