package io.github.aj8gh.aok.y23.d1

import io.github.aj8gh.aok.util.DAY_1
import io.github.aj8gh.aok.util.EXAMPLE_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day1Test {

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
      Arguments.of(readExample(DAY_1), 142),
      Arguments.of(readInput(DAY_1), 54644),
    )

    @JvmStatic
    private fun inputProviderPart2() = listOf(
      Arguments.of(readExample(DAY_1, EXAMPLE_1), 281),
      Arguments.of(readInput(DAY_1), 53348),
    )
  }
}
