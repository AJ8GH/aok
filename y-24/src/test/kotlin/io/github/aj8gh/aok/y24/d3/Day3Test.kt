package io.github.aj8gh.aok.y24.d3

import io.github.aj8gh.aok.util.DAY_3
import io.github.aj8gh.aok.util.EXAMPLE_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day3Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, expected: Int) =
    assertEquals(expected, part1(input))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, expected: Int) =
    assertEquals(expected, part2(input))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(161, 170807108)

    @JvmStatic
    private fun inputProviderPart2() = getInput(48, 74838033, EXAMPLE_1)

    private fun getInput(example: Int, answer: Int, exampleNumber: Int? = null) = listOf(
      Arguments.of(readExample(DAY_3, exampleNumber), example),
      Arguments.of(readInput(DAY_3), answer),
    )
  }
}
