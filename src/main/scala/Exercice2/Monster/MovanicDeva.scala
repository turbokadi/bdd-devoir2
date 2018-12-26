package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class MovanicDeva (id: Int, location: Location)
  extends Thing(
    id,
      location,
      false,
    "Movanic Deva",
      286,
    126,
    24,
    0,
    7,
      50.0,
    new Attack("+1 flaming greatsword", 29, 42, 90, 10),
      new Attack("+3 holy longbow", 25, 42, 90, 110),
  ){
}
