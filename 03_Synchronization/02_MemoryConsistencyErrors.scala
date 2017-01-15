/** Memory Consistency Errors
  * 
  * + Different threads have inconsistent views of same data.
  * + "happens-before" relationship.
  *   + with sync
  * 
  */

/** Rules of "happens-before"
  * 
  * Given statements s, p, r, j; threads T, R, X; thread's exec statement T.s
  * // fork-consistency
  * + let s = T."R.start",
  *       forall X, X.p, hp-bf(X.p, s)
  *   =>
  *     forall R, R.r:
  *       hp-bf(X.p, R.r)
  * 
  * // join-consistency
  * + let T.r = T."return xxx",
  *       R.j = R."T.join"
  *   =>
  *     forall T.p:
  *     forall R.q, hp-bf(R.j, R.q):
  *       hp-bf(p, q)
  */

