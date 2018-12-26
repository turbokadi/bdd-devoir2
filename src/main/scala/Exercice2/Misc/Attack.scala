package Exercice2.Misc

class Attack(
               var name: String,
               var min: Int,
               var max: Int,
               var precision: Int,
               var maxDistance: Int
             )
  extends Serializable {

  def getDamage(Armor: Int) : Int = {
    //if(precision >= scala.util.Random.nextInt(100)) { // Jet d'action
      val damage = scala.util.Random.nextInt(max-min)+min // Jet de dégats
      if(damage < Armor) {
        0
      } else {
        damage
      }
    //}
  }
}
