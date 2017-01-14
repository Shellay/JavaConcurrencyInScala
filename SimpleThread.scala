object SimpleThread {

  def threadMessage(message: String): Unit = {
    val threadName = Thread.currentThread.getName
    println(s"${threadName}, ${message}")
  }

  class MessageLoop extends Runnable {

    @throws[InterruptedException]
    override def run() {
      val infos = Array(
        "3 potatoes left",
        "2 potatoes left",
        "1 potatoes left",
        "All potatoes eaten"
      )
      try {
        for (info <- infos) {
          Thread.sleep(3000)
          threadMessage(info)
        }
      } catch {
        case (e : InterruptedException) => println(s"Still potatoes left T_T")
        case (_ : Throwable) => println("error")
      }
    }
  }

  @throws[InterruptedException]
  def main(args: Array[String]) {

    val patience = 10000L

    threadMessage {"Starting MessageLoop thread"}

    val t = new Thread(new MessageLoop)
    val startTime = (new java.util.Date).getTime
    t.start()

    threadMessage {"Waiting for MessageLoop to finish..."}

    while (t.isAlive) {
      threadMessage {"Still waiting 1s more..."}
      t.join(1000)
      val currentTime = (new java.util.Date).getTime
      if (currentTime - startTime > patience &&
          t.isAlive) {
        threadMessage {"Tired of waiting!"}
        t.interrupt()
        t.join()
      }
    }

    threadMessage("Finally!")
  }
}
