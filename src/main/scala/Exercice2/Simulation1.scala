package Exercice2

import Exercice2.Misc.{Location, Thing, Tools}
import Exercice2.Monster.{BrutalWarlord, DoubleAxeFury, OrcWorgRider, Solar}
import org.apache.spark.graphx.{Edge, VertexId}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

object Simulation1 extends App {

  val sc = new SparkContext(new SparkConf().setAppName("Simulation 1 : Solar vs Orcs").setMaster("local"))

  val fightingEntities: ArrayBuffer[(VertexId,Thing)] = ArrayBuffer()
  val relationEdges: ArrayBuffer[Edge[Null]] = ArrayBuffer()

  fightingEntities += ((0,new Solar(0,new Location(100,100))))

  fightingEntities += ((1,new BrutalWarlord(1,Tools.getRandomPosition(new Location(20,20,0),new Location(180,180,0)))))

  for (i <- 2 to 6) fightingEntities += ((i,new DoubleAxeFury(i,Tools.getRandomPosition(new Location(20,20,0),new Location(180,180,0)))))

  for (i <- 7 to 16) fightingEntities += ((i,new OrcWorgRider(i,Tools.getRandomPosition(new Location(20,20,0),new Location(180,180,0)))))

  for (i <- 1 until fightingEntities.size) {
    relationEdges += Edge(0,i)
  }

  new Engine().run(sc.parallelize(fightingEntities),sc.parallelize(relationEdges),sc,"simulation1.txt")
}
