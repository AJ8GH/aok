package io.github.aj8gh.aok.y25.d9

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun part1(input: List<String>) = solve(input, LEVEL_1)

fun part2(input: List<String>) = solve(input, LEVEL_2)

private fun solve(input: List<String>, level: Int): Long {
  val points = input.map {
    it.split(",")
      .let { s -> Pair(s.first().toLong(), s.last().toLong()) }
  }
  val sortedByX = points.sortedBy { it.first }
  val sortedByY = points.sortedBy { it.second }
  val ranges = mapRanges(points)
  val tiles = mapTiles(ranges)
  val total = ranges.first.values.flatten().sumOf { it.last - it.first } +
      ranges.second.values.flatten().sumOf { it.last - it.first }
  return maxArea(points, sortedByX, sortedByY, level)
}

private fun maxArea(
  points: List<Pair<Long, Long>>,
  sortedByX: List<Pair<Long, Long>>,
  sortedByY: List<Pair<Long, Long>>,
  level: Int
): Long {
  var max = 0L
  var pointSet = points.toSet()
  for (i in 0..<points.lastIndex) {
    for (j in i + 1..points.lastIndex) {
      val a = points[i]
      val b = points[j]
      if (level == LEVEL_2 && isInvalid(a, b, pointSet, sortedByX, sortedByY)) {
        continue
      }
      val area = (abs(a.first - b.first) + 1) * (abs(a.second - b.second) + 1)
      max = max(max, area)
    }
  }
  return max
}

private fun mapRanges(
  points: List<Pair<Long, Long>>
): Pair<Map<Long, List<LongRange>>, Map<Long, List<LongRange>>> {
  val yToXRanges = mutableMapOf<Long, MutableList<LongRange>>()
  val xToYRanges = mutableMapOf<Long, MutableList<LongRange>>()
  val mutPoints = points.toMutableList()
  mutPoints.add(points.first())
  for (i in 0..<mutPoints.lastIndex) {
    val a = mutPoints[i]
    val b = mutPoints[i + 1]
    if (a.first == b.first) {
      val ranges = xToYRanges.computeIfAbsent(a.first) { mutableListOf() }
      ranges.add(min(a.second, b.second)..max(a.second, b.second))
    }

    if (a.second == b.second) {
      val ranges = yToXRanges.computeIfAbsent(a.second) { mutableListOf() }
      ranges.add(min(a.first, b.first)..max(a.first, b.first))
    }
  }

  return Pair(xToYRanges, yToXRanges)
}

private fun isInvalid(
  a: Pair<Long, Long>,
  b: Pair<Long, Long>,
  points: Set<Pair<Long, Long>>,
  sortedByX: List<Pair<Long, Long>>,
  sortedByY: List<Pair<Long, Long>>,
): Boolean {
  for (p in listOf(Pair(a.first, b.second), Pair(b.first, b.second))) {
    if (!points.contains(p)) {
    }
  }

  return false
}

private fun mapTiles(
  ranges: Pair<Map<Long, List<LongRange>>, Map<Long, List<LongRange>>>
): Set<Pair<Long, Long>> {
  val tiles = mutableSetOf<Pair<Long, Long>>()

//  for (val in )

  return tiles
}
/*

7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3

  0 1 2 3 4 5 6 7 8 9 0 1 2 3
0 . . . . . . . . . . . . . .
1 . . . . . . . # X X X # . .
2 . . . . . . . X . . . X . .
3 . . # X X X X # . . . X . .
4 . . X . . . . . . . . X . .
5 . . # X X X X X X # . X . .
6 . . . . . . . . . X . X . .
7 . . . . . . . . . # X # . .
8 . . . . . . . . . . . . . .

  0 1 2 3 4 5 6 7 8 9 0 1 2 3
0 . . . . . . . . . . . . . .
1 . . . . . . . # X X X # . .
2 . . . . . . . X . . . X . .
3 . . # X X X X # . . . X . .
4 . . X . . . . . . . . X . .
5 . . # X X X X X X # . X . .
6 . . . . . . . . . X . X . .
7 . . . . . . . . . X . X . .
8 . . . . . . . . . X . X . .
9 . . . . . . . . . # X # . .
0 . . . . . . . . . . . . . .

 */
