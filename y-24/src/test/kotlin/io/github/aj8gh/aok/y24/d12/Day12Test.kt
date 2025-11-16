package io.github.aj8gh.aok.y24.d12

import io.github.aj8gh.aok.util.DAY_12
import io.github.aj8gh.aok.util.EXAMPLE_1
import io.github.aj8gh.aok.util.EXAMPLE_2
import io.github.aj8gh.aok.util.EXAMPLE_3
import io.github.aj8gh.aok.util.EXAMPLE_4
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day12Test {

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
    private fun inputProviderPart1() = listOf(
      Arguments.of(readExample(DAY_12, EXAMPLE_1), 140),
      Arguments.of(readExample(DAY_12, EXAMPLE_2), 772),
      Arguments.of(readExample(DAY_12), 1930),
      Arguments.of(readInput(DAY_12), 1533644),
    )

    @JvmStatic
    private fun inputProviderPart2() = listOf(
      Arguments.of(readExample(DAY_12, EXAMPLE_1), 80),
      Arguments.of(readExample(DAY_12, EXAMPLE_3), 236),
      Arguments.of(readExample(DAY_12, EXAMPLE_4), 368),
      Arguments.of(readExample(DAY_12), 1206),
      Arguments.of(readInput(DAY_12), 936718),
    )
  }
}
