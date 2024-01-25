package todo

import cats.implicits.*
import java.nio.file.{Path, Paths, Files}
import java.nio.charset.StandardCharsets
import io.circe.{Decoder, Encoder}
import io.circe.parser.*
import io.circe.syntax.*
import scala.collection.mutable
import todo.data.*

/**
 * The PersistentModel is a model that saves all data to files, meaning that
 * tasks persist between restarts.
 *
 * You should modify this file.
 */
object PersistentModel extends Model:
  import Codecs.given

  /** Path where the tasks are saved */
  val tasksPath = Paths.get("tasks.json")
  /** Path where the next id is saved */
  val idPath = Paths.get("id.json")

  /**
   * Load Tasks from a file. Return an empty task list if the file does not exist,
   * and throws an exception if decoding the file fails.
   */
  def loadTasks(): Tasks =
    if Files.exists(tasksPath) then
      load[Tasks](tasksPath)
    else
      Tasks.empty

  /**
   * Load an Id from a file. This Id is guaranteed to have never been used before.
   * Returns Id(0) if the file does not exist, and throws
   * an exception if decoding the file fails.
   */

  // To reiterate the comment above, the design s.t. a singleton Id value is serialised into its own file
  // with the accompanying saveId() method which passes to call encoder[Id](id: Id). The purpuse is that
  // the serialised singleton Id value is guaranteed to be new (by the incremental nature) s.t. the loadId()
  // method from the next operation can directly load and make use of it.
  def loadId(): Id =
    if Files.exists(idPath) then
      load[Id](idPath)
    else
      Id(0)  

  /**
   * Load JSON-encoded data from a file.
   *
   * Given a file name, load JSON data from that file, and decode it into the
   * type A. Throws an exception on failure.
   *
   * It is not necessary to use this method. You should be able to use loadTasks
   * and loadId instead, which have a simpler interface.
   */
  def load[A](path: Path)(using decoder: Decoder[A]): A = {
    // Convinient method in nio.file to read all data from the file by the given path to
    // a single string, with the given character set
    val str = Files.readString(path, StandardCharsets.UTF_8)

    // In a production system we would want to pay more attention to error
    // handling than we do here, but this is sufficient for the case study.
    decode[A].apply(str) match {
      case Right(result) => result
      case Left(error) => throw error
    }
  }

  /**
   * Save tasks to a file. If the file already exists it is overwritten.
   */
  def saveTasks(tasks: Tasks): Unit =
    save(tasksPath, tasks)

  /**
   * Save Id to a file. The Id saved to a file must be an Id that was never used before.
   * If the file already exists it is overwritten.
   */

  // saveId method is called in two situations, both complying to the outcome describbed above
  // 1. Serialize a new Id bigger by 1 than the Ids of existing tasks, s.t creation of future new task
  // will make use by loading this new Id
  // 2. Serialize a Id of value 0 when all tasks are removed s.t. 0 will be the bootstrapping Id for
  // the initial system from scratch
  def saveId(id: Id): Unit =
    save(idPath, id)

  /**
   * Save data to a file in JSON format.
   *
   * Given a file name and some data, saves that data to the file in JSON
   * format. If the file already exists it is overwritten.
   *
   * It is not necessary to use this method. You should be able to use saveTasks
   * and saveId instead, which have a simpler interface.
   */

  // A generic save method over data of type A, utilizing the contextual parameter encoder to choose the respective
  // Encoder for type A s.t. data can be encoded to Json type with an asJson call which is Encoder[A].apply(data)
  def save[A](path: Path, data: A)(using encoder: Encoder[A]): Unit =
    val json = data.asJson
    
    // Convinient to use method in java.nio.file package that write a String(technically CharSequence) into the file
    // given by the path, with the given character set. Sensible default file ops i.e. to create file if not existed,
    // and trancating existing data if existed, to perform the write of the call
    Files.writeString(path, json.spaces2, StandardCharsets.UTF_8)
    ()

  /* Hint: there are two pieces of state we need to implement the model:
   * - the tasks
   * - the next Id
   * (The InMemoryModel uses the same.)
   */

  def create(task: Task): Id =
    val id = loadId()
    // Tasks case class takes an Iterable collection for instantation
    // s.t. the argument supplied to the Tasks() call can bbe populated by less generic 
    // collections, i.e. Map in this case
    val tasks = Tasks(loadTasks().toMap.updated(id, task))
    // val tasks = Tasks(loadTasks().toMap + (id -> task))
    
    saveTasks(tasks)
   
    // Generate and serialize a guaranteed new Id for next use
    val nextId = id.next
    saveId(nextId)
    id

  def read(id: Id): Option[Task] =
    val tasks = loadTasks()
    tasks.toMap.get(id)

  def update(id: Id)(f: Task => Task): Option[Task] =
    val updatedTasks = loadTasks().toMap.updatedWith(id)(task => task.map(f))
    saveTasks(Tasks(updatedTasks))
    updatedTasks.get(id)

  def delete(id: Id): Boolean =
    val tasks = loadTasks().toMap
    // two cases complying with the spec given int the Model trait:
    // 1. if exists task by the given id, the tasks is removed and updated tasks serialzied, the last call returns true
    // 
    saveTasks(Tasks(tasks.removed(id)))
    tasks.isDefinedAt(id)

  def tasks: Tasks =
    loadTasks()

  def tasks(tag: Tag): Tasks =
    Tasks(loadTasks().toMap.filter((taskId, task) => task.tags.contains(tag)))

  def complete(id: Id): Option[Task] =
    update(id)(task => task.complete)

  def tags: Tags =
    Tags(loadTasks().tasks.flatMap((taskId, task) => // use flatMap on tasks: Iterable[Task], which is a generic Iterable collection
      task.tags).toList.distinct) // distint only available in Seq trait collections, thus following toList

  /**
  * Delete the tasks and id files if they exist.
  */
  def clear(): Unit =
    saveId(Id(0))
    saveTasks(Tasks.empty)

