package todo

import todo.data.*
import munit.*

/**
 * Tests for the IdGenerator
 */
// See MUnit for a quick start to declare some tests:
// https://scalameta.org/munit/docs/tests.html
class IdGeneratorSuite extends FunSuite:
  test("generated Ids are monotonically increasing"){
    val generator = IdGenerator(Id(0))
    val ids = for(i <- 1.to(1000)) yield generator.nextId()

    assert(ids.sliding(2).forall{ case Seq(a, b) => a.toInt < b.toInt })
  }


  test("generated Ids start with given Id"){
    val generator = IdGenerator(Id(0))

    assertEquals(generator.nextId(), Id(0))
  }
