package com.github.aj8gh.aok.util

class Reader {

  fun readInput(day: Int): List<String> {
    return read(INPUT.format(day))
  }

  fun readExample(day: Int): List<String> {
    return read(EXAMPLE.format(day))
  }

  fun readExample(day: Int, example: Int): List<String> {
    return read(EXAMPLE_N.format(day, example))
  }

  private fun read(file: String): List<String> {
    return this::class
      .java
      .classLoader
      .getResourceAsStream(file)!!.bufferedReader()
      .readLines()
  }
}
