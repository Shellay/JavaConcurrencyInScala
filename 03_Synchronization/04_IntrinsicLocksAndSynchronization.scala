/** Internal entity
  - intrinsic lock / monitor lock / "monitor"

  Access
  + aquire
  + release
    - hp-bf
  */

/** Locks in sync-methods

  By aquiring

  + lock the *object* owning the invoked sync-method

  + lock the *static fields* of the class owning the invoked static
  sync-method

  Release by return or exception
  */

/** Sync statements

public vois addName(String name) {
  synchronized(this) {
    lastName = name; nameCount++;
  }
  nameList.add(name) // should not lock invoking outer stuff
}
  */

// Fine-graind sync
// + Try ensuring the safety!
case class MsLunch(var c1: Long, var c2: Long) {
  val lock1 = new Object
  val lock2 = new Object
  def inc1() { lock1 synchronized { Thread sleep 10 ; c1 += 1} }
  def inc2() { lock2 synchronized { Thread sleep 10 ; c2 += 1} }
}

case class MsLunchCoarse(var c1: Long, var c2: Long) {
  def inc1() { this synchronized { Thread sleep 10 ; c1 += 1 } }
  def inc2() { this synchronized { Thread sleep 10 ; c2 += 1 } }
}

object Fine {

  val m = MsLunch(0L, 0L)
  // val m = MsLunchCoarse(0L, 0L) // spending ca. 2x long time

  class Tweak1 extends Runnable {
    override def run() { (0 until 10) foreach (_ => m.inc1()) }
  }
  class Tweak2 extends Runnable {
    override def run() { (0 until 10) foreach (_ => m.inc2()) }
  }

  def main(args: Array[String]) {
    val ts: Seq[Thread] = (0 until 100) map (i =>
      new Thread(
        if (1 == i % 2)
          new Tweak1
        else
          new Tweak2))

    ts foreach {_.start}
    ts foreach {_.join}

    println(m.c1)
    println(m.c2)
  }
}


/** Reentrant sync
  
  - prevent self-block

  */
