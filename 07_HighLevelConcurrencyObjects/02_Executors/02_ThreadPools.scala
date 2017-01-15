/**
  * Most executor implementations in [[java.util.concurrent]] use
  * thread pools.
  *
  * Worker threads
  * + minimizes overhead of thread creation
  * 
  * Fixed thread pool
  * + termination and replacement
  * + internal queue of tasks
  * + degrade gracefully
  *   (not boomed with thread creation due to massive requests)
  *
  * Executors with [[newFixedThreadPool]] factory methods
  * + [[newCachedThreadPool]]
  * + [[newSingleThreadExecutor]]
  * + [[ScheduledExecutorService]]
  * + additional options with instances of
  *   + [[ThreadPoolExecutor]]
  *   + [[ScheduledThreadPoolExecutor]]
  */
