/** Fork/Join framework is implementation of [[ExecutorService]]
  *
  * Work broken into smaller pieces
  *
  * Work-stealing algorithm
  *
  * [[ForkJoinPool]] extending [[AbstractExecutorService]]
  * + execute [[ForkJoinTask]]
  */

/** Basic use
  * {{{
  if (my potion of the work is small enough)
    do the work directly
  else
    split my work into two pieces
    invoke the two pieces and wait for the results
  }}}
  *
  * Recursive task
  * Recursive action
  */

/** Example: blurring for clarity */

import java.util.concurrent.RecursiveAction
import java.util.concurrent.ForkJoinPool


case class ForkBlur(
  val mSource: Array[Int],
  val mStart: Int,
  val mLengh: Int,
  val mDestination: scala.collection.mutable.ArrayBuffer[Int],
  val mBlurWidth: Int = 15
) extends RecursiveAction {

  protected def computeDirectly() {
    val sidePixels = (mBlurWidth - 1) / 2
    for (idx <- Range(mStart, mStart + mLengh)) {
      var (rt, gt, bt) = (0f, 0f, 0f)
      for (mI <- Range(-sidePixels, sidePixels)) {
        val mIdx = Math.min (Math.max(mI + idx, 0), mSource.length - 1)
        val pixel = mSource(mIdx)
        rt += (((pixel & 0x00ff0000) >> 16) / mBlurWidth).toFloat
        gt += (((pixel & 0x0000ff00) >>  8) / mBlurWidth).toFloat
        bt += (((pixel & 0x000000ff) >>  0) / mBlurWidth).toFloat
      }
      val dPixel = {
        (0xff000000) | rt.toInt << 16 | gt.toInt <<  8 | bt.toInt << 0
      }
      mDestination(idx) = dPixel
    }
  }

  override def compute() {
    val sThredshold = 100000
    if (mLengh < sThredshold) {
      computeDirectly()
    } else {

      val split = mLengh / 2

      // this.invokeAll(
      //   ForkBlur(mSource, mStart, split, mDestination),
      //   ForkBlur(mSource, mStart + split, mLengh - split, mDestination)
      // )
      ForkBlur(mSource, mStart, split, mDestination).invoke()
      ForkBlur(mSource, mStart + split, mLengh - split, mDestination).invoke()
    }
  }
}


/** Standard implementations
  * 
  * + with fork/join framework
  *   + e.g. [[java.util.Arrays.parallelSort]]
  *   + e.g. [[java.util.stream]]
  */
