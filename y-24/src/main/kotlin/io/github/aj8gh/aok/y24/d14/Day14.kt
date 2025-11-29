package io.github.aj8gh.aok.y24.d14

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.Int.Companion.MAX_VALUE

private const val CYCLES = 100
private const val TREE_WIDTH = 15

private val digits = Regex("-?\\d+")
private val logger = KotlinLogging.logger { }

private data class Robot(
  var pX: Int,
  var pY: Int,
  val vX: Int,
  val vY: Int,
)

fun part1(input: List<String>) = solve(input, 1)

fun part2(input: List<String>) = solve(input, 2)

private fun solve(input: List<String>, level: Int): Int {
  val robots = input.map {
    digits.findAll(it)
      .map { r -> r.value.toInt() }
      .toList()
  }.map { l -> Robot(l[0], l[1], l[2], l[3]) }

  val max = Pair(robots.maxBy { it.pX }.pX + 1, robots.maxBy { it.pY }.pY + 1)
  return if (level == 1) {
    move(robots, max, CYCLES)
    quadrantScore(robots, max)
  } else {
    move(robots, max)
  }
}

private fun move(
  robots: List<Robot>,
  max: Pair<Int, Int>,
  cycles: Int = MAX_VALUE
): Int {
  repeat(cycles) { i ->
    robots.forEach {
      it.pX = (it.pX + it.vX) % max.first
      if (it.pX < 0) it.pX += max.first
      it.pY = (it.pY + it.vY) % max.second
      if (it.pY < 0) it.pY += max.second
    }
    if (hasTree(robots, max)) {
      return i + 1
    }
  }
  return -1
}

private fun quadrantScore(robots: List<Robot>, max: Pair<Int, Int>) =
  robots.filter { it.pX < max.first / 2 && it.pY < max.second / 2 }.size *
      robots.filter { it.pX > max.first / 2 && it.pY > max.second / 2 }.size *
      robots.filter { it.pX > max.first / 2 && it.pY < max.second / 2 }.size *
      robots.filter { it.pX < max.first / 2 && it.pY > max.second / 2 }.size

private fun hasTree(robots: List<Robot>, max: Pair<Int, Int>): Boolean {
  val groupedByY = robots.groupBy { it.pY }
  for (g in groupedByY) {
    val groupedByX = g.value.groupBy { it.pX }
    var i = 1
    var length = 1
    for (e in groupedByX) {
      while (groupedByX.contains(e.key + i)) {
        length++
        i++
        if (length >= TREE_WIDTH) {
          printTree(robots, max)
          return true
        }
      }
      length = 1
    }
  }
  return false
}

private fun printTree(robots: List<Robot>, max: Pair<Int, Int>) {
  val grid = mutableListOf<MutableList<Char>>()
  repeat(max.second) {
    val row = mutableListOf<Char>()
    repeat(max.first) {
      row.add('.')
    }
    grid.add(row)
  }
  for (r in robots) {
    grid[r.pY][r.pX] = '*'
  }
  logger.info { "\n\n${grid.map { "$it\n" }}\n\n" }
}
