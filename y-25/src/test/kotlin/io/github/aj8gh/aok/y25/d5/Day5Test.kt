package io.github.aj8gh.aok.y25.d5

import io.github.aj8gh.aok.util.DAY_5
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day5Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, expected: Long) =
    assertEquals(expected, part1(input))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, expected: Long) =
    assertEquals(expected, part2(input))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(3L, 517L)

    @JvmStatic
    private fun inputProviderPart2() = getInput(14L, 336173027056994L)

    private fun getInput(example: Long, answer: Long) = listOf(
      Arguments.of(readExample(DAY_5), example),
      Arguments.of(readInput(DAY_5), answer),
    )
  }
}
