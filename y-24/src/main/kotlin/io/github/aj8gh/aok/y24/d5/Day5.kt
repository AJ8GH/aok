package io.github.aj8gh.aok.y24.d5

private const val DELIM_1 = "|"
private const val DELIM_2 = ","

fun part1(input: List<String>): Int {
  var count = 0
  val forwardRuleMap = mutableMapOf<Int, MutableSet<Int>>()
  input.map { it.split(DELIM_1) }
    .filter { it.size > 1 }
    .map { Pair(it[0], it[1]) }
    .forEach {
      forwardRuleMap
        .computeIfAbsent(it.first.toInt()) { mutableSetOf() }
        .add(it.second.toInt())
    }

  val updates = input
    .map { it.split(DELIM_2) }
    .filter { it.size > 1 }

  for (update in updates) {
    loop@ for (i in update.indices) {
      val c = update[i]
      val n = c.toInt()
      val forwardRuleSet = forwardRuleMap[n]

      for (j in (i + 1)..<update.size) {
        val cc = update[j]
        val nn = cc.toInt()
        forwardRuleSet?.contains(nn)?.let {
          if (!it) {
            break@loop
          }
        }
        val backwardRuleSet = forwardRuleMap[nn]
        backwardRuleSet?.contains(n)?.let {
          if (it) {
            break@loop
          }
        }
      }
      if (i == update.size - 1) {
        count += update[update.size / 2].toInt()
      }
    }
  }

  return count
}

fun part2(input: List<String>) = 0
