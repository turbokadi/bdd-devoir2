package Exercice2

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}
import java.io._

import Exercice2.Misc.Thing
import net.liftweb.json.DefaultFormats
import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

class Engine extends Serializable { // SCALA est certe scalable mais il à pas été concus pour être bon langage de programmation.

  def sendTargetMsg(triplet: EdgeContext[Thing,Null,List[Thing]]) {
    triplet.sendToSrc(List(triplet.dstAttr))
    triplet.sendToDst(List(triplet.srcAttr))
  }

  def mergeTargetMsg(accum: List[Thing], newer: List[Thing]): List[Thing] = {
    accum:::newer
  }

  def joinTargetMsg(vid: VertexId, fighter: Thing, targets: List[Thing]): Thing = {
    fighter.getAndSetTargets(targets)
    fighter.clone()
  }

  def joinDamageMsg(vid: VertexId, pokemon: Thing, damages: Int): Thing = {
    pokemon.take(damages)
    pokemon.clone()
  }

  def sendDamageMsg(triplet: EdgeContext[Thing, Null, Int]) {
    if(triplet.srcAttr.targets.map(target => target.id).contains(triplet.dstAttr.id)) {
      triplet.sendToDst(triplet.srcAttr.attack(triplet.dstAttr))
    }
    if(triplet.dstAttr.targets.map(target => target.id).contains(triplet.srcAttr.id)) {
      triplet.sendToSrc(triplet.dstAttr.attack(triplet.srcAttr))
    }
  }

  def mergeDamageMsg(sum: Int, damage: Int): Int = {
    sum + damage
  }

  def run(vertex: RDD[(VertexId, Thing)],
          edges: RDD[Edge[Null]],
          sc: SparkContext,
          fileName: String
         ): Graph[Thing, Null] = {

    var graph = Graph(vertex, edges)

    vertex.foreach(vertex => println(vertex._2.toString))
    edges.foreach(edges => println(edges.dstId+" "+edges.srcId))

    val fields = new TripletFields(true, true, true)
    implicit val formats: DefaultFormats.type = DefaultFormats
    sc.setLogLevel("ERROR")
    sc.setCheckpointDir(System.getProperty("java.io.tmpdir"))

    var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))

    var count = 0
    var maxRounds = 200
    var end: Boolean = false

    while(!end) { // Engine Loop

      println("ROUND "+count+" :")
      writer.write("#\n")
      var buffGraph = graph

      print("Pito team : "+buffGraph.vertices.filter(vertex => !vertex._2.imLivingToKillPito).count())
      print(" - Jackass team : "+buffGraph.vertices.filter(vertex => vertex._2.imLivingToKillPito).count()+"\n\n")

      if(count > 0) {
        val newVertice = buffGraph.vertices.map(thing => {
          thing._2.regen()
          thing._2.move()
          thing
        })

        buffGraph = Graph(newVertice,buffGraph.edges)
      }

      val targetMessages = buffGraph.aggregateMessages[List[Thing]](
        sendTargetMsg,
        mergeTargetMsg,
        fields
      )

      buffGraph = buffGraph.joinVertices(targetMessages) (
        (vid, fighter, allTargets) => joinTargetMsg(vid,fighter,allTargets)
      )

      val damageMessages = buffGraph.aggregateMessages[Int](
        sendDamageMsg,
        mergeDamageMsg,
        fields
      )

      buffGraph = buffGraph.joinVertices(damageMessages)(
        (vid, pokemon, damages) => joinDamageMsg(vid, pokemon, damages)
      )

      buffGraph = buffGraph.subgraph(vpred = (vid, thing) => thing.health > 0) // Erase all dead scumbags

      for(pod <- buffGraph.vertices.collect()) writer.write(pod._2.toString+"\n")

      buffGraph.checkpoint()

      if (count < maxRounds) {
        if(buffGraph.vertices.filter{ vertex => vertex._2.health > 0 && vertex._2.imLivingToKillPito}.count == 0){
          println("You've win, Pito mess with the bests !")
          end = true
        }
        else if (buffGraph.vertices.filter{ vertex =>  vertex._2.health > 0 && !vertex._2.imLivingToKillPito}.count == 0){
          println("You've loose, Pito have died like the rests !")
          end = true
        }
      }
      else {
        println("Too long ... It exceeded "+maxRounds+" rounds.")
        end = true
      }

      count+=1
      graph = buffGraph

    }
    writer.close()
    graph
  }
}
