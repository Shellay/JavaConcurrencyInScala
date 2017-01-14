case class Counter(var c: Int) {
  def inc() { c += 1 }
  def get = c
}

/* remark
 (+=) operation is decomposable into
 * fetch current value of c
 * calc (c + 1)
 * store (c + 1) back to c's location
*/

/* e.g.
 interference of thread A and B with steps:
 1. A: fetch c
 2. B: fetch c
 3. A: calc (c + 1), i.e. c'
 4. B: calc (c - 1), i.e. c''
 5. A: store value of c' to location of c
 6. B: store value of c'' to location of c
 
 As a result, the effective value of c' is lost
 */

object Main {

  // shared counter
  val c = Counter(0)

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
