/** An example of shared counter.
  */

case class Counter(var c: Int) {
  def inc() { c += 1 } // Non-atomic operation.
  def get = c
}

/* Remark

 (+=) operation is decomposable into
 + fetch current value of c
 + calc (c + 1)
 + store (c + 1) back to c's location

*/

/* e.g.

 interference of thread A and B with steps:
 1. A: fetch c
 2. B: fetch c
 3. A: calc (c + 1), i.e. u
 4. B: calc (c - 1), i.e. v
 5. A: store value of u to location of c
 6. B: store value of v to location of c
 
 As a result, the effective value of c' is lost.

 */

// Shared counter
val c = Counter(0)

val nRunCounts = 100
val nThreads = 10

class TweakCounter extends Runnable {
  override def run() { 0 until nRunCounts foreach (_ => c.inc()) }
}

val twks = 0 until nThreads map (_ => new Thread(new TweakCounter))

twks foreach (_.start)
twks foreach (_.join)

println(s"Counter value should be ${nRunCounts * nThreads}.")
println(s"But it is indeed ${c.get}!")


/** Sample output (may be different each time)
  * 
  * Counter value should be 1000.
  * But it is indeed 749!
  */
