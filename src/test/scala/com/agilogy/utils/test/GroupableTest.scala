package com.agilogy.utils.test

import com.agilogy.utils.Groupable
import org.scalatest.FlatSpec

class GroupableTest extends FlatSpec {

  import com.agilogy.utils.Groupable._

  behavior of "GroupableIterable"

  it should "group the empty iterable" in {
    assert(Seq.empty[Int].group(_ % 2) === Seq.empty[(Int, Seq[Int])])
  }

  it should "group the iterable by identity" in {
    assert(Seq(1, 2, 3).group(identity) === Seq(1 -> Seq(1), 2 -> Seq(2), 3 -> Seq(3)))
  }

  it should "group consecutive elements" in {
    assert(Seq("apple", "pear", "pineapple").group(_.head) === Seq('a' -> Seq("apple"), 'p' -> Seq("pear", "pineapple")))
  }

  it should "group non consecutive groups in different groups" in {
    assert(Seq("pear", "apple", "pineapple").group(_.head) === Seq('p' -> Seq("pear"), 'a' -> Seq("apple"), 'p' -> Seq("pineapple")))
  }

  it should "group mapping values" in {
    val s = Seq("email" -> "j@example.com", "email" -> "k@example.com", "name" -> "John")
    val res = s.group(by = _._1, as = _._2)
    assert(res === Seq("email" -> Seq("j@example.com", "k@example.com"), "name" -> Seq("John")))
  }

  it should "regroup a grouped seq" in {
    val seq = Seq(("Bcn", "Sales", "John", "first"), ("Bcn", "Sales", "John", "second"), ("Bcn", "Sales", "Mary", "first"), ("Bcn", "Mkting", "Andrea", "first"), ("Berlin", "Fin", "Mark", "first"))
    val res = seq.group({ case (c, d, e, f) => (c, d, e)}, _._4)
      .regroup({ case (c, d, e) => (c, d)}, _._3)
      .regroup(_._1, _._2)
    assert(res === Seq(
      "Bcn" -> Seq(
        "Sales" -> Seq("John" -> Seq("first", "second"), "Mary" -> Seq("first")),
        "Mkting" -> Seq("Andrea" -> Seq("first"))),
      "Berlin" -> Seq("Fin" -> Seq("Mark" -> Seq("first")))))
  }
}
 