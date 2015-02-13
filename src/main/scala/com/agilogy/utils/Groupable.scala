package com.agilogy.utils

import scala.collection.mutable.ListBuffer

object Groupable {

  implicit class GroupableTraversable[T](i: TraversableOnce[T]) {

    def group[GT, RT](by: ((T) => GT)): Seq[(GT, Seq[T])] = group(by,identity[T])

    def group[GT, RT](by: ((T) => GT), as: ((T) => RT)): Seq[(GT, Seq[RT])] = {
      if (i.isEmpty) Seq()
      else {
        val result = new ListBuffer[(GT, Seq[RT])]
        var currentGroup: Option[GT] = None
        var currentBuffer = new ListBuffer[RT]
        i.foreach {
          elem =>
            val group = by(elem)
            val res: RT = as(elem)
            currentGroup match {
              case None => 
                currentGroup = Some(group)
                currentBuffer.append(res)
              case Some(cg) if cg == group =>
                currentBuffer.append(res)
              case Some(cg) =>
                result.append(cg -> currentBuffer.toSeq)
                currentBuffer = new ListBuffer[RT]
                currentBuffer.append(res)
                currentGroup = Some(group)
            }

        }
        currentGroup.foreach(cg => result.append(cg -> currentBuffer.toSeq))
        result.toSeq
      }
    }
  }

  implicit class GroupableGroupedSec[GT, RT](l: Seq[(GT, Seq[RT])]) extends GroupableTraversable[(GT, Seq[RT])](l) {
    def regroup[GT2, RT2](by: (GT => GT2), as: GT => RT2): Seq[(GT2, Seq[(RT2,Seq[RT])])] = this.group(e => by(e._1), l => as(l._1) -> l._2)
  }

}