/**
  * sync idioms
  * + sync methods
  * + sync statements
  */

/**
  With (synchronized) keyword in Java or (this synchronized {}) method
  in Scala, two effects got:

  + When one is executing the sync-method, others block until release.

  + When one exits, a hp-bf relation is established with *any* subsequent
    invocation of a sync-method for the same object.
    - Result: state changes are *visible* to all.
  */

/**
  Constructor is not sync-ed since construction process should be only
  done by a single thread.

  Warning:
  ! When adding a instance being constructed to a shared list in outer
  scope??
    - Other threads may corrupt it!
  */

/**
  * Liveness problem
  */

case class SynchronizedCounter(var c: Int) {
  def inc() {
    this synchronized {
      c += 1
    }}
  def get = c
}


object Main {

  // shared counter
  val c = SynchronizedCounter(0)

  class TweakCounter extends Runnable {
    override def run() { (0 until 100) foreach (_ => c.inc()) }
  }

  def main(args: Array[String]) {
    val twks = 0 until 10 map (_ => new Thread(new TweakCounter))

    twks foreach (_.start)
    twks foreach (_.join)

    println(s"Counter value should be ${10 * 100}.")
    println(s"But it is indeed ${c.get}!")
  }

}
