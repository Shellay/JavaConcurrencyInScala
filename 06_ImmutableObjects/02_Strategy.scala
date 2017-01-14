/** Strategy for definint immutable objects

  - No "inplace setter" methods.
  - Make all fields (final) and (private???).
  - No overriding - (final class) or (private constructor + factory)
  - Provides no methods modifying unavoidable mutable fields.

  */

case class ImmutableRGB(
  val red: Int,
  val green: Int,
  val blue: Int,
  val name: String
) {

  require {
    Seq(red, green, blue) forall (Range(0, 256) contains _)
  }

  def getRGB = (red << 16) | (green << 8) | blue

  def getName = name

  def invert = ImmutableRGB(
    255 - red,
    255 - green,
    255 - blue,
    s"InvOf${name}"
  )
  
}


val clr1 = ImmutableRGB(0, 0, 0, "White")
val clr2 = ImmutableRGB(255, 0, 0, "Red")
try {
  val clrFalse = ImmutableRGB(255, 0, -1, "Foo")
} catch {
  case (e: IllegalArgumentException) => println {"#Color unreasonable#."}
}
