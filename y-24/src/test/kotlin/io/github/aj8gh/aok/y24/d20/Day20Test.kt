package io.github.aj8gh.aok.y24.d20

import io.github.aj8gh.aok.util.DAY_20
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day20Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, minScore: Int, expected: Int) =
    assertEquals(expected, part1(input, minScore))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, expected: Int) =
    assertEquals(expected, part2(input))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(10, 10, 100, 0)

    @JvmStatic
    private fun inputProviderPart2() = getInput(0, 0, 0, 0)

    private fun getInput(
      exampleMinScore: Int,
      example: Int,
      minScore: Int,
      answer: Int
    ) = listOf(
      Arguments.of(readExample(DAY_20), exampleMinScore, example),
      Arguments.of(readInput(DAY_20), minScore, answer),
    )
  }
}
