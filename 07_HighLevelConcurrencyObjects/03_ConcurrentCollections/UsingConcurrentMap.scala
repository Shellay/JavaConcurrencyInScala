/** ConcurrentHashMap
  *
  * 
  */

import java.lang.System.{currentTimeMillis}
import java.util.Collections
import java.util.{HashMap}
import java.util.concurrent._

val N = 100000
val T = 2

val nMap = new HashMap[Int, Int]
val sMap = Collections synchronizedMap {new HashMap[Int, Int]}
val cMap = new ConcurrentHashMap[Int, Int]

var t = currentTimeMillis

for (m <- Seq(nMap, sMap, cMap)) {
  // put
  var ts = 0 until N map {i => new Thread(new Runnable {
    override def run() {
      m put (i, i+1)
    }})
  }
  t = currentTimeMillis
  ts foreach {_.start}
  ts foreach {_.join}

  // get
  ts = 0 until N map {i => new Thread(new Runnable {
    override def run() {
      val y = m get i
    }})
  }
  ts foreach {_.start}
  ts foreach {_.join}

  println {currentTimeMillis - t}
  println {s"size: ${m.size} - should be ${N}"}
}
