package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class Planetar (id: Int, location: Location)
  extends Thing(
    id,
      location,
      false,
    "Planetar",
    279,
    229,
    32,
    10,
    12,
      50.0,
    new Attack("+3 holy greatsword", 25, 42, 90, 10),
    new Attack("+3 holy longbow", 25, 42, 90, 110),
  ){
}
