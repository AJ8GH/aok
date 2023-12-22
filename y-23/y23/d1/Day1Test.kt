package com.github.aj8gh.aok.y23.d1

import com.github.aj8gh.aok.util.DAY_1
import com.github.aj8gh.aok.util.INPUT_PROVIDER_PART_1
import com.github.aj8gh.aok.util.INPUT_PROVIDER_PART_2
import com.github.aj8gh.aok.util.InputReader.Reader
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class Day1Test {

  private val subject = Day1()

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_1)
  fun part1(input: List<String>, expected: Int) =
    assertEquals(expected, subject.part1(input))

  @ParameterizedTest
  @MethodSource(INPUT_PROVIDER_PART_2)
  fun part2(input: List<String>, expected: Int) =
    assertEquals(expected, subject.part2(input))

  companion object {

    @JvmStatic
    private fun inputProviderPart1() = getInput(142, 0)

    @JvmStatic
    private fun inputProviderPart2() = getInput(0, 0)

    private fun getInput(example: Int, answer: Int) = listOf(
      Arguments.of(example, Reader.readExample(DAY_1)),
      Arguments.of(answer, Reader.readInput(DAY_1)),
    )
  }
}
