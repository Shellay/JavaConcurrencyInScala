/** Atomic access
  + happen completely XOR doesn't happen at all

  Atomic operations
  + Reads/Writes for most prim type vars (except long and double)
  + Reads/Writes for all volatile vars (including long and double)

  Memory consistency error still possible!

  Volatile -> happen-before relation
  - changes of volatile are ALWAYS visible
  
  Classes under (java.util.concurrent) provides atomic methods not
  relying on sync. - High level concurrency objects.

  */
