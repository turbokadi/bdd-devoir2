package Exercice2.Misc

class Thing (
              var id:Int = -1,
              var location: Location = new Location(),
              var imLivingToKillPito:Boolean = false,
              var name: String = "",
              var health: Int = 0,
              var fulllife: Int = 0,
              var armor: Int = 0,
              var heal: Int = 0,
              var speeds: Int = 0,
              var maxHeight: Double = 0.0,
              var cac: Attack = null,
              var ranged: Attack = null,
              var targets: List[Thing] = List.empty[Thing], // Simulation live value only
              var maxSimultaneousTargets: Int = 1,
            )
  extends Serializable {

  def take(amount: Int): Unit = {
    this.health -= amount
    if (this.health < 0) this.health = 0
  }

  def regen() {
    this.health += this.heal
    if (this.health > this.fulllife) this.health = this.fulllife
  }

  def attack(target: Thing) : Int = {
    val distance = Location.getDistance(this.location,target.location)
    if(cac != null && distance <= cac.maxDistance) cac.getDamage(target.armor)
    else if(ranged != null && distance <= ranged.maxDistance) ranged.getDamage(target.armor)
    else 0
  }

  def move(){
    if(targets.nonEmpty) {
      val distance = Location.getDistance(location,targets.head.location)
      val speedFactor = speeds/distance

      if(distance > 2) {
        if(speedFactor <= 1.0) {
          val x = location.x - (location.x - targets.head.location.x)*speedFactor
          val y = location.y - (location.y - targets.head.location.y)*speedFactor
          val z = location.z - (location.z - targets.head.location.z)*speedFactor

          if(x < 0) location.x = 0.0
          else location.x = x

          if(y < 0) location.y = 0.0
          else location.y = y

          if ( z > maxHeight) location.z = maxHeight
          else if (z < 0)  location.z = 0.0
          else location.z = z
        }
        else {
          val x = targets.head.location.x - Math.sqrt(2)
          val y = targets.head.location.y - Math.sqrt(2)
          val z = targets.head.location.z - Math.sqrt(2)

          if(x < 0) location.x = 0.0
          else location.x = x

          if(y < 0) location.y = 0.0
          else location.y = y

          if(z>maxHeight) location.z = maxHeight
          else if (z < 0.0+0.0001)  location.z = 0.0
          else location.z = z
        }
      }
    }
  }

  def getAndSetTargets(targets: List[Thing]) {
    this.targets = targets.sortBy(target => Location.getDistance(location, target.location)).take(maxSimultaneousTargets)
  }

  override def toString: String = id + "," + Tools.bool2Int(imLivingToKillPito) + "," + name + "," + health + "," + location.toString + ":"

  override def clone(): Thing = {
    new Thing(
      id,
      location,
      imLivingToKillPito,
      name,
      health,
      fulllife,
      armor,
      heal,
      speeds,
      maxHeight,
      cac,
      ranged,
      targets,
      maxSimultaneousTargets
    )
  }
}
