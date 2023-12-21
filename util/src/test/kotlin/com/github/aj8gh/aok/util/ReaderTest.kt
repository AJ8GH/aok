package com.github.aj8gh.aok.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReaderTest {

  private val subject = Reader()

  @Test
  fun read() {
    val expected = listOf("test data")
    val actual = subject.read("./test-input.txt")
    assertEquals(expected, actual)
  }
}
