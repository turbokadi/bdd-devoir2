package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class AstralDeva (id: Int, location: Location)
  extends Thing(
    id,
      location,
      false,
    "Astral Deva",
    212,
    172,
    29,
    0,
    9,
      50.0,
    new Attack("+2 disrupting warhammer", 25, 42, 90, 10),
    new Attack("+3 holy longbow", 25, 42, 90, 110),
  ){
}
