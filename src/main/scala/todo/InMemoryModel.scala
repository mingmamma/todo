package todo

import cats.implicits.*
import scala.collection.mutable
import todo.data.*

/**
 * The InMemoryModel is a Model that stores all the tasks in RAM, and hence they
 * are lost when the server restarts.
 *
 * You should modify this file.
 */
object InMemoryModel extends Model:
  /* These are the tasks the application starts with. You can change these if you want. */
  val defaultTasks = List(
    // states, description, notes, tags
    Id(0) -> Task(State.completedNow, "Complete Effective Scala Week 2", None, List(Tag("programming"), Tag("scala"))),
    Id(1) -> Task(State.Active, "Complete Effective Scala Week 3", Some("Finish the todo list exercise"), List(Tag("programming"), Tag("scala"), Tag("encapsulation"), Tag("sbt"))),
    Id(2) -> Task(State.Active, "Make a sandwich", Some("Cheese and salad or ham and tomato?"), List(Tag("food"), Tag("lunch")))
  )

  /* Every Task is associated with an Id. Ids must be unique. */
  private val idGenerator = IdGenerator(Id(3))

  /* The idStore stores the associated between Ids and Tasks. We use a
   * LinkedHashMap so we can access elements in insertion order. We need to keep
   * a stable order so the UI doesn't jump around, which would be confusing to
   * the user.
   *
   * Note that this data structure is not safe to use with concurrent access.
   * This doesn't matter in this case study, but in a real situation it would be
   * a problem. In a future week we'll learn the techniques to address this. */
  
   // Note the in-memory data structure for a collection of tasks is the MUTABLE LinkedHashMap
   // https://docs.scala-lang.org/overviews/collections-2.13/overview.html
  private val idStore: mutable.LinkedHashMap[Id, Task] =
    mutable.LinkedHashMap.from(defaultTasks)

  // put method is specific to mutable maps, noting that it has a return value
  // but not useful in this case
  def create(task: Task): Id =
    val id = idGenerator.nextId()
    val _ = idStore.put(id, task)
    id

  // Use get() method since the required return type is an Option
  // Use map.apply() or equivalently, map() to get unwrapped value by key
  // exception raised if key NOT existing, comparable API to Rust
  def read(id: Id): Option[Task] =
    idStore.get(id)

  def complete(id: Id): Option[Task] =
    update(id)((task: Task) => (task.complete: Task))

  // Noting the parameter required by the updateWith() method
  def update(id: Id)(f: Task => Task): Option[Task] =
    idStore.updateWith(id)((task: Option[Task]) => (task.map(f): Option[Task]))

  // Noting the semantic difference of remove() method beween immutable & mutable maps
  // remove() of immutable maps return the map with the key removed 
  // remove() of mutable maps return an option to the value associated to the removed key, hence some(v) if existed OR None
  def delete(id: Id): Boolean =
    var found = false
    idStore.remove(id).nonEmpty
    // nonEmpty and isDefined are equivalent methods for Option
    // https://www.scala-lang.org/api/3.3.1/scala/Option.html
    // idStore.remove(id).isDefined

  def tasks: Tasks =
    Tasks(idStore)

  def tags: Tags =
    Tags(idStore.flatMap((id: Id, task: Task) => 
      (task.tags: List[Tag])).toList.distinct)

  def tasks(tag: Tag): Tasks =
    Tasks(idStore.filter((id: Id, task: Task) => 
      (task.tags.contains(tag): Boolean)))

  // clear() method removes all mapping off a mutable map
  def clear(): Unit =
    idStore.clear()
