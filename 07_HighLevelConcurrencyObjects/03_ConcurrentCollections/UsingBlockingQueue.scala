import java.util.Random
import java.util.concurrent.{BlockingQueue, ArrayBlockingQueue, LinkedBlockingDeque}


case class Producer(
  val queue: BlockingQueue[String]
) extends Runnable {
  val gen = new Random
  override def run() {
    try {
      while (true) {
        val stf = produce
        Console println s"Producer: sending..."
        queue put stf
        println
      }
    } catch {
      case (e: InterruptedException) => Console println "Producer stop." 
    }
  }
  def produce: String = {
    val num = gen nextInt 3000
    println {s"Producer: making ${num}"}
    Thread sleep num
    s"$num"
  }
}

case class Consumer(
  val queue: BlockingQueue[String]
) extends Runnable {
  val gen = new Random
  override def run() {
    try {
      while (true) {
        val stf = queue.take
        println {s"Consumer: receiving"}
        Thread sleep (gen nextInt 3000)
        this consume stf
        println {}
      }
    } catch {
      case (e: InterruptedException) => { println {"Consumer stop."} }
    }
  }
  def consume(stf: String) {
    println {s"Consumer: consumed ${stf}"}
  }
}

val que = new LinkedBlockingDeque[String]
val pdr = new Thread(Producer(que))
val cmr = new Thread(Consumer(que))

pdr.start
Thread sleep 1000
cmr.start

Thread sleep 10000
pdr.interrupt
cmr.interrupt


println { s"Stuff rest in queue after interruption: ${que}" }
