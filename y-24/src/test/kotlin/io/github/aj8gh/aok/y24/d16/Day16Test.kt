package io.github.aj8gh.aok.y24.d16

import io.github.aj8gh.aok.util.DAY_16
import io.github.aj8gh.aok.util.EXAMPLE_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day16Test {

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
    private fun inputProviderPart1() = getInput(7036, 11048, 109516)

    @JvmStatic
    private fun inputProviderPart2() = getInput(45, 64, 568)

    private fun getInput(example: Int, example1: Int, answer: Int) = listOf(
      Arguments.of(readExample(DAY_16), example),
      Arguments.of(readExample(DAY_16, EXAMPLE_1), example1),
      Arguments.of(readInput(DAY_16), answer),
    )
  }
}
