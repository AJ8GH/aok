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
  fun part1(input: List<String>, minSaving: Int, expected: Int) =
    assertEquals(expected, part1(input, minSaving))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, minSaving: Int, expected: Int) =
    assertEquals(expected, part2(input, minSaving))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(Pair(10, 10), Pair(100, 1355))

    @JvmStatic
    private fun inputProviderPart2() = getInput(Pair(50, 285), Pair(100, 0))

    private fun getInput(
      example: Pair<Int, Int>,
      answer: Pair<Int, Int>
    ) = listOf(
      Arguments.of(readExample(DAY_20), example.first, example.second),
      Arguments.of(readInput(DAY_20), answer.first, answer.second),
    )
  }
}
