package io.github.aj8gh.aok.y24.d18

import io.github.aj8gh.aok.util.DAY_18
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day18Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, bytes: Int, expected: Int) =
    assertEquals(expected, part1(input, bytes))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, bytes: Int, expected: String) =
    assertEquals(expected, part2(input, bytes))

  companion object {
    @JvmStatic
    private fun inputProviderPart1() = listOf(
      Arguments.of(readExample(DAY_18), 12, 22),
      Arguments.of(readInput(DAY_18), 1024, 438),
    )

    @JvmStatic
    private fun inputProviderPart2() = listOf(
      Arguments.of(readExample(DAY_18), 12, "6,1"),
      Arguments.of(readInput(DAY_18), 1024, "26,22"),
    )
  }
}
