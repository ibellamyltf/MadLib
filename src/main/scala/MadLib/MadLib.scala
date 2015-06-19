/**
 * Created by ianbellamy on 6/12/15.
 */
package MadLib


import org.jsoup.Jsoup
import org.jsoup.select.Elements
import scala.collection.JavaConversions._
//
// import scala.sys.process._
import scala.language.postfixOps
// import java.net.URL
//
import java.util.Random
import epic.preprocess._
import epic.models.PosTagSelector



object MadLib {

  val baseURL = "http://denver.craigslist.org/"

  def findElements(url: String, selector: String): Elements = {
    val doc = Jsoup.connect(url).get()
    val elems = doc.select(selector)
    elems
  }
  def listURLs(elems: Elements): List[String] = {
    (for (elem <- elems) yield elem.attr("href") mkString) toList
  }

  // doesn't actually 'clean up' the lewd content you are about to enjoy
  // just turns them into a String

  def cleanElems(elems: List[Elements]): List[String] = {
    for (elem <- elems if elem.text.length > 300) yield elem.text mkString
  }

  // grabs the links from each row of Missed Connections

  val elems = findElements(baseURL+"search/mis", ".i")
  val links = listURLs(elems)
  val docs = for (href <- links) yield findElements(baseURL+href, "#postingbody")

  // clean that puppy up
  // again, still lewd

  val cleaned = cleanElems(docs)

  /*
  Text processing guideline
  Each text blob has 300 or more characters; that's enough to make a MadLib!

  Testing: just make one that gets the parts of speech and case matches it back in
  see https://github.com/dlwh/epic
   */

  // much like the tests, please check those out.

  val rand = new Random
  val getRand: Int = rand.nextInt((cleaned.length - 0) + 1) + 1

  val sentenceSplitter = MLSentenceSegmenter.bundled().get
  val tokenizer = new epic.preprocess.TreebankTokenizer()
  val sentences: IndexedSeq[IndexedSeq[String]] = sentenceSplitter(cleaned(getRand)).map(tokenizer).toIndexedSeq
  val tagger = PosTagSelector.loadTagger("en").get
  val tags: IndexedSeq[epic.sequences.TaggedSequence[epic.trees.AnnotatedLabel, String]] = for {
    sentence <- sentences
  } yield tagger.bestSequence(sentence)
  def zipText(text: IndexedSeq[epic.sequences.TaggedSequence[epic.trees.AnnotatedLabel, String]]): IndexedSeq[IndexedSeq[(epic.trees.AnnotatedLabel, String)]] ={
    for { t <- text } yield t.label zip t.features
  }
  val testValue = zipText(tags)

  // will have to do this for each sentence!
  //val sentences: IndexedSeq[IndexedSeq[String]] = sentenceSplitter(text).map(tokenizer).toIndexedSeq






}
