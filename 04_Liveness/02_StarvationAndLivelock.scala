/** Starvation and livelock
  
  * Much less common than deadlock
  */

/** Starvation

  - A thread is unable to gain regular access to shared resources and
  unable to make progress.

  - e.g.

    + Object (o) with long-time sync-method (o.m), sync on object (o.s)
    + Thread (t) invokes (o.m) frequently
    + Another thread (t1) need frequently invoke sync-method (o.n), sync on (o.s)
    + (t1) get blocked frequently
  */


/** Livelock

  - May be treated as special case of starvation (wikipedia)

  - State of one threads constantly change w.r.t. others.
  */
