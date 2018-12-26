package Exercice2

import Exercice2.Misc.{Location, Thing, Tools}
import Exercice2.Monster._
import org.apache.spark.graphx.{Edge, VertexId}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object Simulation2 extends App {

  val sc = new SparkContext(new SparkConf().setAppName("Simulation 2 : Le bordel ...").setMaster("local"))

  val fightingEntities: ArrayBuffer[(VertexId,Thing)] = ArrayBuffer()
  val relationEdges: ArrayBuffer[Edge[Null]] = ArrayBuffer()

  fightingEntities += ((0,new Solar(0,Tools.getRandomPosition(new Location(0,0,0),new Location(100,200,0)))))

  fightingEntities += ((13,new RedDragon(13,new Location(200,100,50))))

  for(i <- 1 to 3) fightingEntities += ((i,new MovanicDeva(i,Tools.getRandomPosition(new Location(0,0,0),new Location(100,200,0)))))
  for(i <- 4 to 6) fightingEntities += ((i,new Planetar(i,Tools.getRandomPosition(new Location(0,0,0),new Location(100,200,0)))))
  for(i <- 7 to 12) fightingEntities += ((i,new AstralDeva(i,Tools.getRandomPosition(new Location(0,0,0),new Location(100,200,0)))))

  for(i <- 14 to 24) fightingEntities += ((i,new AngelSlayer(i,Tools.getRandomPosition(new Location(150,0,0),new Location(200,200,0)))))
  for(i <- 25 to 225) fightingEntities += ((i,new GreataxeOrc(i,Tools.getRandomPosition(new Location(150,0,0),new Location(200,200,0)))))


  for (x <- 0 to 12) {
    for (i <- 13 until fightingEntities.size) {
      relationEdges += Edge(x,i)
    }
  }

  new Engine().run(sc.parallelize(fightingEntities),sc.parallelize(relationEdges),sc,"simulation2.txt")
}