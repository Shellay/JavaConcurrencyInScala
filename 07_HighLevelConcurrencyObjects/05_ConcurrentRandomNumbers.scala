/** For applications using random numbers from multiple threads
  * 
  * + [[Math.random()]] issue
  * 
  * Usages like in [[ForkJoinTask]]
  */


/** HOWTO demonstrate the difference?
  * 
  * http://thoughtfuljava.blogspot.de/2012/09/prefer-threadlocalrandom-over-random.html
  */

/** Fault code...
  * Should use thread pool? Or better benchmarking methods?
  */

import java.lang.System.{currentTimeMillis}
import java.util.Random
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}
import java.util.concurrent.{Executors, ExecutorService}

val N = 100000

val q1 = new LinkedBlockingQueue[Double]
var ts = 0 until N map {_ => new Thread(new Runnable {
  override def run() {
    q1 put (Math.random)
  }})
}
var t = currentTimeMillis
ts foreach {_.start}
ts foreach {_.join}
println {currentTimeMillis - t}

val q2 = new LinkedBlockingQueue[Double]
ts = 0 until N map {_ => new Thread(new Runnable {
  override def run() {
    q2 put (ThreadLocalRandom.current.nextDouble)
  }})
}
t = currentTimeMillis
ts foreach {_.start}
ts foreach {_.join}
println {currentTimeMillis - t}

