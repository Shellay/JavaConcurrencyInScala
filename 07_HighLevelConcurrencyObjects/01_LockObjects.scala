/** Lock objects
  *
  * + More locking idioms with [[java.util.concurrenct.locks]]
  *
  * + [[Lock]] object
  *   + like implicit locks by [[synchronized]] code
  *   + Only one thread can own a [[Lock]] object at a time.
  *   + Supports [[wait]] and [[notify]] through [[Condition]] objects.
  *
  * + Advantage:
  *   + back out with [[tryLock]]
  *   + [[lockInterruptibly]]
  *
  */


/**
  * Solving deadlock problem
  * 
  * + Aquire both locks to proceed.
  */

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import java.util.Random

case class Friend(val name: String) {

  val lock: Lock = new ReentrantLock

  def impendingBow(bower: Friend): Boolean = {
    var thisLock: Boolean = false
    var thatLock: Boolean = false
    try {
      thisLock = this.lock.tryLock()
      thatLock = bower.lock.tryLock()
    } finally {
      if (! (thisLock && thatLock)) {
        if (thisLock) { this.lock.unlock() }
        if (thatLock) { bower.lock.unlock() }
      }
    }
    thisLock && thatLock
  }

  def acceptBowFrom(that: Friend) {
    if (this impendingBow that) {
      try {
        // [[this]] is accepting the bow from [[that]]
        println{"Round start: "}
        println{"###"}
        println{s"${name}: ${that.name} has bowed to me!"}
        println{s"${name}: Now I am bowing to him!"}
        println{"###"}
        // [[this]] calls [[that]] to accept bowing.
        that acceptBowBackFrom this
      } finally {
        this.lock.unlock()
        that.lock.unlock()
      }
    } else {
      println{s"${name}: ${that.name} started to bow to me, saw I was already bowing to him."}
    }
  }

  def acceptBowBackFrom(that: Friend) {
    /* Remark:
     *
     * When more than one [[println]] statements are placed in this
     * block, they may be interfered by [[println]] from another
     * thread executing [[acceptBowFrom]]
     */    
    // println{"###"}
    println{s"${name}: ${that.name} has bowed back to me!\n"}
    // println{"###"}
    // println(s"Round end.\n")
  }
}


case class BowLoop(val bower: Friend, val bowee: Friend) extends Runnable {
  @throws[InterruptedException]
  override def run() {
    val random = new Random
    while(true) {
      try {
        Thread sleep (random nextInt 10)
        // Thread sleep 1000
      } catch {
        case (e: InterruptedException) => {
          println {"Over."}
          return
        }}
      bowee acceptBowFrom bower
    }
  }
}

val a = Friend("Alphonse")
val b = Friend("Gaston")

val t1 = new Thread(BowLoop(a, b))
val t2 = new Thread(BowLoop(b, a))

t1.start
t2.start

println("Doing")

Thread sleep 5000

println {"Should stop."}
t1.interrupt
t2.interrupt
