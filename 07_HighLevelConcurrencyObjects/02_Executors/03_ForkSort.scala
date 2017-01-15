import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveAction
import java.lang.System.{currentTimeMillis}

import scala.collection.mutable.ArrayBuffer


case class Sorter(
  val src: ArrayBuffer[Int],
  val low: Int,
  val high: Int
) extends RecursiveAction {

  override def compute() {
    if (low < high) {
      val piv = src(high)
      var i = low
      for (j <- Range(low, high)) {
        if (src(j) < piv) {
          val tmp = src(j) ; src(j) = src(i) ; src(i) = tmp
          i += 1
        }
      }
      val tmp = src(high) ; src(high) = src(i) ; src(i) = tmp
      assert { (low until i map {src(_) < piv} foldLeft true) (_ && _)}
      assert { (i+1 to high map {src(_) >= piv} foldLeft true) (_ && _)}
      Sorter(src, low,  i-1).invoke()
      Sorter(src, i+1, high).invoke()
    }
  }

  def sort() {
    if (low < high) {
      val piv = src(high)
      var i = low
      for (j <- Range(low, high)) {
        if (src(j) < piv) {
          val tmp = src(j) ; src(j) = src(i) ; src(i) = tmp
          i += 1
        }
      }
      val tmp = src(high) ; src(high) = src(i) ; src(i) = tmp
      assert { (low until i map {src(_) < piv} foldLeft true) (_ && _)}
      assert { (i+1 to high map {src(_) >= piv} foldLeft true) (_ && _)}
      Sorter(src, low,  i-1).sort()
      Sorter(src, i+1, high).sort()
    }
  }
}


val xs = ArrayBuffer(9, 3, 4, 1, 5, 8, 6, 7, 0, 2)
val s = Sorter(xs, 0, xs.length-1)
// s.sort()
s.invoke()
println(xs)

// val f = ForkSorter(xs, 0, xs.length)
// val pool = new ForkJoinPool
// pool.invoke(f)
