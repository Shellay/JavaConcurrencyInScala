/** Deadlock
  - Two or more threads blocked forever waiting for each other. 
*/

/** Example
  - Two friends obeying courtesy rules:
    + Bow to the other;
    + Not return until the other returns.
  */

object DeadLock {

  case class Friend(val name: String) {

    def bow(other: Friend) {
      this synchronized {
        println(s"I am ${name}, start bowing to ${other.name}...")
        other bowBack this
      }
    }

    def bowBack(other: Friend) {
      this synchronized {
        println(s"I am ${name}, starting bowing back to ${other.name}...")
      }
    }
  }

  def main(args: Array[String]) {
    val f1 = Friend("Alphonse")
    val f2 = Friend("Gaston")
    new Thread(new Runnable {
      override def run { f1 bow f2 }
    }).start
    /*
     * Thread locks f1 calling "bow", and try to aquire f2 calling
     * "bowBack"...
     
     * But f2 may already be locked by another thread, which is trying
     * to aquire f1
     */
    new Thread(new Runnable {
      override def run { f2 bow f1}
    }).start
  }

}
