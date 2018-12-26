package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class AngelSlayer (id: Int, location: Location)
  extends Thing(
    id,
      location,
      true,
    "Angel Slayer",
    212,
    112,
    26,
    0,
    7,
      0.0,
    new Attack("double axe", 28, 45, 80, 5),
    new Attack("mwk composite longbow", 26, 34, 60, 110),
  ){
}
