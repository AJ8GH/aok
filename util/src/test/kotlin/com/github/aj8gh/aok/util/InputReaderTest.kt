package com.github.aj8gh.aok.util

import com.github.aj8gh.aok.util.InputReader.Reader
import kotlin.test.Test
import kotlin.test.assertEquals

class ReaderTest {

  @Test
  fun readInput() {
    val expected = listOf("test input data", "test input data")
    val actual = Reader.readInput(DAY_1)
    assertEquals(expected, actual)
  }

  @Test
  fun readExample() {
    val expected = listOf("test example data", "test example data")
    val actual = Reader.readExample(DAY_1)
    assertEquals(expected, actual)
  }

  @Test
  fun readExample_MultipleExamples() {
    val expected = listOf("test example 1 data", "test example 1 data")
    val actual = Reader.readExample(DAY_1, EXAMPLE_1)
    assertEquals(expected, actual)
  }
}
