/** Internal entity
  * - intrinsic lock / monitor lock / "monitor"
  * 
  * Access
  * + aquire
  * + release
  *   - hp-bf
  */

/** Locks in sync-methods
  * 
  * By aquiring
  * 
  * + lock the *object* owning the invoked sync-method
  * 
  * + lock the *static fields* of the class owning the invoked static
  * 
  * With sync-method
  * 
  * + Release by return or exception
  */

/** Sync statements
  * {{{
  * public vois addName(String name) {
  *   synchronized(this) {
  *     lastName = name; nameCount++;
  *   }
  *   nameList.add(name) // should not lock invoking outer stuff
  * }
  * }}}
  */

/** Fine-graind sync
  + Try ensuring the safety!
  */

trait Lunch {
  def inc1(): Unit
  def inc2(): Unit
}

case class MsLunch(var c1: Long, var c2: Long) extends Lunch {
  val lock1 = new Object
  val lock2 = new Object
  override def inc1() { lock1 synchronized {Thread sleep 10; c1 += 1} }
  override def inc2() { lock2 synchronized {Thread sleep 10; c2 += 1} }
}

case class MsLunchCoarse(var c1: Long, var c2: Long) extends Lunch {
  override def inc1() { synchronized {Thread sleep 10; c1 += 1} }
  override def inc2() { synchronized {Thread sleep 10; c2 += 1} }
}


/** Shared objects */
val mFine = MsLunch(0L, 0L)
val mCoarse = MsLunchCoarse(0L, 0L) // spending ca. 2x long time

val nIncTimes = 5
val nThreads = 100

/** Logic */
case class Tweak1(val m: Lunch) extends Runnable {
  override def run() { 0 until nIncTimes foreach (_ => m.inc1()) }
}
case class Tweak2(val m: Lunch) extends Runnable {
  override def run() { 0 until nIncTimes foreach (_ => m.inc2()) }
}


/** Time */
import java.lang.System.{currentTimeMillis}


val tsFine: Seq[Thread] = 0 until nThreads map (i =>
  new Thread(
    if (1 == i % 2) Tweak1(mFine) else Tweak2(mFine)))

val t0 = currentTimeMillis
tsFine foreach {_.start}
tsFine foreach {_.join}
println {s"Using fine lock: ellapsed ${currentTimeMillis - t0}ms"}
/** Should both print 250. */
println(mFine.c1)
println(mFine.c2)


val tsCoarse: Seq[Thread] = 0 until 100 map (i =>
  new Thread(
    if (1 == i % 2) Tweak1(mCoarse) else Tweak2(mCoarse)))

val t1 = currentTimeMillis
tsCoarse foreach {_.start}
tsCoarse foreach {_.join}
println {s"Using coarse lock: ellapsed ${currentTimeMillis - t1}ms"}
/** Should both print 250. */
println(mCoarse.c1)
println(mCoarse.c2)


/** Results

Using fine lock: ellapsed 2546ms
250
250
Using coarse lock: ellapsed 5081ms
250
250

  */


/** Remark
  * 
  * Reentrant sync
  * - prevent self-block
  */
