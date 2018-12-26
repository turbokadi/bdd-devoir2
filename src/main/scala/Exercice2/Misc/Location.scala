package Exercice2.Misc

class Location (var x: Double = 0, var y: Double = 0, var z: Double = 0) extends Serializable {
  override def toString: String = x+","+y+","+z
}

object Location {
  def getDistance(l1: Location, l2: Location): Double = {
    Math.sqrt(Math.pow(l1.x-l2.x,2)+Math.pow(l1.y-l2.y,2)+Math.pow(l1.z-l2.z,2))
  }
}