package io.github.aj8gh.aok.y24.d14

val digits = Regex("-?\\d+")

data class Robot(
  var pX: Int,
  var pY: Int,
  val vX: Int,
  val vY: Int,
)

fun part1(input: List<String>): Int {
  val robots = input.map {
    digits.findAll(it)
      .map { r -> r.value.toInt() }
      .toList()
  }.map { l -> Robot(l[0], l[1], l[2], l[3]) }

  val max = Pair(robots.maxBy { it.pX }.pX + 1, robots.maxBy { it.pY }.pY + 1)
  move(robots, 100, max)
  return quadrantScore(robots, max)
}

fun part2(input: List<String>) = 0

private fun move(robots: List<Robot>, cycles: Int, max: Pair<Int, Int>) =
  repeat(cycles) {
    robots.forEach {
      it.pX = (it.pX + it.vX) % max.first
      if (it.pX < 0) it.pX += max.first
      it.pY = (it.pY + it.vY) % max.second
      if (it.pY < 0) it.pY += max.second
    }
  }

private fun quadrantScore(robots: List<Robot>, max: Pair<Int, Int>) =
  robots.filter { it.pX < max.first / 2 && it.pY < max.second / 2 }.size *
      robots.filter { it.pX > max.first / 2 && it.pY > max.second / 2 }.size *
      robots.filter { it.pX > max.first / 2 && it.pY < max.second / 2 }.size *
      robots.filter { it.pX < max.first / 2 && it.pY > max.second / 2 }.size
