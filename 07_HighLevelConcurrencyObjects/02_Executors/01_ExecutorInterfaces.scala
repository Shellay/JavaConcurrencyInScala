/** Executor interfaces
  */

/** The Executor Interface
  * 
  * Single method [[execute]]
  * + [[new Thread(r).start()]] -> [[e.execute(r)]]
  *   where (r: Runnable), (e: Executor)
  * + Possible executions may
  *   + use existing worker thread to run [[r]]
  *   + place [[r]] in a queue
  *
  * + More advanced implementations in [[java.util.concurrent]]
  *   + [[ExecutorService]]
  *   + [[ScheduledExecutorService]]
  */

/** The ExecutorService Interface
  *
  * [[ExecutorService]] with
  * + [[submit]] accepts [[Callable]]
  *   + allows to return value
  *   + [[Future]]
  * + shutdown through [[interrupt]]
  */

/** The Scheduledexecutorservice Interface
  * 
  * [[ExecutorService]] with [[schedule]]
  * + fixed rate
  * + fixed delay
  * 
  */
