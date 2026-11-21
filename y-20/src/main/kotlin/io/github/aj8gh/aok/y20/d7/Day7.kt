package io.github.aj8gh.aok.y20.d7

private const val SHINY_GOLD = "shiny gold"

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
  val queue = ArrayDeque(listOf(SHINY_GOLD))
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

data class Bag(
  val colour: String,
  val children: List<Bag>,
)

fun part2(input: List<String>): Int {
  val bagsByCarrier = mutableMapOf<String, MutableSet<String>>()
  for (line in input) {
    val colours = colourPattern.findAll(line)
      .map { it.groups[1]!!.value }
      .toList()

    val bag = colours.first()
    for (i in 1..colours.lastIndex) {
      bagsByCarrier.getOrPut(bag) { mutableSetOf() }.add(colours[i])
    }
  }
return 0
//  return findTotalCarried(SHINY_GOLD, mutableMapOf(), bagsByCarrier)
}

//private fun findTotalCarried(
//  colour: String,
//  totalCarriedByBag: MutableMap<String, Int>,
//  bagsByCarrier: Map<String, Set<String>>,
//): Int {
//  totalCarriedByBag[colour]?.let { return it }
//  var total = 0
//  val queue = ArrayDeque<String>()
//  bagsByCarrier[colour]?.forEach {
//    queue.add(it)
//  }
//  while (queue.isNotEmpty()) {
//    val current = queue.removeFirst()
//    val valForCurrent = totalCarriedByBag[current]
//    if (valForCurrent != null) {
//      total += valForCurrent
//    } else {
//      findTotalCarried(current, totalCarriedByBag, bagsByCarrier)
//    }
//    bagsByCarrier[current]?.forEach {
//      queue.add(it)
//    }
//  }
//  totalCarriedByBag[colour] = total
//  return total
//}


/*
shiny gold ->
1 dark olive, 2 vibrant plum
13 faded blue, 16 dotted black


 */
