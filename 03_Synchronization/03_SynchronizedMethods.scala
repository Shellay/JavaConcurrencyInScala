/**
  * Sync idioms
  * + sync methods
  * + sync statements
  */

/**
  * With (synchronized) keyword in Java or (this synchronized {}) method
  * in Scala, two effects got:
  * 
  * + When one is executing the sync-method, others block until release.
  * 
  * + When one exits, a hp-bf relation is established with *any* subsequent
  *   invocation of a sync-method for the same object.
  *   - Result: state changes are *visible* to all.
  */

/**
  * Constructor is not sync-ed since construction process should be only
  * done by a single thread.
  * 
  * Warning: when adding a instance being constructed to a shared list
  * in outer scope??
  *   - Other threads may corrupt it!
  */

/**
  * Liveness problem
  */

case class SynchronizedCounter(var c: Int) {
  // Using [[this.synchronized{}]] method
  def inc() { synchronized {c += 1} }
  def get = c
}


// Shared counter
val c = SynchronizedCounter(0)

val nRunCounts = 100
val nThreads = 10

class TweakCounter extends Runnable {
  override def run() { 0 until nRunCounts foreach (_ => c.inc()) }
}

val twks = 0 until nThreads map (_ => new Thread(new TweakCounter))

twks foreach (_.start)
twks foreach (_.join)

println(s"Counter value should be ${nRunCounts * nThreads}.")
println(s"With sync it is indeed ${c.get}!")


/** Sample output
  * 
  * Counter value should be 1000.
  * With sync it is indeed 1000!
  */
