package io.github.aj8gh.aok.util

import io.github.aj8gh.aok.util.InputReader.Reader
import kotlin.test.Test
import kotlin.test.assertEquals

class ReaderTest {

  @Test
  fun readInput() = assertEquals(
    listOf("test input data", "test input data"),
    Reader.readInput(DAY_1)
  )

  @Test
  fun readExample() = assertEquals(
    listOf("test example data", "test example data"),
    Reader.readExample(DAY_1)
  )

  @Test
  fun readExample_MultipleExamples() = assertEquals(
    listOf("test example 1 data", "test example 1 data"),
    Reader.readExample(DAY_1, EXAMPLE_1)
  )
}
