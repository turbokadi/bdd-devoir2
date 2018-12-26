package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class DoubleAxeFury (id: Int, location: Location)
  extends Thing (
    id,
      location,
      true,
    "Double Axe Fury",
    142,
    142,
    17,
    0,
    7,
      0.0,
    new Attack("+1 orc double axe", 11, 18, 80, 5),
    new Attack("mwk composite longbow", 7, 14, 50, 110),
    null
  ){

}
