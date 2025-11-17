package io.github.aj8gh.aok.y24.d14

import io.github.aj8gh.aok.util.DAY_14
import io.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import io.github.aj8gh.aok.util.InputReader.Reader.readExample
import io.github.aj8gh.aok.util.InputReader.Reader.readInput
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, expected: Int) =
    assertEquals(expected, part1(input))

  @Test
  fun part2() = assertEquals(6285, part2(readInput(DAY_14)))

  companion object {
    @JvmStatic
    private fun inputProviderPart1() = listOf(
      Arguments.of(readExample(DAY_14), 12),
      Arguments.of(readInput(DAY_14), 215476074),
    )
  }
}
