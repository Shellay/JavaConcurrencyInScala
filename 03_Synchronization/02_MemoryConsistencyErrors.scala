/** Memory Consistency Errors 

  * different threads have inconsistent views of same data

  * happens-before relationship
  * * with sync

  Rules

  Given statements s, p, t; thread T, R

  // fork-consistency
  * s = (T.body."R.start"), forall p: hp-bf(p, s) =>
  *   forall r in R.body: hp-bf(p, r)

  // join-consistency
  * r = (T.body."return xxx"), j = (R.body.join(T)) =>
  *   forall p in T.body: forall q in R.body, j < q: hp-bf(p, q)

  */

