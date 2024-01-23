package todo

import todo.data.Id

/**
 * This class encapsulates an Id that will be incremented every time it is accessed.
 *
 * You should NOT modify this file
 */
// Explicitly declaring the private member variable id for the class
// See: https://docs.scala-lang.org/tour/classes.html#defining-a-class
// https://docs.scala-lang.org/tour/classes.html#private-members-and-gettersetter-syntax

// The following is close but not equivalent in the sense that id is a private member
// BUT it is a immutable val, not a variable, when the type is omited:
// class IdGenerator(id: Id):
class IdGenerator(private var id: Id):
  /**
   *  Get the next Id value to use, and increment the Id stored in the IdGenerator.
   */
  def nextId(): Id =
    val currentId = id
    this.id = currentId.next
    currentId 
