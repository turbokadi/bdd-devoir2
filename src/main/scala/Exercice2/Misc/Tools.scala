package Exercice2.Misc

object Tools {

  def bool2Int(bool: Boolean): Int = {
    if(bool) 1
    else 0
  }

  def getRandomPosition(minOffset: Location, maxOffset: Location): Location = {
    new Location(
      scala.util.Random.nextInt(maxOffset.x.toInt-minOffset.x.toInt)+minOffset.x+scala.util.Random.nextDouble(),
      scala.util.Random.nextInt(maxOffset.y.toInt-minOffset.y.toInt)+minOffset.y+scala.util.Random.nextDouble(),
      0.0
    )
  }

}
