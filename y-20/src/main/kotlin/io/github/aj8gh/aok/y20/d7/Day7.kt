package io.github.aj8gh.aok.y20.d7

private val colourPattern = Regex("(\\w+ \\w+)( bag(s?))")

fun part1(input: List<String>): Int {
  val bagToCarriers = mutableMapOf<String, MutableSet<String>>()
  for (line in input) {
    val colours = colourPattern.findAll(line)
      .map { it.groups[1]!!.value }
      .toList()

    val carrier = colours.first()
    for (i in colours.lastIndex downTo 1) {
      bagToCarriers.getOrPut(colours[i]) { mutableSetOf() }.add(carrier)
    }
  }

  var total = 0
  val processed = mutableSetOf<String>()
  val queue = ArrayDeque(listOf("shiny gold"))
  while (queue.isNotEmpty()) {
    val current = queue.removeFirst()
    val carriers = bagToCarriers[current]
    carriers?.forEach {
      if (it !in processed) {
        processed.add(it)
        queue.add(it)
        total++
      }
    }
  }
  return total
}

fun part2(input: List<String>) = 0
