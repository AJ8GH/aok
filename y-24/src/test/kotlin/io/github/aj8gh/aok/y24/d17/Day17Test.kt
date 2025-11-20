package io.github.aj8gh.aok.y24.d17

import io.github.aj8gh.aok.util.DAY_17
import io.github.aj8gh.aok.util.EXAMPLE_1
import io.github.aj8gh.aok.util.EXAMPLE_2
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day17Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, expected: String) =
    assertEquals(expected, part1(input))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, expected: String) =
    assertEquals(expected, part2(input))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(
      "0,1,2",
      "4,2,5,6,7,7,7,7,3,1,0",
      "4,6,3,5,6,3,5,2,1,0",
      "7,0,7,3,4,1,3,0,1",
    )

    @JvmStatic
    private fun inputProviderPart2() = getInput("", "", "", "")

    private fun getInput(
      example1: String,
      example2: String,
      example: String,
      answer: String
    ) = listOf(
      Arguments.of(readExample(DAY_17, EXAMPLE_1), example1),
      Arguments.of(readExample(DAY_17, EXAMPLE_2), example2),
      Arguments.of(readExample(DAY_17), example),
      Arguments.of(readInput(DAY_17), answer),
    )
  }
}
