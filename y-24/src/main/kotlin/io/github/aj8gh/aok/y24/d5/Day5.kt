package io.github.aj8gh.aok.y24.d5

private const val DELIM_1 = "|"
private const val DELIM_2 = ","

fun part1(input: List<String>): Int {
  var count = 0
  val ruleMap = mutableMapOf<Int, MutableSet<Int>>()
  input.map { it.split(DELIM_1) }
    .filter { it.size > 1 }
    .map { Pair(it[0], it[1]) }
    .forEach {
      ruleMap
        .computeIfAbsent(it.first.toInt()) { mutableSetOf() }
        .add(it.second.toInt())
    }

  val updates = input
    .map { it.split(DELIM_2) }
    .filter { it.size > 1 }
    .map { it.map { s -> s.toInt() } }

  for (update in updates) {
    loop@ for (i in update.indices) {
      for (j in (i + 1)..<update.size) {
        if (
          !(ruleMap[update[i]]?.contains(update[j]) ?: false)
          || ruleMap[update[j]]?.contains(update[i]) ?: false
        ) {
          break@loop
        }
      }
      if (i == update.size - 1) {
        count += update[update.size / 2]
      }
    }
  }

  return count
}

fun part2(input: List<String>): Int {
  var count = 0
  val ruleMap = mutableMapOf<Int, MutableSet<Int>>()
  input.map { it.split(DELIM_1) }
    .filter { it.size > 1 }
    .map { Pair(it[0], it[1]) }
    .forEach {
      ruleMap
        .computeIfAbsent(it.first.toInt()) { mutableSetOf() }
        .add(it.second.toInt())
    }

  val updates = input
    .map { it.split(DELIM_2) }
    .filter { it.size > 1 }
    .map { it.map { s -> s.toInt() } }

  for (update in updates) {
    val updatedUpdate = update.toIntArray()
    var hadToFix = false
    loop@ for (i in update.indices) {
      for (j in (i + 1)..<update.size) {
        if (
          !(ruleMap[updatedUpdate[i]]?.contains(updatedUpdate[j]) ?: false)
          || ruleMap[updatedUpdate[j]]?.contains(updatedUpdate[i]) ?: false
        ) {
          val iVal = updatedUpdate[i]
          updatedUpdate[i] = updatedUpdate[j]
          updatedUpdate[j] = iVal
          hadToFix = true
        }
      }
    }
    if (hadToFix) {
      count += updatedUpdate[updatedUpdate.size / 2]
    }
  }

  return count
}
