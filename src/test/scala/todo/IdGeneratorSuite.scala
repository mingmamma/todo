package todo

import todo.data.*
import munit.*

/**
 * Tests for the IdGenerator
 */
// Much like the basic example where some simple tests are
// done after declaring a test suite: https://scalameta.org/munit/docs/tests.html#declare-tests-inside-a-helper-function
class IdGeneratorSuite extends FunSuite:
  test("generated Ids are monotonically increasing"){
    val generator = IdGenerator(Id(0))
    val ids = for(i <- 1.to(1000)) yield generator.nextId()
    // Use assert method:
    // https://scalameta.org/munit/docs/getting-started.html#quick-start
    assert(ids.sliding(2).forall{ case Seq(a, b) => a.toInt < b.toInt })
  }


  test("generated Ids start with given Id"){
    val generator = IdGenerator(Id(0))
    // Use assertEquals: https://scalameta.org/munit/docs/assertions.html#assertequals
    assertEquals(generator.nextId(), Id(0))
  }
