case class SyncRGB(
  var red: Int,
  var green: Int,
  var blue: Int,
  var name: String
) {
  require( // Contract! throws IllegalArgumentException!
    Seq(red, green, blue) forall (Range(0, 256) contains _))

  def set(red: Int, green: Int, blue: Int, name: String) {
    require(Seq(red, green, blue) forall (Range(0, 256) contains _))
    this synchronized {
      this.red = red
      this.green = green
      this.blue = blue
      this.name = name
    }
  }

  def getRGB = (red << 16) | (green << 8) | blue

  def getName = name

  def invert() {
    red   = 255 - red
    green = 255 - green
    blue  = 255 - blue
    name  = s"InvOf${name}"
  }
}

val clr = SyncRGB(0, 0, 0, "Pitch Black")

/** When we want to get to attribute values, i.e. RGB and name ...*/
val clrInt0 = clr.getRGB
// Here some thread may interfere invoking clr.set(...)!!
val name0 = clr.getName
/** As a result clrInt0 and name0 may represent different states! */

// Binding together the locks to maintain state consistency when getting!
val (clrInt, name) = clr synchronized {
  (clr.getRGB, clr.getName)
}

println(clrInt)
println(name)

try {
  val clrWrong = SyncRGB(0, 256, 0, "Impossible")
} catch {
  case (e: IllegalArgumentException) => {
    println { "#Contract violation: impossible color.#" }
  }}
