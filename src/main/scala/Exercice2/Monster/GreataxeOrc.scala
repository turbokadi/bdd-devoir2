package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class GreataxeOrc  (id: Int, location: Location)
  extends Thing(
    id,
      location,
      true,
    "Greataxe Orc",
    42,
    42,
    15,
    0,
    5,
      0.0,
    new Attack("greathaxe", 11, 26, 80, 5),
    new Attack("throwing haxe", 8, 28, 60, 110),
  ){
}
