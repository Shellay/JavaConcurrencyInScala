import java.util.Random

case class Drop(var message: String, var empty: Boolean) {
  def take: String = {
    this synchronized {
      while (empty) {
        try {
          wait
        } catch {
          case (e: InterruptedException) => {}}}
      empty = true
      notifyAll
      message
    }}

  def put(message: String) {
    this synchronized {
      while (!empty) {
        try {
          wait
        } catch {
          case (e: InterruptedException) => {}}}
      empty = false
      this.message = message
      notifyAll
    }}
}

case class Producer(val drop: Drop, val infos: List[String]) extends Runnable {
  override def run() {
    val gen = new Random
    for (info <- infos) {
      drop put info
      try {Thread sleep { gen nextInt 5000 }
      } catch {case (e: InterruptedException) => {}}}
    drop put "DONE"
  }
}

case class Consumer(val drop: Drop) extends Runnable {
  override def run() {
    val gen = new Random
    while (true) {
      drop.take match { // sleep until (!drop.empty) and notified.
        case "DONE" => return
        case msg => {
          println(s"RECEIVED: ${msg}")
          try {
            Thread sleep { gen nextInt 5000 }}
          catch {
            case (e: InterruptedException) => {}}}}}
  }
}

// main
object ProductAndConsumer {
  def main(args: Array[String]) {
    val infos = List("Mary", "has", "a", "little", "lamb", "!")
    val drop = Drop("BOTTOM", true)

    new Thread(Producer(drop, infos)).start
    new Thread(Consumer(drop)).start
  }
}
