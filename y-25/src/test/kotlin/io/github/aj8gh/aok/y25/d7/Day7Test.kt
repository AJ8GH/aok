package io.github.aj8gh.aok.y25.d7

import io.github.aj8gh.aok.util.DAY_7
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day7Test {

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
    private fun inputProviderPart1() = getInput(21L, 1590L)

    @JvmStatic
    private fun inputProviderPart2() = getInput(40L, 20571740188555L)

    private fun getInput(example: Long, answer: Long) = listOf(
      Arguments.of(readExample(DAY_7), example),
      Arguments.of(readInput(DAY_7), answer),
    )
  }
}
