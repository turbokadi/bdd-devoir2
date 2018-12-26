package Exercice2.Monster

import Exercice2.Misc.{Attack, Location, Thing}

class OrcWorgRider (id: Int, location: Location)
  extends Thing (
    id,
      location,
      true,
    "Orc worg Rider",
    13,
    13,
    18,
    0,
    3,
      0.0,
    new Attack("mwk battleaxe", 4, 12, 80, 5),
    new Attack("shortbow", 1, 6, 60, 110),
  )
{

}
