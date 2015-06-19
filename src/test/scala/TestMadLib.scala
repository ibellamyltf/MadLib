
import org.scalatest._
import MadLib._

class TestMadLib extends FlatSpec with Matchers {

  "MadLib" should "grab pages and preproceess" in {

    val baseURL = "http://denver.craigslist.org/"
    // remember .content
    val elements = MadLib.findElements(baseURL+"search/mis", ".i")
    val urls = MadLib.listURLs(elements)
    val docs = for (href <- urls) yield MadLib.findElements(baseURL+href, "#postingbody")
    val cleaned = MadLib.cleanElems(docs)

    // println(cleaned(0))
     println(MadLib.sentences)
    println(MadLib.tags)
    for (i <- MadLib.tags) {
      println(i)
    }
    println(MadLib.testValue)
    val test = MadLib.normalizeToString(MadLib.testValue)
    MadLib.spitOutput(test, "NN")

  }
}

