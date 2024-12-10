package io.github.aj8gh.aok.util

class InputReader {

  private fun read(file: String): List<String> = this::class
    .java
    .classLoader
    .getResourceAsStream(file)!!.bufferedReader()
    .readLines()

  companion object Reader {

    private val ir = InputReader()

    fun readInput(day: Int): List<String> = ir.read(INPUT.format(day))

    fun readExample(day: Int): List<String> = ir.read(EXAMPLE.format(day))

    fun readExample(day: Int, example: Int): List<String> =
      ir.read(EXAMPLE_N.format(day, example))
  }
}
