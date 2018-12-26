package Exercice2.Monster

import Exercice2.Misc._

class BrutalWarlord (id: Int, location: Location)
  extends Thing (
    id,
    location,
    true,
    "Brutal Warlord",
    141,
    141,
    27,
    0,
      7,
      0.0,
    new Attack("+1 vicious flail", 11, 18, 20, 5),
    new Attack("mwk throwing axe", 6, 11,19, 110),
  ){}
