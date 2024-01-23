package todo
package data


/*
 * The Id class encapsulates an integer that represents an Id. This
 * distinguishes Ids from other Ints that may occur in the program.
 *
 * You should NOT modify this file.
 */
final case class Id(toInt: Int):
  def next: Id = this.copy(toInt = toInt + 1)
object Id:
  // using Ordering.by method to create a given instance of Ordering[Id]
  // Equivalently:
  // object IdOrdering extends Ordering[Id]:
  //   def compare(x: Id, y: Id): Int = 
  //     Ordering[Int].compare(x.toInt, y.toInt)
  // given idOrdering: Ordering[Id] = IdOrdering
  // See context parameters & given definitions: https://docs.scala-lang.org/scala3/book/ca-context-parameters.html
  given idOrdering: Ordering[Id] = Ordering.by(id => id.toInt)
