/** Guarded blocks

  - Begin by polling a condition which must hold before proceeding.
  
  */

object JoyRunner {
  var joy: Boolean = false
  def runWithJoy {
    // Loop guard - wastes time!
    while (!joy) {}
    println("Now running with joy!")
  }
  def makeJoy { joy = true }
}

println { "Start." }
new Thread(new Runnable {
  override def run { JoyRunner.runWithJoy }
}).start
println { "He is still not joy..." }
JoyRunner.makeJoy
println { "End." }


/**
  - More efficient with (wait) method for suspension.
  - Wait for notification.
  */

object WaitJoyRunner {
  var joy: Boolean = false
  def runWithJoy {
    this synchronized {
      while (!joy) {
        try {
          wait() // Registered as waiter for the lock on (this)!
                 // Release lock and wait for notification!
                 // Aquire lock after notification! Maybe blocked!
                 // Test while-condition again after notified!
        } catch {
          case (e: InterruptedException) => { println("Interrupted.")}}}
      println("Got joy! Running! ")
    }}
  def notifyJoy {
    this synchronized { // Waiters have released lock on (this),
                        // so not blocked by waiters!
      joy = true        // Safe mutation.
      notifyAll()       // Notify all waiters to proceed with their
                        // syncs.
    }}
}

println { Thread.sleep(1000) ; "" }
println { "Start. "}
new Thread(new Runnable {
  override def run { WaitJoyRunner.runWithJoy }}).start
println { "He should be waiting for joy..." }
WaitJoyRunner.notifyJoy
println { "End." }

