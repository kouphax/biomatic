package se.yobriefca.Biomatic

import com.twitter.finatra._
import com.twitter.finatra.ContentType._
import scala.util.Random
import org.scalautils.TimesOnInt

object App extends FinatraServer {

  class IndexView(val bio: String) extends View {
    def template: String = "index.mustache"
  }

  class Biomatic extends Controller {
    get("/") { request =>
      val bio = BioBuilder.build
      respondTo(request) {
        case _:Json => render.json(Map(
          "bio"   -> bio,
          "about" -> "http://github.com/kouphax/biomatic"
        )).toFuture
        case _:All => render.plain(bio + "\n\n  - http://github.com/kouphax/biomatic").toFuture
      }
    }

  }

  val app = new Biomatic

  register(app)
}

object BioBuilder extends TimesOnInt {

  val words = List("husband", "father", "mother", "lover", "uncle", "donkey", "carpenter", "lazy-bones", "babymaker", "footballer",
                   "rugby mad", "fashion victim", "bearded", "curmudgeon", "grumpy", "cat eating", "always smiling", "maniac",
                   "on a boat", "hater of food", "lover of food", "lover", "singer", "space lazer enthusiast", "once spoke to Yoko",
                   "full o' beans", "parent", "sibling", "cousin", "shopper", "shipper", "forever chipper", "star gazer", "dreamboat",
                   "friend to Kim", "political hairdresser", "angry seamstress", "widow", "chocolate eater", "in the dictionary", "hairy",
                   "dungeon master", "sous chef", "table tennis champion", "beast", "sad", "forever alone", "F1", "Tesla", "Elon Musk",
                   "Agile", "BDD Barista", "thought leader", "once sacked from Wendys", "painter", "artist", "manager", "tired",
                   "red bull drinking", "coffee guzzlin'", "beatmaker", "theremin playin' gangsta", "right wing", "left wing",
                   "mullet rockin'", "festival goin'", "steak eatin'", "vegetarian", "vegan", "dog lover", "quiet", "loud", "INT-J",
                   "extrovert", "cyclist", "hiker", "trekkie", "paleo pal", "craft beer", "innovation czar", "social architect",
                   "quizmaster", "wizard", "he was dead all along", "robot building", "single", "married", "traveller", "growth hacker",
                   "bit-shifter", "pixel pusher", "boardroom badass", "rockstar", "ninja", "innovationizer", "proud husband", "father of jimmy",
                   "old", "early riser", "late night streaker", "yak shaver", "ballroom dancer", "twitter-mad", "soccer mom",
                   "leviathan", "foolish", "ZANZIBAR!", "yolo", "swag", "Irish Dancer", "poet", "tv zombie", "santas little helper",
                   "take the path less travelled", "accountant", "miner", "bit-coin", "SEO", "blogger", "fantastic", "excellent",
                   "honest", "Favourite Movie: Beaches", "Football", "404: Not Profound")

  val wordCount = words.size

  def build = {

    val numberOfWords = Random.nextInt(9)

    val bio = 0.to(numberOfWords).map { _ =>
      words(Random.nextInt(wordCount))
    }.distinct.mkString(", ")

    bio.head.toUpper + bio.tail
  }
}
