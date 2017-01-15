/** The package [[java.util.concurrent.atomic]]
  *
  * Atomic operations on single variables
  * + get/set like read/write on volatile
  * + hp-bf(x.set, x.get).
  * 
  */

/** Example: Counter
  */

import java.util.concurrent.atomic.AtomicInteger

case class SyncCounter(var c: Int = 0) {
  def inc() {synchronized { c += 1}}
  def get: Int = {c}
}

case class AtomicCounter(val c: AtomicInteger = new AtomicInteger(0)) {
  def inc() {c.incrementAndGet}
  def get: Int = {c.get}
}
