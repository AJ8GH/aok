package io.github.aj8gh.aok.y20.d4

import io.github.oshai.kotlinlogging.KotlinLogging

private const val COUNTRY_ID = "cid"
private const val SPACE = " "

private val log = KotlinLogging.logger { }

fun part1(input: List<String>): Int {
  var valid = 0
  var fields = 0

  for (i in input.indices) {
    if (input[i].isNotEmpty()) {
      for (field in input[i].split(SPACE)) {
        if (!field.trim().startsWith(COUNTRY_ID)) fields++
      }
    }
    if (i == input.lastIndex || input[i].isEmpty()) {
      if (fields >= 7) valid++
      fields = 0
      continue
    }
  }
  return valid
}

fun part2(input: List<String>) = 0
