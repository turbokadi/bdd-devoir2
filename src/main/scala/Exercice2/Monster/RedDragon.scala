package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class RedDragon(id: Int, location: Location)
  extends Thing(
    id,
    location,
    true,
    "Red Great Wyrm Dragon",
    391,
    391,
    27,
    0,
    15,
    50.0,
    new Attack("dragon rush", 25, 55, 90, 5),
    new Attack("mighty flame", 26, 52, 80, 70),
  ){

  override def getAndSetTargets(targets: List[Thing]): Unit = {
    targets.find(target => target.name == "Solar").foreach(target => this.targets = List(target))
  }

}
