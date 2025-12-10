package io.github.aj8gh.aok.y25.d9

import io.github.aj8gh.aok.util.LEVEL_1
import io.github.aj8gh.aok.util.LEVEL_2
import kotlin.math.abs
import kotlin.math.max

fun part1(input: List<String>): Long {
  val points = input.map {
    it.split(",")
      .let { s ->
        Pair(s.first().toLong(), s.last().toLong())
      }
  }

  val sortedByX = points.sortedBy { it.first }
  val sortedByY = points.sortedBy { it.second }
  return maxArea(points, sortedByX, sortedByY, LEVEL_1)
}

fun part2(input: List<String>): Long {
  val points = input.map {
    it.split(",")
      .let { s ->
        Pair(s.first().toLong(), s.last().toLong())
      }
  }

  val sortedByX = points.sortedBy { it.first }
  val sortedByY = points.sortedBy { it.second }

  return maxArea(points, sortedByX, sortedByY, LEVEL_2)
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

private fun isInvalid(
  a: Pair<Long, Long>,
  b: Pair<Long, Long>,
  points: Set<Pair<Long, Long>>,
  sortedByX: List<Pair<Long, Long>>,
  sortedByY: List<Pair<Long, Long>>,
): Boolean {
  return false
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

                   1 1 1 1
0 1 2 3 4 5 6 7 8 9 0 1 2 3
0. . . . . . . . . . . . . .
1. . . . . . . # X X X # . .
2. . . . . . . X . . . X . .
3. . # X X X X # . . . X . .
4. . X . . . . . . . . X . .
5. . # X X X X X X # . X . .
6. . . . . . . . . X . X . .
7. . . . . . . . . # X # . .
8. . . . . . . . . . . . . .

                   1 1 1 1
0 1 2 3 4 5 6 7 8 9 0 1 2 3
0. . . . . . . . . . . . . .
1. . . . . . . # X X X # . .
2. . . . . . . X . . . X . .
3. . # X X X X # . . . X . .
4. . X . . . . . . . . X . .
5. . # X X X X X X # . X . .
6. . . . . . . . . X . X . .
7. . . . . . . . . X . X . .
8. . . . . . . . . X . X . .
9. . . . . . . . . # X # . .
0. . . . . . . . . . . . . .
 */
