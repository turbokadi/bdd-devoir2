package Exercice2.Monster

import Exercice2.Misc._

class Solar (id: Int,location: Location) extends Thing (
    id,
    location,
    false,
    "Solar",
    383,
    363,
    44,
  9,
  10,
  50.0,
    new Attack("+5 dancing greatsword", 35, 48, 35, 10),
    new Attack("+n5 composite longbow", 31, 46, 31,110),
  ){
}
