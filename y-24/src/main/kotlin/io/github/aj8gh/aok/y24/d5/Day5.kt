package io.github.aj8gh.aok.y24.d5

private const val DELIM_1 = "|"
private const val DELIM_2 = ","

fun part1(input: List<String>) = parseUpdates(input)
  .filter { isValid(it, parseRules(input)) }
  .sumOf { it[it.size / 2] }

fun part2(input: List<String>): Int {
  val updates = parseUpdates(input)
  val rules = parseRules(input)
  return updates
    .filter { !isValid(it, rules) }
    .sumOf {
      for (i in it.indices) {
        for (j in (i + 1)..<it.size) {
          if (isInvalid(it, rules, i, j)) {
            val iVal = it[i]
            it[i] = it[j]
            it[j] = iVal
          }
        }
      }
      it[it.size / 2]
    }
}

private fun isValid(update: IntArray, rules: Map<Int, Set<Int>>): Boolean {
  for (i in update.indices) {
    for (j in (i + 1)..<update.size) {
      if (isInvalid(update, rules, i, j)) {
        return false
      }
    }
  }
  return true
}

private fun isInvalid(update: IntArray, rules: Map<Int, Set<Int>>, i: Int, j: Int) =
  !(rules[update[i]]?.contains(update[j]) ?: false)
      || rules[update[j]]?.contains(update[i]) ?: false

private fun parseRules(input: List<String>): Map<Int, Set<Int>> {
  val ruleMap = mutableMapOf<Int, MutableSet<Int>>()
  input.map { it.split(DELIM_1) }
    .filter { it.size > 1 }
    .map { Pair(it[0], it[1]) }
    .forEach {
      ruleMap
        .computeIfAbsent(it.first.toInt()) { mutableSetOf() }
        .add(it.second.toInt())
    }
  return ruleMap
}

private fun parseUpdates(input: List<String>): List<IntArray> = input
  .map { it.split(DELIM_2) }
  .filter { it.size > 1 }
  .map { it.map { s -> s.toInt() } }
  .map { it.toIntArray() }
