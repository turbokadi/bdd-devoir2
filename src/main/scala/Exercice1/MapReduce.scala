package Exercice1

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}
import scala.collection.mutable.ListBuffer

import org.apache.spark.{SparkConf, SparkContext}
import net.liftweb.json._

class Pet extends Serializable { // Filler class
  var name = ""
  var spells = List.empty[String]

  def this(name: String, spells: List[String]) {
    this()
    this.name = name
    this.spells = spells
  }
}

object MapReduce extends App {

  def dumpBatchView(batchView: Array[(String,Iterable[String])], fileName: String) : Unit = { // Html Table Array
    var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)))
    writer.write("<style>table, th, td { border: 1px solid black; border-collapse: collapse; padding: 10px; font-family: sans-serif;}</style><table><tr><th>Spell</th><th>Monsters</th></tr>\n")
    for (pod <- batchView) {
      writer.write("<tr><td>"+pod._1 + "</td><td>")
      for (mnstr <- pod._2) {
        writer.write(mnstr + ", ")
      }
      writer.write("</td></tr>")
    }
    writer.write("</table>\n")
    writer.close()
  }

  implicit val formats: DefaultFormats.type = DefaultFormats

  val ctx = new SparkContext(new SparkConf().setAppName("exercice1").setMaster("local")) // Load cluster driver

  val Burp = parse(scala.io.Source.fromFile("crawler/Bestiary.json").mkString) // Load Json file

  var Bestiary = new ListBuffer[Pet]()
  for(pod <- Burp.children) {
    Bestiary.append(pod.extract[Pet]) // Bootstrap data in filler class
  }

  val batchView = ctx.parallelize(Bestiary).flatMap(monster => monster.spells.map(spell => (spell, monster.name))).groupByKey()

  dumpBatchView(batchView.collect(), "all-monsters-associate-to-spells.html")
}
